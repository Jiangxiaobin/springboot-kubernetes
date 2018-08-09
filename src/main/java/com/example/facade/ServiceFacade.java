package com.example.facade;

import com.example.utils.ConfigUtil;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Component;

@Component("serviceFacade")
public class ServiceFacade {
    private KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClint();

    public void createService() {
        Service service = new ServiceBuilder()
                .withApiVersion("v1")
                .withKind("Service")
                .withNewMetadata()
                .withName("gitbook")
                .addToLabels("app", "gitbook")
                .endMetadata()
                .withNewSpec()
                .withType("NodePort")
                .addToSelector("app", "gitbook")
                .addNewPort()
                .withName("web")
                .withPort(8080)
                .withNodePort(30005)
                .withTargetPort(new IntOrString(4000))
                .endPort()
                .endSpec()
                .build();
        if (service != null) {
            System.out.println("service成功啦");
        }
    }
}
