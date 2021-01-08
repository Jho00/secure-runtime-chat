package common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class AppConfig {
    private static volatile ConfigModel config = null;

    public static ConfigModel getConfig() {
        if (config != null) {
            return config;
        }
        try {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            config = yamlMapper.readValue(new File("src/main/resources/app.yml"), ConfigModel.class);
            return config;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Can not parse config");
    }
}
