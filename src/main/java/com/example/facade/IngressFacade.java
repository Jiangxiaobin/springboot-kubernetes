package com.example.facade;

import com.example.utils.ConfigUtil;
import com.example.utils.K8S_Constants;
import com.example.utils.StringUtil;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.api.model.extensions.IngressBuilder;
import io.fabric8.kubernetes.api.model.extensions.IngressList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("ingressFacade")
public class IngressFacade {

    private KubernetesClient kubernetesClient;

    /**
     * 创建Ingress资源对象
     * 参数先写死
     */
    public void create() {
        //初始化客户端
//        KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient("extensions");
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //创建Ingress资源
        Ingress ingress = new IngressBuilder()
                .withNewMetadata()
                    .withName("php-apache")
                    .withNamespace("default")
                .endMetadata()
                .withKind("Ingress")
                .withNewSpec()
                    .addNewRule()
                        .withHost("php-apache.k8s3.paas.abc")
                        .withNewHttp()
                            .addNewPath()
                            .withPath("/")
                                .withNewBackend()
                                    .withServicePort(new IntOrString(80))
                                    .withServiceName("php-apache")
                                .endBackend()
                            .endPath()
                        .endHttp()
                    .endRule()
                .endSpec()
                .build();
        //在集群中创建Ingress资源
        kubernetesClient.extensions().ingresses().create(ingress);
    }

    /**
     * 根据条件查询集群中的Ingress资源
     * @param namespace 命名空间
     * @return list
     */
    public IngressList queryList(String namespace) {
        //初始化客户端
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //判断namespace是否有值
        if (StringUtil.emptyString(namespace))
            return kubernetesClient.extensions().ingresses().list();
        //namespace不为空
        else
            return kubernetesClient.extensions().ingresses().inNamespace(namespace).list();
    }

    /**
     * 根据条件查询Ingress的详情
     * @param namespace 命名空间
     * @param name 名称
     * @return model
     */
    public Ingress query(String namespace, String name) {
        //初始化客户端
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //名称为空，返回空值
        if (StringUtil.emptyString(name))
            return null;
        if (StringUtil.emptyString(namespace))
            namespace = K8S_Constants.DEFAULT;
        return kubernetesClient.extensions().ingresses().inNamespace(namespace).withName(name).get();
    }

    /**
     * 删除Ingress资源
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
        return kubernetesClient.extensions().ingresses().inNamespace(namespace).withName(name).delete();
    }
}
