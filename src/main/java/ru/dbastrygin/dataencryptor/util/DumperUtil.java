package ru.dbastrygin.dataencryptor.util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

public final class DumperUtil {

    private DumperUtil() {}

    public static DumperOptions getDumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        return dumperOptions;
    }
}
