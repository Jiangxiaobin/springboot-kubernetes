package com.example.utils;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class ConfigUtil {
    /*
    apiserver通讯地址
     */
    private static final String APISERVER_URL = "https://192.168.56.101:8080";

    public static KubernetesClient initKubernetesClint() {
        Config config = new ConfigBuilder()
                .withMasterUrl(APISERVER_URL)
                .build();
        return new DefaultKubernetesClient(config);
    }
}
