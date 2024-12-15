package ru.dbastrygin.dataencryptor.util;

public final class EncryptorUtil {

    private EncryptorUtil() {
    }

    private static final String PREFIX = "ENC(";
    private static final String SUFFIX = ")";

    public static boolean checkValueForEncrypt(String value) {
        return value != null && !value.contains(PREFIX);
    }

    public static String getFullEncryptValue(String value) {
        return PREFIX.concat(value).concat(SUFFIX);
    }
}
