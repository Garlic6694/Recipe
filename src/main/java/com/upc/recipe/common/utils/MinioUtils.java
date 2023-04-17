package com.upc.recipe.common.utils;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Minio客户端工具类
 */
@SuppressWarnings("ALL")
@Slf4j
@Component
public class MinioUtils {

    /**
     * 创建文件桶（建议租户ID为桶的名称）
     */
    public boolean exitsBucket(MinioClient minioClient, String bucket) {
        boolean found = false;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("create bucket is error", e);
        }
        return found;
    }

    public String putObjectLocalFile(MinioClient minioClient, String bucket, String filename, String fileFullPath) {
        try {
            boolean bucketExsit = exitsBucket(minioClient, bucket);
            if (!bucketExsit) {
                //makeBucketPolicy(bucket);
                log.error(bucket + "-不存在");
                throw new RuntimeException(bucket + "-不存在");
            }

            minioClient
                    .uploadObject(
                            UploadObjectArgs.builder().bucket(bucket).object(filename).filename(fileFullPath).build()
                    );
            return minioClient.getObjectUrl(bucket, filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 自动创建桶并存储文件
     *
     * @param inputStream
     * @param fileName
     * @param bucket
     * @param fileSize
     * @return
     */
    public String putObjectStream(MinioClient minioClient, InputStream inputStream, String fileName, String bucket, Long fileSize) {

        try {
            boolean bucketExsit = exitsBucket(minioClient, bucket);
            if (bucketExsit) {
                //makeBucketPolicy(bucket);
                log.error(bucket + "-不存在");
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket).object(fileName).stream(inputStream, fileSize, -1).build());
            inputStream.close();
            return minioClient.getObjectUrl(bucket, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bucket   桶名称
     * @param path     文件夹路径 [doc/]
     * @param file     要上传的文件
     * @param fileName 自定义文件名
     * @return
     */
    public String putObject(MinioClient minioClient, String bucket, String path, MultipartFile file, String fileName) throws Exception {
        if (!exitsBucket(minioClient, bucket)) {
            log.error(bucket + "-不存在");
        }
        InputStream inputStream = null;
        try {

            inputStream = file.getInputStream();
            if (StringUtils.isEmpty(fileName)) {
                fileName = file.getOriginalFilename();
            }

            InputStream in = file.getInputStream();
            PutObjectOptions options = new PutObjectOptions(in.available(), -1);
            options.setContentType(file.getContentType());
            String objectName = path + "/" + System.currentTimeMillis() + "_" + fileName; // 生成时间戳防止重名
            minioClient.putObject(bucket, objectName, in, options);
            file.getInputStream().close();
            in.close();
            return minioClient.presignedGetObject(bucket, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    /**
     * 自动创建桶并存储文件
     *
     * @return
     */
    public String putObjectStream(MinioClient minioClient, String bucket, MultipartFile file) throws Exception {
        try {
            InputStream inputStream = file.getInputStream();
            boolean bucketExsit = exitsBucket(minioClient, bucket);
            if (!bucketExsit) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
                String config = "  [\n" +
                        "            {\n" +
                        "                \"Action\": [\n" +
                        "                    \"s3:GetBucketLocation\",\n" +
                        "                    \"s3:ListBucket\"\n" +
                        "                ],\n" +
                        "                \"Effect\": \"Allow\",\n" +
                        "                \"Principal\": \"*\",\n" +
                        "                \"Resource\": \"arn:aws:s3:::" + bucket + "\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"Action\": \"s3:GetObject\",\n" +
                        "                \"Effect\": \"Allow\",\n" +
                        "                \"Principal\": \"*\",\n" +
                        "                \"Resource\": \"arn:aws:s3:::\"+BUCKET_NAME+\"/*\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"Version\": \"2012-10-17\"\n" +
                        "    }";
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(bucket)
                        .config(config)
                        .build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket).object(file.getOriginalFilename()).stream(inputStream, inputStream.available(), -1).build());
            inputStream.close();
            return minioClient.getObjectUrl(bucket, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有桶文件
     *
     * @return
     */
    public List<Bucket> getListBuckets(MinioClient minioClient) {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 删除文件
     *
     * @param bucket     桶名称
     * @param objectName 对象名称
     * @return boolean
     */
    public boolean removeObject(MinioClient minioClient, String bucket, String objectName) {
        try {
            boolean exsit = exitsBucket(minioClient, bucket);
            if (exsit) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
                return true;
            }
        } catch (Exception e) {
            log.error("removeObject", e);
        }
        return false;
    }

    /**
     * 批量删除文件
     *
     * @param bucket      桶名称
     * @param objectNames 对象名称
     * @return boolean
     */
    public boolean removeObjects(MinioClient minioClient, String bucket, List<String> objectNames) {
        boolean exsit = exitsBucket(minioClient, bucket);
        if (exsit) {
            try {
                List<DeleteObject> objects = new LinkedList<>();
                for (String str : objectNames) {
                    objects.add(new DeleteObject(str));
                }
                minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucket).objects(objects).build());
                return true;
            } catch (Exception e) {
                log.error("removeObject", e);
            }
        }
        return false;
    }

    /**
     * 获取单个桶中的所有文件对象名称
     *
     * @param bucket 桶名称
     * @return {@link List}<{@link String}>
     */
    public List<String> getBucketObjectName(MinioClient minioClient, String bucket) {
        boolean exsit = exitsBucket(minioClient, bucket);
        if (exsit) {
            List<String> listObjetcName = new ArrayList<>();
            try {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    listObjetcName.add(item.objectName());
                }
                return listObjetcName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucket     桶名称
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    public InputStream getObjectInputStream(MinioClient minioClient, String bucket, String objectName) {
        boolean exsit = exitsBucket(minioClient, bucket);
        if (exsit) {
            try {
                ObjectStat objectStat = minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
                if (objectStat.length() > 0) {
                    // 获取objectName的输入流。
                    return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(objectName).build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除一个桶
     *
     * @param bucket 桶名称
     */
    public boolean removeBucket(MinioClient minioClient, String bucket) throws Exception {
        // 删除之前先检查`my-bucket`是否存在。
        boolean found = exitsBucket(minioClient, bucket);
        if (found) {
            Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).build());
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                //有对象文件，则删除失败
                if (item.size() > 0) {
                    return false;
                }
            }
            // 删除`bucketName`存储桶，注意，只有存储桶为空时才能删除成功。
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            found = exitsBucket(minioClient, bucket);
            return !found;
        }
        return false;
    }

    /**
     * 获取某个桶下某个对象的URL
     *
     * @param bucket     桶名称
     * @param objectName 对象名 (文件夹名 + 文件名)
     * @return
     */
    public String getBucketObject(MinioClient minioClient, String bucket, String objectName) throws Exception {
        // 删除之前先检查`my-bucket`是否存在。
        boolean found = exitsBucket(minioClient, bucket);
        if (found) {
            return minioClient.getObjectUrl(bucket, objectName);
        }
        return "";
    }

    /**
     * 根据文件路径得到预览文件绝对地址
     *
     * @param bucket     桶名称
     * @param objectName 对象名 (文件夹名+文件名)
     * @return
     */
    public String getPreviewFileUrl(MinioClient minioClient, String bucket, String objectName) {
        try {
            return minioClient.presignedGetObject(bucket, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {

        String bucket = "my-bucket-test01";
        String filename = System.currentTimeMillis() + "test.jpg";
        String fileFullPath = "test.jpg";

        String fileUrl = new MinioUtils().putObjectLocalFile(MinioClient.builder().build(), bucket, filename, fileFullPath);
        System.out.println(fileUrl);
    }

}

