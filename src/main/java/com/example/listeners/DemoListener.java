package com.example.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==>DemoListener启动");
//        CountDownLatch closeLatch = new CountDownLatch(1);
//        /*
//        添加监控
//         */
//        KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient();
//
//        /*
//        监控Deployments
//         */
//        kubernetesClient.apps().deployments().inNamespace("default").watch(new Watcher<Deployment>() {
//            @Override
//            public void eventReceived(Watcher.Action action, Deployment resource) {
//                System.out.println("deployments eventReceived >>>>> " + action + " > " + resource);
//            }
//
//            @Override
//            public void onClose(KubernetesClientException cause) {
//                if (cause != null)
//                    System.out.println("Deployment eventReceived >>>>> " + cause);
//            }
//        });
//
//        /*
//        监控service
//         */
//        kubernetesClient.services().inNamespace("default").watch(new Watcher<Service>() {
//            @Override
//            public void eventReceived(Watcher.Action action, Service resource) {
//                System.out.println("services eventReceived >>>>> " + action + " > " + resource);
//            }
//
//            @Override
//            public void onClose(KubernetesClientException cause) {
//                if (cause != null)
//                    System.out.println("services eventReceived >>>>> " + cause);
//            }
//        });
//
//        /*
//        监控ingress
//         */
//        kubernetesClient.extensions().ingresses().inNamespace("default").watch(new Watcher<Ingress>() {
//            @Override
//            public void eventReceived(Action action, Ingress resource) {
//                System.out.println("ingresses eventReceived >>>>> " + action + " > " + resource);
//            }
//
//            @Override
//            public void onClose(KubernetesClientException cause) {
//                if (cause != null)
//                    System.out.println("ingresses eventReceived >>>>> " + cause);
//            }
//        });
//
//        try {
//            closeLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
