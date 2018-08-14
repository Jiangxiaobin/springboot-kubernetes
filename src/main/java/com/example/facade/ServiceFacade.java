package com.example.facade;

import com.example.utils.ConfigUtil;
import com.example.utils.K8S_Constants;
import com.example.utils.StringUtil;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("serviceFacade")
public class ServiceFacade {
    private KubernetesClient kubernetesClient;

    /**
     * 创建Service资源
     * 以php-apache为例
     * 参数先写死
     */
    public void create() {
        //初始化客户端
//        kubernetesClient = ConfigUtil.initKubernetesClient("v1");
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //创建资源对象
        Service service = new ServiceBuilder()
                .withKind("Service")
                .withNewMetadata()
                    .withName("php-apache")
                    .withNamespace("default")
                .endMetadata()
                .withNewSpec()
                    .withType("NodePort")
                    .addNewPort()
                        .withPort(80)
                        .withProtocol("TCP")
                        .withNodePort(31000)
                        .withTargetPort(new IntOrString(80))
                    .endPort()
                    .addToSelector("run", "php-apache")
                .endSpec()
                .build();
        //在集群中创建资源
        this.kubernetesClient.services().create(service);
    }

    /**
     * 通过传入条件查询某命名空间下的service信息
     * @param namespace 命名空间 可传空值
     * @return list
     */
    public ServiceList queryList(String namespace) {
        //初始化客户端
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //当传入的${namespace}为空时
        if (StringUtil.emptyString(namespace))
            return kubernetesClient.services().list();
        else
            return kubernetesClient.services().inNamespace(namespace).list();
    }

    /**
     * 通过条件查询某个Service资源的详情信息
     * @param name 服务名称
     * @param namespace 命名空间
     * @return model
     */
    public Service query(String name, String namespace) {
        //初始化客户端
        kubernetesClient = ConfigUtil.initKubernetesClient();
        if (StringUtil.emptyString(name))
            return null;
        if (StringUtil.emptyString(namespace))
            namespace = K8S_Constants.DEFAULT;
        return kubernetesClient.services().inNamespace(namespace).withName(name).get();
    }

    /**
     * 删除Deployment资源
     * @param namespace 命名空间
     * @param name 名称
     * @return 是否成功删除
     */
    public Boolean delete(String namespace, String name) {
        if (StringUtil.emptyString(namespace) || StringUtil.emptyString(name)) {
            System.out.println("传入值有误，不予操作！");
            return false;
        }
        //初始化客户端
        //注意！删除资源时，不用特意指定apiVersion！
        kubernetesClient = ConfigUtil.initKubernetesClient();
        return kubernetesClient.services().inNamespace(namespace).withName(name).delete();
    }
}
