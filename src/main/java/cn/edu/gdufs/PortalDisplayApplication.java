package cn.edu.gdufs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync    // 开启异步任务
@EnableSwagger2
@SpringBootApplication
public class PortalDisplayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalDisplayApplication.class, args);
    }

}
