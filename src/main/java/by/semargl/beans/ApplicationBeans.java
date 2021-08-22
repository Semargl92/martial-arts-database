package by.semargl.beans;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.mapstruct.factory.Mappers;

import by.semargl.controller.requests.mappers.ExerciseMapper;
import by.semargl.controller.requests.mappers.GradeMapper;
import by.semargl.controller.requests.mappers.MartialArtMapper;
import by.semargl.controller.requests.mappers.StudentMapper;
import by.semargl.controller.requests.mappers.UserCreateMapper;
import by.semargl.controller.requests.mappers.UserMapper;

@Configuration
@EnableCaching
public class ApplicationBeans {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users", "grades", "exercises", "students");
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

    @Bean
    public GradeMapper gradeMapper() {
        return Mappers.getMapper(GradeMapper.class);
    }

    @Bean
    public MartialArtMapper martialArtMapper() {
        return Mappers.getMapper(MartialArtMapper.class);
    }

    @Bean
    public StudentMapper studentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }

    @Bean
    public UserCreateMapper userCreateMapper() {
        return Mappers.getMapper(UserCreateMapper.class);
    }
}
