package com.example.facade;

import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.openshift.api.model.ClusterRoleBinding;
import io.fabric8.openshift.api.model.ClusterRoleBindingBuilder;
import org.springframework.stereotype.Component;

@Component("serviceAccountFacade")
public class ServiceAccountFacade {

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
                .withKind("ClusterRoleBinding")
                .withApiVersion("rbac.authorization.k8s.io/v1beta1")
                .withNewMetadata()
                .withName("gitbook")
                .endMetadata()
                .addNewSubject()
                .withKind("ServiceAccount")
                .withName("gitbook")
                .withNamespace("default")
                .endSubject()
                .withNewRoleRef()
                .withKind("ClusterRole")
                .withName("cluster-admin")
                .withApiVersion("rbac.authorization.k8s.io")
                .endRoleRef()
                .build();

        if (serviceAccount != null  && clusterRoleBinding != null) {
            System.out.println("权限成功了!");
        }


    }
}
