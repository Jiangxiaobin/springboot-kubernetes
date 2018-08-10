package com.example.facade;

import com.example.utils.ConfigUtil;
import com.example.utils.K8S_Constants;
import com.example.utils.StringUtil;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("deploymentFacade")
public class DeploymentFacade {

    private KubernetesClient kubernetesClient;

    /**
     * 创建Deployment资源
     * 以php-hpa应用为例
     * 未添加动态扩缩容
     * 参数先写死
     */
    public void create() {
        //初始化客户端
        kubernetesClient = ConfigUtil.initKubernetesClient("extensions");
        //创建model
        Deployment deployment = new DeploymentBuilder()
                .withKind("Deployment")
                .withNewMetadata()
                    .addToAnnotations("deployment.kubernetes.io/revision", "1")
                    .addToLabels("run", "php-apache")
                    .withName("php-apache")
                    .withNamespace("default")
                .endMetadata()
                .withNewSpec()
                    .withReplicas(1)
                    .withNewSelector()
                        .addToMatchLabels("run", "php-apache")
                        .endSelector()
                        .withNewTemplate()
                            .withNewMetadata()
                                .addToLabels("run", "php-apache")
                            .endMetadata()
                            .withNewSpec()
                                .addNewContainer()
                                    .withImage("192.168.56.106:5000/php-hpa")
                                    .withImagePullPolicy("Always")
                                    .withName("php-apache")
                                    .addNewPort()
                                        .withContainerPort(80)
                                        .withProtocol("TCP")
                                    .endPort()
                                .endContainer()
                                .withRestartPolicy("Always")
                                .withTerminationGracePeriodSeconds(30L)
                            .endSpec()
                        .endTemplate()
                    .endSpec()
                .build();
        //在集群中创建deployment资源
        this.kubernetesClient.apps().deployments().create(deployment);
    }

    /**
     * 根据条件（可选）查询集群内部的Deployments资源
     * @param namespace 命名空间，null表示全部命名空间
     * @return list
     */
    public DeploymentList queryList(String namespace) {
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //未传入${namespace}
        if (StringUtil.emptyString(namespace)) {
            return kubernetesClient.apps().deployments().list();
        }
        //传入${namespace}
        else {
            return kubernetesClient.apps().deployments().inNamespace(namespace).list();
        }
    }

    /**
     * 根据条件（可选）查询集群内部某个Deployment资源信息
     * @param namespace 命名空间
     * @param name 名称
     * @return model
     */
    public Deployment query(String namespace, String name) {
        kubernetesClient = ConfigUtil.initKubernetesClient();
        //${name}值为空，直接返回null
        if (StringUtil.emptyString(name)) {
            return null;
        }
        //${namespace}值为空，设置为default命名空间
        if (StringUtil.emptyString(namespace))
            namespace = K8S_Constants.DEFAULT;
        return kubernetesClient.apps().deployments().inNamespace(namespace).withName(name).get();
    }

    /**
     * 根据条件删除Deployment资源
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
        return kubernetesClient.apps().deployments().inNamespace(namespace).withName(name).delete();
    }
}