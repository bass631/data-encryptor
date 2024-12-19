package ru.dataencryptor.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.dataencryptor.constants.LogTag;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableEncryptableProperties
@ComponentScan(basePackages = {"ru.dataencryptor"})
@Slf4j
public class EncryptorConfig {

    @Value("${spring.config.location}")
    private String configPath;

    @Bean(name = "configPath")
    public String getConfigPath() {
        return configPath;
    }

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        return getStandardPBEStringEncryptor();
    }

    @Primary
    @Bean(name = "standardPBEStringEncryptor")
    public StandardPBEStringEncryptor getStandardPBEStringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = getSimpleStringPBEConfig();
        encryptor.setConfig(config);
        log.info("[{}] Encryptor has been initialized...", LogTag.ENCRYPTOR);
        return encryptor;
    }

    private SimpleStringPBEConfig getSimpleStringPBEConfig() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword(getHostName());
        config.setKeyObtentionIterations(1000);
        config.setProviderName("SunJCE");
        config.setStringOutputType("base64");
        return config;
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "defaultValue";
        }
    }
}
