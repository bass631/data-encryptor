package ru.dataencryptor.service;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import ru.dataencryptor.constants.LogTag;
import ru.dataencryptor.util.DumperUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class PropertiesService {

    private static final String READ_SUCCESS = "Data from the configuration file was received success.";
    private static final String READ_ERROR = "Error getting data from configuration file";
    private static final String WRITE_SUCCESS = "Data written to the configuration file success.";
    private static final String WRITE_ERROR = "Error writing data to configuration file";

    /**
     * Reading data from YAML configuration file into map
     */
    public static Map<String, Object> getYamlProperties(String path) {
        Map<String, Object> properties = new HashMap<>();
        try (final InputStream inputStream = Files.newInputStream(Paths.get(path))) {
            Yaml yamlIn = new Yaml();
            properties = yamlIn.load(inputStream);
            log.debug("[{}] {}", READ_SUCCESS, LogTag.ENCRYPTOR);
        } catch (IOException e) {
            log.error("[{}] {}\n{}", READ_ERROR, LogTag.ENCRYPTOR, e.getLocalizedMessage());
        }
        return properties;
    }

    /**
     * Writing data to the YAML configuration file
     */
    public static void flushYamlProperties(String path, Map<String, Object> properties) {
        DumperOptions options = DumperUtil.getDumperOptions();
        Yaml yamlOut = new Yaml(options);
        try (final Writer writer = new FileWriter(path)) {
            yamlOut.dump(properties, writer);
            log.debug("[{}] {}", WRITE_SUCCESS, LogTag.ENCRYPTOR);
        } catch (IOException e) {
            log.error("[{}] {}\n{}", WRITE_ERROR, LogTag.ENCRYPTOR, e.getLocalizedMessage());
        }
    }

    /**
     * Reading data from PROPERTIES configuration file into map
     */
    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        try (final InputStream inputStream = Files.newInputStream(Paths.get(path))) {
            properties.load(inputStream);
            log.debug("[{}] {}", READ_SUCCESS, LogTag.ENCRYPTOR);
        } catch (IOException e) {
            log.error("[{}] {}\n{}", READ_ERROR, LogTag.ENCRYPTOR, e.getLocalizedMessage());
        }
        return properties;
    }

    /**
     * Writing data to the PROPERTIES configuration file
     */
    public static void flushProperties(String path, Properties properties) {
        try (final OutputStream outputStream = Files.newOutputStream(Paths.get(path))) {
            properties.store(outputStream, "File updated");
            log.debug("[{}] {}", WRITE_SUCCESS, LogTag.ENCRYPTOR);
        } catch (IOException e) {
            log.error("[{}] {}\n{}", WRITE_ERROR, LogTag.ENCRYPTOR, e.getLocalizedMessage());
        }
    }
}
