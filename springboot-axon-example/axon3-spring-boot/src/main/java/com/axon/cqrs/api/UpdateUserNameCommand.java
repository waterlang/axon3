package com.axon.cqrs.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.TargetAggregateVersion;

/**
 * Created by chenlang on 2017/2/14.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserNameCommand {

    @TargetAggregateIdentifier
    private String userId;

//    @TargetAggregateVersion
//    private Long version;

    private String name;
}
