package com.example.facade;

import com.example.utils.ConfigUtil;
import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.api.model.rbac.KubernetesClusterRoleBinding;
import io.fabric8.kubernetes.api.model.rbac.KubernetesClusterRoleBindingBuilder;
import io.fabric8.kubernetes.api.model.rbac.KubernetesRoleRefBuilder;
import io.fabric8.kubernetes.api.model.rbac.KubernetesSubjectBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.ClusterRoleBinding;
import io.fabric8.openshift.api.model.ClusterRoleBindingBuilder;
import org.springframework.stereotype.Component;

@Component("serviceAccountFacade")
public class ServiceAccountFacade {
    private KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient();

    public void create() {
        ServiceAccount serviceAccount = new ServiceAccountBuilder()
                .withApiVersion("v1")
                .withKind("ServiceAccount")
                .withNewMetadata()
                .withName("gitbook")
                .withNamespace("default")
                .endMetadata()
                .build();

        ClusterRoleBinding clusterRoleBinding = new ClusterRoleBindingBuilder()
                .withKind("")
                .withApiVersion("")
                .withNewMetadata()
                .withName("")
                .endMetadata()
                .addNewSubject()
                .withKind("")
                .withName("")
                .withNamespace("")
                .endSubject().build();

        KubernetesClusterRoleBinding kubernetesClusterRoleBinding = new KubernetesClusterRoleBindingBuilder()
                .withNewMetadata()
                .withName("read-nodes")
                .endMetadata()
                .addToSubjects(0, new KubernetesSubjectBuilder()
                        .withApiGroup("rbac.authorization.k8s.io")
                        .withKind("User")
                        .withName("jane")
                        .withNamespace("default")
                        .build()
                )
                .withRoleRef(new KubernetesRoleRefBuilder()
                        .withApiGroup("rbac.authorization.k8s.io")
                        .withKind("ClusterRole")
                        .withName("node-reader")
                        .build()
                )
                .build();

        if (serviceAccount != null  && kubernetesClusterRoleBinding != null) {
            System.out.println("权限成功了!");
        }


    }
}
