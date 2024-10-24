package com.ljl.opweGatewayService.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * @Author Liu Jialin
 * @Date 2023/9/1 20:46
 * @PackageName com.ljl.inventory.utils
 * @ClassName JwtUtil
 * @Description JwtUtils
 * @Version 1.0.0
 */
@Component
public class JwtUtil {

    //expire in
    public static final Long JWT_TTL = 8 * 60 * 60 *1000L;// 60 * 60 *1000  一个小时
    //key, minimal length is 6
    public static final String JWT_SECRET = "thisIsTheJwtSecretKeyForOneProjectWithEverythingAuthModule";

    public String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * generate jwt
     * @param subject data in token（json format）
     * @return
     */
    public Mono<String> createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return Mono.fromCallable(() -> getJwtBuilder(subject, null, getUUID()).compact());
    }

    /**
     * 生成jtw
     * @param subject data in token（json format）
     * @param ttlMillis token expire time
     * @return
     */
    public String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    private JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)                //UUID
                .setSubject(subject)        //data
                .setIssuer("glenn")        // signer
                .setIssuedAt(now)          // sign time
                .signWith(signatureAlgorithm, secretKey) // use HS256
                .setExpiration(expDate);
    }


    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public Mono<String> createJWT(String id, String subject, Long ttlMillis) {
        return Mono.fromCallable(() -> getJwtBuilder(subject, ttlMillis, id).compact());
    }

    /**
     * generate secret key
     * @return
     */
    public SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
//        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Mono<Claims> parseJWT(String jwt) {
        return Mono.fromCallable(() -> {
            SecretKey secretKey = generalKey();
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        });
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract any claims from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Get expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Validate the token (reactive style)
    public Mono<Boolean> validateToken(String token) {
        return Mono.just(!isTokenExpired(token));  // Reactive validation
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}