package ru.dbastrygin.dataencryptor.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;
import ru.dbastrygin.dataencryptor.config.EncryptorProp;

@Slf4j
@Service
@RequiredArgsConstructor
public class EncryptorService {

    private final String configPath;
    private final StandardPBEStringEncryptor encryptor;
    private final EncryptorProp properties;


    @PostConstruct
    private void checkEnable() {
        if (properties.isEnabled()) {
            run();
        }
    }

    private void run() {
        switch (properties.getFileType()) {
            case YAML:
                EncryptYamlFile eyf = new EncryptYamlFile(configPath, encryptor, properties);
                eyf.startEncrypt();
                break;
            case PROPERTIES:
                EncryptPropertiesFile epf = new EncryptPropertiesFile(configPath, encryptor, properties);
                epf.startEncrypt();
                break;
        }
    }
}
