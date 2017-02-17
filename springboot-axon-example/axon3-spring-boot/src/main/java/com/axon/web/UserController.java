package com.axon.web;

import com.axon.cqrs.api.CreateUserCommand;
import com.axon.cqrs.api.UpdateUserNameCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by chenlang on 2017/2/10.
 */

@RestController
public class UserController {

//    @Autowired
//    CommandBus commandBus;
    @Autowired
    CommandGateway commandGateway;




    @RequestMapping("/aa")
    public String aa(){
        CreateUserCommand command = new CreateUserCommand();
        command.setAdd("成都");
        command.setCompany("长虹");
        command.setName("chenlang");
        command.setUserId(UUID.randomUUID().toString());
        commandGateway.send(GenericCommandMessage.asCommandMessage(command));

        return "success";
    }

    @RequestMapping("/user/{id}/{name}/{version}")
    public String bb(@PathVariable  String id ,@PathVariable  String name,@PathVariable  Long version){
        UpdateUserNameCommand command = new UpdateUserNameCommand();

        command.setName(name);
        command.setUserId(id);
//        command.setVersion(version);
        String a = commandGateway.sendAndWait(GenericCommandMessage.asCommandMessage(command));
        System.out.println("------"+a);
        return "success";
    }
}
