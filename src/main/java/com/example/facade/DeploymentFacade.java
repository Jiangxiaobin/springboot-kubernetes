package com.example.facade;

import com.example.utils.ConfigUtil;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("deploymentFacade")
public class DeploymentFacade {

    private KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClint();

    public void createDeployment() {
        Deployment deployment = new DeploymentBuilder()
                .withApiVersion("extensions/v1beta1")
                .withKind("Deployment")
                .withNewMetadata()
                .withName("gitbook")
                .addToLabels("app", "gitbook")
                .endMetadata()
                .withNewSpec()
                .withReplicas(1)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", "gitbook")
                .endMetadata()
                .withNewSpec()
                .withServiceAccountName("gitbook")
                .addNewContainer()
                .withName("gitbook")
                .withImage("192.168.56.106:5000/alpine-gitbook:latest")
                .addNewPort()
                .withContainerPort(4000)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();
        if (deployment != null) {
            System.out.println("成功啦!");
        }
    }




}
