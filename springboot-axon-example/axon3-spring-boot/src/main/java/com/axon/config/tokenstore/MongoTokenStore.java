package com.axon.config.tokenstore;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.UnableToClaimTokenException;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.axonframework.serialization.SerializedObject;
import org.axonframework.serialization.Serializer;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.temporal.TemporalAmount;

/**
 * Created by chenlang on 2017/2/9.
 */
public class MongoTokenStore implements TokenStore {

    private MongoTokenEntryRepository repository;

    private final TemporalAmount claimTimeout;

    private final String nodeId;

    private  Class contentType ;

    private  SerializedObject serializedToken;

    private Serializer serializer;

    public MongoTokenStore(MongoTokenEntryRepository repository,Serializer serializer){
        this(repository,serializer, Duration.ofSeconds(10L), ManagementFactory.getRuntimeMXBean().getName());
    }

    public MongoTokenStore( MongoTokenEntryRepository repository,Serializer serializer,TemporalAmount claimTimeout,String nodeId){
        this.repository = repository;
        this.claimTimeout = claimTimeout;
        this.nodeId = nodeId;
        this.serializer = serializer;
    }


    @Override
    public void storeToken(TrackingToken trackingToken, String processorName, int segment) throws UnableToClaimTokenException {
        MongoTokenEntry tokenEntry = this.loadOrCreateToken(processorName, segment);
        updateTokenEntry(tokenEntry,trackingToken);
    }

    @Override
    public TrackingToken fetchToken(String processorName, int segment) throws UnableToClaimTokenException {
        return this.loadOrCreateToken(processorName, segment).getToken(this.serializer);
    }

    @Override
    public void releaseClaim(String processorName, int segment) {
        MongoTokenEntry dbData = repository.findByProcessorNameAndSegment(processorName,segment);
        if(dbData != null){
            dbData.setTimestamp(TokenEntry.clock.instant().toString());
            dbData.setProcessorName(processorName);
            dbData.setSegment(Integer.valueOf(segment));
            repository.save(dbData);
        }else{
            throw new UnableToClaimTokenException("Unable to extend the claim on token for processor \'" + processorName + "[" + segment + "]\'. It is either claimed by another process, or there is no such token.");
        }

    }

    private void updateTokenEntry(MongoTokenEntry mongoTokenEntry,TrackingToken token){
        mongoTokenEntry.updateToken(token, this.serializer,byte[].class);
        repository.save(mongoTokenEntry);
    }


    private MongoTokenEntry loadOrCreateToken(String processorName,int segment){
        MongoTokenEntry mongoTokenEntry =   repository.findByProcessorNameAndSegment(processorName,segment);
        if(mongoTokenEntry == null ) {
            mongoTokenEntry = new MongoTokenEntry(this.serializer,processorName,segment,null,byte[].class);
            mongoTokenEntry.claim(this.nodeId,this.claimTimeout);
            repository.save(mongoTokenEntry);
        }else if(!mongoTokenEntry.claim(this.nodeId, this.claimTimeout)){
            throw new UnableToClaimTokenException(String.format("Unable to claim token \'%s[%s]\'. It is owned by \'%s\'", new Object[]{mongoTokenEntry.getProcessorName(), Integer.valueOf(mongoTokenEntry.getSegment()), mongoTokenEntry.getOwner()}));
        }

        return mongoTokenEntry;
    }



}
