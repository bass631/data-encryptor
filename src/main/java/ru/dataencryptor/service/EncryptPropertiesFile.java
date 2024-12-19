package ru.dataencryptor.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import ru.dataencryptor.config.EncryptorProp;
import ru.dataencryptor.constants.LogTag;
import ru.dataencryptor.util.EncryptorUtil;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class EncryptPropertiesFile {

    private final String configPath;
    private final StandardPBEStringEncryptor encryptor;
    private final EncryptorProp encryptorProp;

    private Properties propertiesCache;
    private boolean isFlush;


    public void startEncrypt() {
        log.info("[{}] Start of data encryption phase", LogTag.ENCRYPTOR);
        propertiesCache = PropertiesService.getProperties(configPath);
        encryptorProp.getEncryptKey().forEach(key -> processingValue(key.get(0)));
        if (isFlush) {
            PropertiesService.flushProperties(configPath, propertiesCache);
        }
        log.debug("[{}] Configuration after data encrypting: {}", LogTag.ENCRYPTOR, propertiesCache);
        log.info("[{}] Finish of data encryption phase", LogTag.ENCRYPTOR);
    }

    private void processingValue(String key) {
        String value = propertiesCache.getProperty(key);
        if (EncryptorUtil.checkValueForEncrypt(value)){
            String fullEncryptValue = EncryptorUtil.getFullEncryptValue(encryptor.encrypt(value));
            log.debug("[{}] Value for key '{}' after encrypting: {}", LogTag.ENCRYPTOR, key, fullEncryptValue);
            propertiesCache.setProperty(key, fullEncryptValue);
            isFlush = true;
            log.info("[{}] Data encrypted success for '{}' ", LogTag.ENCRYPTOR, key);
        } else {
            log.info("[{}] No encrypting data available for key '{}' ", LogTag.ENCRYPTOR, key);
        }
    }
}
