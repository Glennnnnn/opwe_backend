package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author Liu Jialin
 * @Date 2024/7/18 21:53
 * @PackageName com.ljl.opweOpenService.service.impl
 * @ClassName MinioServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String uploadFile(String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .contentType(contentType)
                    .build());

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(7, TimeUnit.DAYS) // URL will be valid for 7 days
                    .build()); // Return MinIO object URL
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    @Override
    public ObjectWriteResponse uploadFileSimple(String bucketName, String objectName, MultipartFile multipartFile) {
        try {
            return minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .contentType(multipartFile.getContentType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }
    }

    @Override
    public void ensureBucketExists(String bucketName) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        // Check if the bucket exists
        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        // Create the bucket if it does not exist
        if (!bucketExists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
            System.out.println("Bucket created: " + bucketName);
        } else {
            System.out.println("Bucket already exists: " + bucketName);
        }
    }

    @Override
    public InputStream fetchFile(String bucketName, String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generatePresignedUrl(String bucketName, String objectKey, int expiryTimeInSeconds) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectKey)
                            .expiry(expiryTimeInSeconds)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }

    /**
     * fetch file by prefix
     *
     * @param objectName
     * @return
     */
    @Override
    public List<String> fuzzyListObjects(String bucketName, String objectName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(objectName)
                            .build());
            if (results == null || !results.iterator().hasNext()) {
                return null;
            }
            List<String> list = new ArrayList<>();
            for (Result<Item> result : results) {
                String s = result.get().objectName();
                String finalName = null;
                if (s.lastIndexOf("/") != -1) {
                    finalName = s.substring(s.lastIndexOf("/") + 1);
                } else {
                    finalName = s;
                }
                if (!finalName.startsWith(objectName)) {
                    continue;
                }
                list.add(s);
            }
            return list;
        } catch (Exception e) {
            log.error("#### fuzzyListObjects error: {}", objectName, e);
            return null;
        }
    }

    /**
     * compose file
     *
     * @param list
     * @param originBucket
     * @param originalFileName
     * @return
     */
    @Override
    public String composeObject(List<String> list, String originalFileName, String originBucket, String targetBucket) {
        String objectName = null;
        List<ComposeSource> composeSourceList = list.stream().map(s -> {
            ComposeSource source = ComposeSource.builder().bucket(originBucket).object(s).build();
            return source;
        }).collect(Collectors.toList());
        try {
            //sort by name eg: xx.part0,xx.part1...
            Collections.sort(composeSourceList, new Comparator<>() {
                @Override
                public int compare(ComposeSource o1, ComposeSource o2) {
                    if (o1.object().compareTo(o2.object()) < 0) {
                        return -1;
                    }
                    return 1;
                }
            });
            objectName = originalFileName;
//            objectName = pathPrefix.concat("/").concat(fileName);
            minioClient.composeObject(ComposeObjectArgs.builder().bucket(targetBucket).object(objectName).sources(composeSourceList).build());
        } catch (Exception e) {
            log.error("####### composeObject error", e);
            return null;
        }
        return originalFileName;
//        return objectName;
    }

    @Override
    public void deleteObjects(List<String> objects, String bucketName) {
        List<DeleteObject> collect = objects.stream().map(str -> new DeleteObject(str)).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(collect).build());
        Iterator<Result<DeleteError>> iterator = results.iterator();
        while (iterator.hasNext()) {
            Result<DeleteError> next = iterator.next();
            System.out.println(next);
        }
    }

    @Override
    public boolean isFileExists(String bucketName, String objectName) {
        try {
            minioClient.statObject(
                    io.minio.StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
            return true; // File exists
        } catch (ErrorResponseException | InsufficientDataException |
                InternalException | InvalidResponseException |
                IOException | NoSuchAlgorithmException |
                ServerException e) {
            // File does not exist or other error occurred
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error checking file existence", e);
        }
    }
}
