package by.semargl.beans;

import by.semargl.controller.requests.mappers.ExerciseMapper;
import by.semargl.controller.requests.mappers.UserMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class ApplicationBeans {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
                .expireAfterAccess(200, TimeUnit.SECONDS)
                .weakKeys()
                .recordStats();
    }

    @Bean
    public UserMapper userMapper() {
       return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public ExerciseMapper exerciseMapper() {
        return Mappers.getMapper(ExerciseMapper.class);
    }
}
