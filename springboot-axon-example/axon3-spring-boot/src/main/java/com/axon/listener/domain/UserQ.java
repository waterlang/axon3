package com.axon.listener.domain;

import com.axon.cqrs.api.CreateUserCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chenlang on 2017/2/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQ {
    private String id;
    private String name;
    private String company;
    private String add;
    private Long version;
}
