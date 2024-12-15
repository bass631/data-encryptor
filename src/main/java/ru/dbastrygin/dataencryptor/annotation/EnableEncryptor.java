package ru.dbastrygin.dataencryptor.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.dbastrygin.dataencryptor.config.EncryptorConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EncryptorConfig.class)
@Configuration
public @interface EnableEncryptor {
}