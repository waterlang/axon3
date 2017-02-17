package com.axon.cqrs.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.serialization.Revision;

/**
 * Created by chenlang on 2017/2/14.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Revision("2.0")
@Data
public class UserNameUpdatedEvent {
    private String id;
    private String name;
//    private Long version;
    private 	String	complaint;	//	New	field
}
