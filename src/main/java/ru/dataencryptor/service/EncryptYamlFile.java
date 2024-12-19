package ru.dataencryptor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import ru.dataencryptor.config.EncryptorProp;
import ru.dataencryptor.constants.LogTag;
import ru.dataencryptor.util.EncryptorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class EncryptYamlFile {

    private final String configPath;
    private final StandardPBEStringEncryptor encryptor;
    private final EncryptorProp encryptorProp;

    private Map<String, Object> propertiesMap;

    public void startEncrypt() {
        log.info("[{}] Start of data encryption phase", LogTag.ENCRYPTOR);
        propertiesMap = PropertiesService.getYamlProperties(configPath);
        for (List<String> data : encryptorProp.getEncryptKey()) {
            processingValue(data.toArray(new String[0]));
        }
        PropertiesService.flushYamlProperties(configPath, propertiesMap);
        log.debug("[{}] Configuration after data encrypting: {}", LogTag.ENCRYPTOR, propertiesMap);
        log.info("[{}] Finish of data encryption phase", LogTag.ENCRYPTOR);
    }

    /**
     * Search for value by key in yaml file blocks
     *
     * @param yamlChain chain of yaml file attachments including key, value to be encrypted
     */
    private void processingValue(String... yamlChain) {
        if (yamlChain == null || yamlChain.length < 1 || yamlChain[0] == null) {
            log.warn("Incorrectly specified data for encrypting");
            return;
        }
        Map<String, Object> currentBlock = propertiesMap;
        int yamlChainLength = yamlChain.length - 1;
        try {
            for (int i = 0; i < yamlChainLength; i++) {
                String currentParam = yamlChain[i];
                Map<String, Object> nextBlock = (Map<String, Object>)
                    Optional.ofNullable(currentBlock.get(currentParam)).orElse(new HashMap<>());
                currentBlock.put(currentParam, nextBlock);
                currentBlock = nextBlock;
            }
            String currentKey = yamlChain[yamlChainLength];
            currentBlock.put(currentKey,
                             getValue(currentKey, (String) currentBlock.get(currentKey)));
            log.info("[{}] Data encrypted success for '{}' ", LogTag.ENCRYPTOR, currentKey);
        } catch (ClassCastException e) {
            log.error("[{}] Data type error, you need to check the data type in the yaml file for the key '{}'\n{}",
                      LogTag.ENCRYPTOR, yamlChain[yamlChainLength], e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("[{}] Error encrypting data for key {}, {}",
                     LogTag.ENCRYPTOR, yamlChain[yamlChainLength], e.getLocalizedMessage());
        }
    }

    private String getValue(String key, String value) {
        if (EncryptorUtil.checkValueForEncrypt(value)) {
            String fullEncryptValue = EncryptorUtil.getFullEncryptValue(encryptor.encrypt(value));
            log.debug("[{}] Value for key '{}' after encrypting: {}", LogTag.ENCRYPTOR, key, fullEncryptValue);
            return fullEncryptValue;
        } else {
            log.info("[{}] No encrypting data available for key '{}' ", LogTag.ENCRYPTOR, key);
            return value != null ? value : "";
        }
    }
}