package com.axon.replay;

        import com.axon.cqrs.api.UserNameUpdatedEvent;
        import org.axonframework.config.Configurer;
        import org.axonframework.config.DefaultConfigurer;
        import org.axonframework.eventhandling.EventHandler;
        import org.axonframework.eventhandling.SimpleEventBus;
        import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
        import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
        import org.springframework.stereotype.Component;

        import java.lang.management.ManagementFactory;

/**
 * Created by chenlang on 2017/2/15.
 */
@Component
public class UserReplay {

    @EventHandler
    public void hannd(UserNameUpdatedEvent event){
        System.out.println("333333333333333333333333333333333");
    }
}
