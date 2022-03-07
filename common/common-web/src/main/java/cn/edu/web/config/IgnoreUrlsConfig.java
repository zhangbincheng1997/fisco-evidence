package cn.edu.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Data
@PropertySource({"classpath:ignore-urls.properties"})
@ConfigurationProperties(prefix = "ignore")
@Configuration
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();
}
