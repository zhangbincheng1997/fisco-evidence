package cn.edu.ipfs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource({"classpath:ipfs.properties"})
@ConfigurationProperties(prefix = "ipfs")
@Configuration
public class IPFSConfig {

    private String host;
    private int port;
    private String gateway;
}
