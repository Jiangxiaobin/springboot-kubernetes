package com.example.facade;

import com.example.utils.ConfigUtil;
import com.example.utils.StringUtil;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("namespaceFacade")
public class NamespaceFacade {

    private KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient();

    /**
     *  获取集群中所有命名空间
     * @return list
     */
    public NamespaceList queryNamespaces() {
        return kubernetesClient.namespaces().list();
    }

    /**
     * 通过名称查询命名空间
     * @param name 名称
     * @return model
     */
    public Namespace getNamespaceByName(String name) {
        if (!StringUtil.emptyString(name)) {
            return kubernetesClient.namespaces().withName(name).get();
        }
        return null;
    }

    /**
     * 通过标签查询命名空间
     * @param label 标签
     * @return model
     */
    public NamespaceList queryNamespaceByLabel(String label) {
        if (!StringUtil.emptyString(label)) {
            return kubernetesClient.namespaces().withLabel(label).list();
        }
        return null;
    }
}
