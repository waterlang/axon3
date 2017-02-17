package com.axon.config.tokenstore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.axonframework.serialization.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

/**
 * Created by chenlang on 2017/2/16.
 */
@Document(collection = "mongoToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MongoTokenEntry<T> {

    public static Clock clock = Clock.systemUTC();

    @Id
    private String id;

    private String processorName;

    private int segment;

    private T  token;

    private String tokenType;

    private String timestamp;

    private String owner;

    public MongoTokenEntry(Serializer serializer, String processorName, int segment, TrackingToken token, Class<T> contentType){
        this.processorName = processorName;
        this.segment = segment;
        if(token != null){
            SerializedObject serializedToken = serializer.serialize(token, contentType);
            this.token = (T) serializedToken.getData();
            this.tokenType = serializedToken.getType().getName();
        }
    }


    public boolean claim(String owner, TemporalAmount claimTimeout) {
        if(!this.mayClaim(owner, claimTimeout)) {
            return false;
        } else {
            this.timestamp = clock.instant().toString();
            this.owner = owner;
            return true;
        }
    }




    protected final void updateToken(TrackingToken token, Serializer serializer, Class<T> contentType) {
        SerializedObject serializedToken = serializer.serialize(token, contentType);
        this.token = (T)serializedToken.getData();
        this.tokenType = serializedToken.getType().getName();
        this.timestamp = clock.instant().toString();
    }

    public boolean mayClaim(String owner, TemporalAmount claimTimeout) {
        return this.owner == null || owner.equals(this.owner) || this.expired(claimTimeout);
    }

    private boolean expired(TemporalAmount claimTimeout) {
        return this.timestamp().plus(claimTimeout).isBefore(clock.instant());
    }

    public Instant timestamp() {
        return Instant.parse(this.timestamp);
    }


    public TrackingToken getToken(Serializer serializer){
        return this.token == null?null:(TrackingToken)serializer.deserialize(this.getSerializedToken());
    }

    public SerializedObject<T> getSerializedToken() {
        return this.token == null?null:new SimpleSerializedObject(this.token, this.token.getClass(), this.getTokenType());
    }

    protected SerializedType getTokenType() {
        return new SimpleSerializedType(this.tokenType, (String)null);
    }
}
