package com.axon.config;


import com.axon.config.tokenstore.MongoTokenEntryRepository;
import com.axon.config.tokenstore.MongoTokenStore;
import com.axon.cqrs.core.User;
import com.axon.util.UserEventUpcaster;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.AbstractMongoEventStorageStrategy;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chenlang on 2017/2/9.
 */
@Configuration
@EnableConfigurationProperties(value = {MongoProperties.class})
public class AxonCustomConfiguration {

    @Autowired
    MongoProperties mongoProperties;

    @Autowired
    MongoClient mongo;

    @Autowired
    AxonConfiguration axonConfiguration;

    @Autowired
    private EventHandlingConfiguration eventHandlingConfiguration;

    @Autowired
    DataSource dataSource;

    @Autowired
    MongoTokenEntryRepository mongoTokenEntryRepository;

    @Bean
    public SingleEventUpcaster myUpcaster()	{
        return	new UserEventUpcaster();
    }

    @Bean
    EventStorageEngine mongoEventStorageEngine(Serializer serializer,  SingleEventUpcaster	myUpcaster){
        return new MongoEventStorageEngine(serializer,myUpcaster::upcast,axonMongoTemplate(),new DocumentPerEventStorageStrategy());
    }

//    @Bean
//    EventStorageEngine mongoEventStorageEngine(Serializer serializer){
//        return  new MongoEventStorageEngine(axonMongoTemplate());
//    }

    @Bean
    MongoTemplate axonMongoTemplate(){
        return  new DefaultMongoTemplate(mongo,mongoProperties.getDatabase(),"domainEvents","snapshotEvents");
    }
    @Bean
    Repository<User> getUserRepository(){
        return axonConfiguration.repository(User.class);
    }

//  replay for   Mongo
    @Bean
    TokenStore getMongoTokenStore(Serializer serializer){
        return  new MongoTokenStore(mongoTokenEntryRepository,serializer);
    }



    //  replay for jdbc
//    @Bean
//    TokenStore getJdbcTokenStore(Serializer serializer){
//        ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);
//        return  new JdbcTokenStore(connectionProvider,serializer);
//    }

    //  replay for jpa
//    @Bean
//    TokenStore getJpaTokenStore(EntityManagerProvider entityManagerProvider ,Serializer serializer){
//        return  new JpaTokenStore(entityManagerProvider,serializer);
//    }


    @PostConstruct
    public void init() {
        eventHandlingConfiguration.registerTrackingProcessor("com.axon.replay");
    }

}
