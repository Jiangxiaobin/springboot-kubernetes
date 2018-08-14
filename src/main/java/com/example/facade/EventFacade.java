package com.example.facade;

import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.client.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component("eventFacade")
public class EventFacade {

    public void watch(String namespace, String name) throws InterruptedException {
        final CountDownLatch closeLatch = new CountDownLatch(1);
        Config config = new ConfigBuilder().build();
        try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
            try (Watch watch = client.replicationControllers().inNamespace("default").withName("test").watch(new Watcher<ReplicationController>() {
                @Override
                public void eventReceived(Action action, ReplicationController resource) {
                    System.out.println(action + " " + resource.getMetadata().getResourceVersion());
                }

                @Override
                public void onClose(KubernetesClientException e) {
                    System.out.println("Watcher onClose");
                    if (e != null) {
                        e.printStackTrace();
                        closeLatch.countDown();
                    }
                }
            })) {
                closeLatch.await(10, TimeUnit.SECONDS);
            } catch (KubernetesClientException | InterruptedException e) {
                System.out.println("Could not watch resources");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(60000L);
    }
}
