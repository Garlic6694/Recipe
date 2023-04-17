package com.upc.recipe.controller;

import com.upc.recipe.annotation.AnonymousAccess;
import com.upc.recipe.common.api.CommonResult;
import com.upc.recipe.common.utils.MinioUtils;
import com.upc.recipe.dto.MinioUploadDto;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @Autowired
    private MinioUtils minioUtils;

    @AnonymousAccess
    @PostMapping(value = "/upload")
    public CommonResult<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();

            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String path = sdf.format(new Date());
            String objectUrl = minioUtils.putObject(minioClient, BUCKET_NAME, path, file, filename);

            if (objectUrl == null || objectUrl.equals("")) {
                return CommonResult.failed("上传失败");
            }
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(objectUrl);
            return CommonResult.success(minioUploadDto, "上传成功");
        } catch (Exception e) {
            log.info("上传发生错误：{}！", e.getMessage());
        }
        return CommonResult.failed("上传失败");
    }

    @AnonymousAccess
    @PostMapping(value = "/delete")
    public CommonResult<?> delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            boolean success = minioUtils.removeObject(minioClient, BUCKET_NAME, objectName);
            if (!success) {
                return CommonResult.failed("删除失败或没有这个文件");
            }
            return CommonResult.success(objectName, "删除成功");
        } catch (Exception e) {
            log.info("删除 [" + objectName + "] 发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed("删除失败");

    }

    @AnonymousAccess
    @GetMapping("/downloadFile")
    public CommonResult<?> downloadFile(@RequestParam String objectName, HttpServletResponse response) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            try (InputStream inputStream = minioUtils.getObjectInputStream(minioClient, BUCKET_NAME, objectName)) {
                if (inputStream == null) {
                    return CommonResult.failed("下载失败");
                }
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName, StandardCharsets.UTF_8));
                IOUtils.copy(inputStream, response.getOutputStream());
                return CommonResult.success(inputStream, "下载成功");
            }
        } catch (Exception e) {
            log.info("下载 [" + objectName + "] 发生错误: {}！", e.getMessage());
        }
        return CommonResult.failed("下载失败");
    }

    @AnonymousAccess
    @GetMapping("/getPreviewFileUrl")
    public CommonResult<?> getPreviewFileUrl(@RequestParam String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            String previewFileUrl = minioUtils.getPreviewFileUrl(minioClient, BUCKET_NAME, objectName);
            if (previewFileUrl.equals("")) {
                return CommonResult.failed("图片不存在或查找出现错误");
            }
            return CommonResult.success(previewFileUrl, "图片预览路径");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed("获取图片路径失败");
    }

}
