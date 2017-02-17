package com.axon.cqrs.core;

import com.axon.cqrs.api.CreateUserCommand;
import com.axon.cqrs.api.UserCreatedEvent;
import com.axon.cqrs.api.UserNameUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateVersion;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by chenlang on 2017/2/9.
 */
@Aggregate
//@Data

@NoArgsConstructor
public class User {
    @AggregateIdentifier
    private String id;

//    @TargetAggregateVersion
//    private Long version;

//    private int version;

    private String name;
    private String company;
    private String add;

    private 	String	complaint;	//	New	field





    public User(String id,String name,String company,String add) {
        apply(new UserCreatedEvent(id,name,company,add));
    }

    public void updateUserName(String id,String name){
//        apply(new UserNameUpdatedEvent(id,name));//old
        apply(new UserNameUpdatedEvent(id,name,"aaa"));
    }


    @EventHandler
    private void handle(UserCreatedEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.company = event.getCompany();
        this.add = event.getAdd();
//        this.version = event.getVersion();
    }

    @EventSourcingHandler
    private void handle(UserNameUpdatedEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.complaint = event.getComplaint();
//        this.version = event.getVersion();

    }


}
