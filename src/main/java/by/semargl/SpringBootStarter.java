package by.semargl;


import by.semargl.beans.ApplicationBeans;
import by.semargl.beans.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "by.semargl")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@EnableCaching
@EnableSwagger2
@Import({ApplicationBeans.class,
        SwaggerConfig.class})
public class SpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarter.class, args);
    }
}
