package org.bedracket.entity_events.event.living;

import net.minecraft.entity.LivingEntity;

public class LivingTickEvent extends LivingEvent {

    public LivingTickEvent(LivingEntity livingEntity) {
        super(livingEntity);
    }

}
