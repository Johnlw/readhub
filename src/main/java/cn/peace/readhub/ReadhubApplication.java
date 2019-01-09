package cn.peace.readhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.peace.readhub.mapper")
public class ReadhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadhubApplication.class, args);
    }

}