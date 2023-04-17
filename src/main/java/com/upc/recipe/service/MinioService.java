package com.upc.recipe.service;

import io.minio.MinioClient;

public interface MinioService {
    public boolean exitsBucket(MinioClient minioClient);
}
