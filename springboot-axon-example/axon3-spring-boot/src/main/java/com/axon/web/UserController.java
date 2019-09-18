package com.axon.web;

import com.axon.cqrs.api.CreateUserCommand;
import com.axon.cqrs.api.UpdateUserNameCommand;
import com.axon.replay.UserReplay;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by chenlang on 2017/2/10.
 */

@Slf4j
@RestController
public class UserController {

    //    @Autowired
//    CommandBus commandBus;
    @Autowired
    CommandGateway commandGateway;


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser() {
        CreateUserCommand command = new CreateUserCommand();
        command.setAdd("成都");
        command.setCompany("长虹");
        command.setName("chenlang");
        command.setUserId(UUID.randomUUID().toString());
        commandGateway.send(GenericCommandMessage.asCommandMessage(command));

        return "success";
    }


    @RequestMapping(value = "/user/{id}/{name}/{version}", method = RequestMethod.POST)
    public String bb(@PathVariable String id, @PathVariable String name, @PathVariable Long version) {
        UpdateUserNameCommand command = new UpdateUserNameCommand();

        command.setName(name);
        command.setUserId(id);
//        command.setVersion(version);
        String result = commandGateway.sendAndWait(GenericCommandMessage.asCommandMessage(command));
        log.info("result:{}",result);
        return "success";
    }





}
