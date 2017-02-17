package com.axon.listener;

import com.axon.cqrs.api.UserCreatedEvent;
import com.axon.cqrs.api.UserNameUpdatedEvent;
import com.axon.listener.domain.UserQ;
import com.axon.listener.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chenlang on 2017/2/9.
 */
@Component
@Slf4j
public class UserListener {

    @Autowired
    private UserRepository userRepository;

    @EventHandler
    public  void hanndler(UserCreatedEvent event){
        log.info("UserCreatedEvent :{} ",event);
        UserQ u = UserQ.builder()
                .add(event.getAdd())
                .company(event.getCompany())
                .id(event.getId())
                .name(event.getName())
//                .version(event.getVersion())
                .build();

        userRepository.save(u);
        log.info("添加成功");
    }


    @EventHandler
    public  void hanndler(UserNameUpdatedEvent event){
        log.info("UserNameUpdatedEvent :{} ",event);
        UserQ dbData =  userRepository.findOne(event.getId());
        dbData.setName(event.getName());
//        dbData.setVersion(event.getVersion());

        userRepository.save(dbData);
        log.info("修改成功");
    }

}
