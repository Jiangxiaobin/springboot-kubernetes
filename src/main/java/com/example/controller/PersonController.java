package com.example.controller;

import com.example.domain.PersonDO;
import com.example.facade.DeploymentFacade;
import com.example.facade.ServiceAccountFacade;
import com.example.facade.ServiceFacade;
import com.example.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by FlySheep on 17/3/25.
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private ServiceAccountFacade serviceAccountFacade;
    @Autowired
    private ServiceFacade serviceFacade;
    @Autowired
    private DeploymentFacade deploymentFacade;


    @RequestMapping("/save")
    public Integer save() {
        PersonDO personDO = new PersonDO();
        personDO.setName("张三");
        personDO.setAge(18);
        personMapper.insert(personDO);
        return personDO.getId();
    }

    @RequestMapping("/update")
    public Long update() {
        PersonDO personDO = new PersonDO();
        personDO.setId(3);
        personDO.setName("旺旺");
        personDO.setAge(12);
        return personMapper.update(personDO);
    }

    @RequestMapping("/delete")
    public Long delete() {
        return personMapper.delete(10L);
    }

    @RequestMapping("/selectById")
    public PersonDO selectById() {
        return personMapper.selectPersonById(3);
    }

    @RequestMapping("/selectAll")
    public List<PersonDO> selectAll() {
        return personMapper.selectAll();
    }

    @RequestMapping("/transaction")
    @Transactional  // 需要事务的时候加上
    public Boolean transaction() {
        delete();
        int i = 3 / 0;

        save();

        return true;
    }

    @RequestMapping("/create")
    public void testCreateNamespace() {
//        Config config = new Config();
//        KubernetesClient kubernetesClient = new DefaultKubernetesClient("http://192.168.56.101:8080");
//        Namespace ns = new Namespace();
//        ns.setApiVersion("v1");
//        ObjectMeta om = new ObjectMeta();
//        om.setName("ns-fabric8");
//        ns.setMetadata(om);
//        kubernetesClient.namespaces().create(ns);

        this.serviceAccountFacade.create();
        this.deploymentFacade.createDeployment();
        this.serviceFacade.createService();
    }

}
