package io.github.wdpm.maple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 使用HashMap保存配置
 *
 * @author evan
 * @date 2020/4/22
 */
public class ConfigLoader {
    private Map<String, Object> configMap;

    public ConfigLoader() {
        this.configMap = new HashMap<>();
    }

    public void load(String conf) {
        Properties properties = new Properties();
        try (FileInputStream inStream = new FileInputStream(new File(conf))) {
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        toMap(properties);
    }

    private void toMap(Properties properties) {
        if (null != properties) {
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                Object value = properties.get(key);
                configMap.put(key.toString(), value);
            }
        }
    }

    public void setConf(String name, String value) {
        configMap.put(name, value);
    }

    public String getConf(String name) {
        Object value = configMap.get(name);
        if (null != value) {
            return value.toString();
        }
        return null;
    }
}

