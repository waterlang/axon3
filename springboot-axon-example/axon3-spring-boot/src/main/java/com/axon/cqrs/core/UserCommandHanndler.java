package com.axon.cqrs.core;

import com.axon.cqrs.api.CreateUserCommand;
import com.axon.cqrs.api.UpdateUserNameCommand;
import com.axon.cqrs.api.UserNotFoundEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

/**
 * Created by chenlang on 2017/2/9.
 */
@Component
@Slf4j
public class UserCommandHanndler {

    @Autowired
    Repository<User> userRepository;

    @Autowired
    EventBus eventBus;

    @CommandHandler
    public void hanndle(CreateUserCommand createUserCommand){
        try{
            log.info("createUserCommand:{}",createUserCommand);
//          Aggregate<User> user =   userRepository.load(createUserCommand.getUserId());
            userRepository.newInstance(()-> new User(createUserCommand.getUserId(),createUserCommand.getName(),createUserCommand.getCompany(),createUserCommand.getAdd()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @CommandHandler
    public String  hanndle(UpdateUserNameCommand updateUserNameCommand){
        try{
            log.info("updateUserNameCommand:{}",updateUserNameCommand);
            Aggregate<User> userAggregate =   userRepository.load(updateUserNameCommand.getUserId());
            userAggregate.execute(user -> user.updateUserName(updateUserNameCommand.getUserId(),updateUserNameCommand.getName()));
        }catch (AggregateNotFoundException e){
            e.printStackTrace();
//            eventBus.publish(asEventMessage(new UserNotFoundEvent(updateUserNameCommand.getUserId())));
            return "errors";
        }

        return updateUserNameCommand.getUserId();
    }

}
