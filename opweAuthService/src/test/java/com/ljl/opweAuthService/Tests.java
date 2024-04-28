package com.ljl.opweAuthService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Liu Jialin
 * @Date 2024/4/15 23:17
 * @PackageName com.ljl.opweAuthService
 * @ClassName Tests
 * @Description TODO
 * @Version 1.0.0
 */
@SpringBootTest
public class Tests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Test
//    void generateId(){
//        System.out.println(snowFlakeUtil.getNextId());
//    }

    @Test
    void generateEncodedPassword(){
        System.out.println(bCryptPasswordEncoder.encode("test01"));
//        System.out.println(passwordEncoder.encode("clhl658r8g"));
//        System.out.println(bCryptPasswordEncoder.encode("h0y64ziy7d"));
//        System.out.println(passwordEncoder.encode("h0y64ziy7d"));
//        System.out.println(bCryptPasswordEncoder.encode("prvlx6qfwq"));
//        System.out.println(passwordEncoder.encode("prvlx6qfwq"));
//        System.out.println(bCryptPasswordEncoder.matches("h0y64ziy7d", "$2a$10$WuwepFdpzkBw1wLGfIDPyO8gopHrWRA/22cl6.JOksk.Hs9tjCuoa"));
//        System.out.println(passwordEncoder.matches("h0y64ziy7d", "$2a$10$WuwepFdpzkBw1wLGfIDPyO8gopHrWRA/22cl6.JOksk.Hs9tjCuoa"));
    }
}
