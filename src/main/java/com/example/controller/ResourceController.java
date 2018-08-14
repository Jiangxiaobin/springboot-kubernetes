package com.example.controller;

import com.example.facade.DeploymentFacade;
import com.example.facade.EventFacade;
import com.example.facade.IngressFacade;
import com.example.facade.ServiceFacade;
import com.example.utils.ConfigUtil;
import com.example.utils.K8S_Constants;
import io.fabric8.kubernetes.api.model.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.api.model.extensions.IngressList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private DeploymentFacade deploymentFacade;

    @Autowired
    private ServiceFacade serviceFacade;

    @Autowired
    private IngressFacade ingressFacade;

    @Autowired
    private EventFacade eventFacade;

    /**
     * 新增Deployment
     */
    @RequestMapping("/createApp")
    public String createPHP_HPA_Application() {
        String name = "php-apache";
        String namespace = K8S_Constants.DEFAULT;

        /*
        创建Deployment资源
         */
        //创建前首先查询${namespace}下的所有deployments
        DeploymentList deploymentList = this.deploymentFacade.queryList(namespace);
        System.out.println("Deployment资源创建前，集群中的Deployment资源有：");
        System.out.println(deploymentList);
        //查询某个Deployment的详情信息
        Deployment deployment = this.deploymentFacade.query(namespace, name);
        System.out.println("Deployment资源创建前，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Deployment资源？" + (deployment == null? "否": "是"));
        if (deployment != null) {
            System.out.println("该Deployment的信息详情为：" + deployment.toString());
        }
        //创建Deployment
        System.out.println("即将开始创建Deployment");
        this.deploymentFacade.create();
        System.out.println("Deployment资源创建完成");

        //开始验证Deployment资源创建结果
        System.out.println("开始验证Deployment资源创建结果");
        //创建前首先查询${namespace}下的所有deployments
        deploymentList = this.deploymentFacade.queryList(namespace);
        System.out.println("Deployment资源创建后，集群中的Deployment资源有：");
        System.out.println(deploymentList);
        //查询某个Deployment的详情信息
        deployment = this.deploymentFacade.query(namespace, name);
        System.out.println("Deployment资源创建后，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Deployment资源？" + (deployment == null? "否": "是"));
        if (deployment != null) {
            System.out.println("该Deployment的信息详情为：" + deployment.toString());
        }
        System.out.println("Deployment资源创建完成！");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        /*
        创建Service资源
         */
        //创建前首先查询${namespace}下的所有services
        ServiceList serviceList = this.serviceFacade.queryList(namespace);
        System.out.println("Service资源创建前，集群中的Service资源有：");
        System.out.println(serviceList);
        //查询某个Service的详情信息
        Service service = this.serviceFacade.query(namespace, name);
        System.out.println("Service资源创建前，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Service资源？" + (service == null? "否": "是"));
        if (service != null) {
            System.out.println("该Service的信息详情为：" + service.toString());
        }
        //创建Service
        System.out.println("即将开始创建Service");
        this.serviceFacade.create();
        System.out.println("Service资源创建完成");

        //开始验证Service资源创建结果
        System.out.println("开始验证Service资源创建结果");
        //创建前首先查询${namespace}下的所有Services
        serviceList = this.serviceFacade.queryList(namespace);
        System.out.println("Service资源创建后，集群中的Service资源有：");
        System.out.println(serviceList);
        //查询某个Service的详情信息
        service = this.serviceFacade.query(namespace, name);
        System.out.println("Service资源创建后，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Service资源？" + (service == null? "否": "是"));
        if (service != null) {
            System.out.println("该Service的信息详情为：" + service.toString());
        }
        System.out.println("Service资源创建完成！");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        /*
        创建Ingress资源
         */
        //创建前首先查询${namespace}下的所有ingresses
        IngressList ingressList = this.ingressFacade.queryList(namespace);
        System.out.println("Ingress资源创建前，集群中的Ingress资源有：");
        System.out.println(ingressList);
        //查询某个ingress的详情信息
        Ingress ingress = this.ingressFacade.query(namespace, name);
        System.out.println("Ingress资源创建前，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Ingress资源？" + (ingress == null? "否": "是"));
        if (ingress != null) {
            System.out.println("该ingress的信息详情为：" + ingress.toString());
        }
        //创建Ingress
        System.out.println("即将开始创建Ingress");
        this.ingressFacade.create();
        System.out.println("Ingress资源创建完成");

        //开始验证Ingress资源创建结果
        System.out.println("开始验证Ingress资源创建结果");
        //创建前首先查询${namespace}下的所有Ingresses
        ingressList = this.ingressFacade.queryList(namespace);
        System.out.println("Ingress资源创建后，集群中的Ingress资源有：");
        System.out.println(ingressList);
        //查询某个Ingress的详情信息
        ingress = this.ingressFacade.query(namespace, name);
        System.out.println("Ingress资源创建后，集群中是否有命名空间为" + namespace + "，名称为" + name + "的Ingress资源？" + (ingress == null? "否": "是"));
        if (ingress!= null) {
            System.out.println("该Ingress的信息详情为：" + ingress.toString());
        }
        System.out.println("Ingress资源创建完成！");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");

        return null;
    }

    @RequestMapping("/deleteApp")
    public String deleteApp() {
        String namespace = K8S_Constants.DEFAULT;
        String name = "php-apache";
        System.out.println("测试删除功能开始");
        Deployment deployment = this.deploymentFacade.query(namespace, name);
        System.out.println("Deployment删除前，系统中是否存在该Deployment？" + (deployment == null? "否": "是"));
        if (deployment != null)
            System.out.println(deployment);
        System.out.println("开始执行删除Deployment操作");
        Boolean deploymentFlag = this.deploymentFacade.delete(namespace, name);
        System.out.println("删除Deployment操作完成，结果是" + (deploymentFlag ? "成功！": "失败！"));
        deployment = this.deploymentFacade.query(namespace, name);
        System.out.println("Deployment删除后，系统中是否存在该Deployment？" + (deployment == null? "否": "是"));
        if (deployment != null)
            System.out.println(deployment);
        this.deploymentFacade.delete(K8S_Constants.DEFAULT, "php-apache");
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println("---------------------");

        Service service = this.serviceFacade.query(namespace, name);
        System.out.println("Service删除前，系统中是否存在该Service？" + (service == null? "否": "是"));
        if (service != null)
            System.out.println(service);
        System.out.println("开始执行删除Service操作");
        Boolean serviceFlag = this.serviceFacade.delete(namespace, name);
        System.out.println("删除Service操作完成，结果是" + (serviceFlag ? "成功！": "失败！"));
        service = this.serviceFacade.query(namespace, name);
        System.out.println("Service删除后，系统中是否存在该Service？" + (service == null? "否": "是"));
        if (service != null)
            System.out.println(service);
        this.serviceFacade.delete(K8S_Constants.DEFAULT, "php-apache");
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println("---------------------");

        Ingress ingress = this.ingressFacade.query(namespace, name);
        System.out.println("Ingress删除前，系统中是否存在该Ingress？" + (ingress == null? "否": "是"));
        if (ingress != null)
            System.out.println(ingress);
        System.out.println("开始执行删除Ingress操作");
        Boolean ingressFlag = this.ingressFacade.delete(namespace, name);
        System.out.println("删除Ingress操作完成，结果是" + (ingressFlag ? "成功！": "失败！"));
        ingress = this.ingressFacade.query(namespace, name);
        System.out.println("Ingress删除后，系统中是否存在该Ingress？" + (ingress == null? "否": "是"));
        if (ingress != null)
            System.out.println(ingress);
        this.ingressFacade.delete(K8S_Constants.DEFAULT, "php-apache");
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println("---------------------");
        if (deploymentFlag && serviceFlag && ingressFlag)
            return "成功啦！";
        else
            return "失败了。。。";
    }

    @RequestMapping("/createYaml")
    public String createByYaml() {
        KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient();

        Deployment deployment = kubernetesClient.apps().deployments().load(getClass().getResourceAsStream("/yamls/hpa-deploy.yaml")).get();
        HorizontalPodAutoscaler horizontalPodAutoscaler = kubernetesClient.autoscaling().horizontalPodAutoscalers().load(getClass().getResourceAsStream("/yamls/hpa-scale-v2beta1.yaml")).get();
        Service service = kubernetesClient.services().load(getClass().getResourceAsStream("/yamls/hpa-service.yaml")).get();
        Ingress ingress = kubernetesClient.extensions().ingresses().load(getClass().getResourceAsStream("/yamls/hpa-ingress.yaml")).get();
        kubernetesClient.apps().deployments().create(deployment);
        kubernetesClient.autoscaling().horizontalPodAutoscalers().create(horizontalPodAutoscaler);
        kubernetesClient.services().create(service);
        /*
        The client is using resource type 'ingresses' with unstable version 'v1beta1'
         */
        kubernetesClient.extensions().ingresses().create(ingress);
        return "成功啦！";
    }

    @RequestMapping("/watch")
    public String testWatch() {
        /*
        添加监控
         */
        KubernetesClient kubernetesClient = ConfigUtil.initKubernetesClient();

        while (true) {
            Watch watch = kubernetesClient.apps().deployments().inNamespace("default").withName("php-apache").watch(new Watcher<Deployment>() {
                @Override
                public void eventReceived(Watcher.Action action, Deployment resource) {
                    if (Action.DELETED.equals(action) || Action.MODIFIED.equals(action)) {
                        System.out.println("哈哈哈哈哈！！！An event happens, the action is " + action.name() + " and the deployment's namespace is " + resource.getMetadata().getNamespace() + " and name is " + resource.getMetadata().getName());
                    }
                }

                @Override
                public void onClose(KubernetesClientException cause) {
                    System.out.println("watch closed...");
                }
            });
        }
//        return "hello";
    }
}
