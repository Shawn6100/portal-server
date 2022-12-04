package cn.edu.gdufs;

import cn.edu.gdufs.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PortalDisplayApplication {

    @Test
    void contextLoads() {
    }

    @Test
    void testMD5Util() {
        // 生成随机盐值
        System.out.println(MD5Util.getSalt());
        // 生成超级管理员的密码
        System.out.println(MD5Util.encode("123456", "2kMb0BdrlHMC7otL"));
    }

}
