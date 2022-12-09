package org.bedracket.entity_events.event.living;

import net.minecraft.entity.LivingEntity;

public class LivingJumpEvent extends LivingEvent {

    public LivingJumpEvent(LivingEntity livingEntity) {
        super(livingEntity);
    }
}
