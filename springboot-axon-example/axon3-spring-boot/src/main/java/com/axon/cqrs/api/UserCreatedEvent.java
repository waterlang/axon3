package com.axon.cqrs.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by chenlang on 2017/2/9.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {
    private String id;
    private String name;
    private String company;
    private String add;
//    private Long version ;
}
