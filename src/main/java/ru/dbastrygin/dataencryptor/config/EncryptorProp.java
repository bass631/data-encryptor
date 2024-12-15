package ru.dbastrygin.dataencryptor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.dbastrygin.dataencryptor.constants.FileType;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "encryptor")
@Data
public class EncryptorProp {

    /**
     * Flag for enable/disable encryptor
     */
    private boolean enabled;
    /**
     * File type to encrypt
     */
    private FileType fileType;
    /**
     * The key whose value needs to be encrypted
     */
    private List<List<String>> encryptKey;
}
