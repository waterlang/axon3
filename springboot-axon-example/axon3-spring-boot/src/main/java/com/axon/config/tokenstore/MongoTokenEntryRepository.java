package com.axon.config.tokenstore;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by chenlang on 2017/2/16.
 */
public interface MongoTokenEntryRepository extends MongoRepository<MongoTokenEntry,String> {

    public MongoTokenEntry findByProcessorNameAndSegment(String processorName,int segment);
}
