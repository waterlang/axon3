package com.axon.listener.respository;

import com.axon.listener.domain.UserQ;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by chenlang on 2017/2/9.
 */
public interface UserRepository extends MongoRepository<UserQ, String> {
}
