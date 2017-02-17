package com.axon.cqrs.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.TargetAggregateVersion;

/**
 * Created by chenlang on 2017/2/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private String userId;
    private String name;
    private String company;
    private String add;
}
