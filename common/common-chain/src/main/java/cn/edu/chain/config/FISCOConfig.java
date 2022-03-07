package cn.edu.chain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource({"classpath:fisco.properties"})
@ConfigurationProperties(prefix = "fisco")
@Configuration
public class FISCOConfig {

    private Integer group;
    private String privateKey;
}
