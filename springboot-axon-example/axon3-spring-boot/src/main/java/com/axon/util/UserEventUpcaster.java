package com.axon.util;

import com.axon.cqrs.api.UserNameUpdatedEvent;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;

import java.util.stream.Stream;

/**
 * Created by chenlang on 2017/2/14.
 */
public class UserEventUpcaster extends SingleEventUpcaster {

    private static SimpleSerializedType targeType = new SimpleSerializedType(UserNameUpdatedEvent.class.getTypeName(),"1.0");



    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.getType().equals(targeType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.upcastPayload(
                new SimpleSerializedType(targeType.getName(),"2.0"),
                org.dom4j.Document.class,
                document	->	{
                    document.getRootElement().addElement("complaint");
                    document.getRootElement().element("complaint").setText("no	complai nt	description");	//	Default	value
                    return	document;
                }
        );
    }
}
