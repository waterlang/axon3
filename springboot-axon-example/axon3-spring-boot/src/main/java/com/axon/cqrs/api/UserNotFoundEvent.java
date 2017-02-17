package com.axon.cqrs.api;

import lombok.Value;

/**
 * Created by chenlang on 2017/2/14.
 */
@Value
public class UserNotFoundEvent {
    private String id;
}
