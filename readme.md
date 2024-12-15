# data-encryptor 
The **data-encryptor** library is a utility for encrypting data in yaml and properties files.

### Usage
#### 1. To activate the library, you need to add the @EnableEncrypt annotation to your project.
#### 2. Add the required variables to the configuration file:

- for **yaml** file

```
spring:
    config:
        location: .../application.yaml                    // path to file
encryptor:
    enabled: true                                         // enabled encrypting data
    file-type: yaml                                       // file type
    encrypt-key:
    - server,ssl,key-store-password                             
    - server,ssl,trust-store-password                     // key for encrypting
```

- for **properties** file

```
spring.config.location=.../application.properties           // path to file
encryptor.enabled=true                                      // enabled encrypting data
encryptor.file-type=properties                              // file type
encryptor.encrypt-key=server.ssl.key-store-password,server.ssl.trust-store-password  // key for encrypting
```