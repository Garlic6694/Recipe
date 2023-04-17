package com.upc.recipe.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan({"com.upc.recipe.mbg.mapper",
        "com.upc.recipe.dao",
        "com.upc.recipe.mapper"})
public class MyBatisConfig {
}
