package org.bedracket.entity_events.mixin;

import net.minecraft.entity.Entity;
import org.bedracket.entity_events.event.EntityLeaveWorldEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@EventInfo(events = "EntityLeaveWorldEvent")
@Mixin(targets = "net.minecraft.server.world.ServerWorld.ServerEntityHandler")
public abstract class MixinServerEntityHandler {

    @Inject(method = "stopTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void callEntityLeaveWorldEvent(Entity entity, CallbackInfo ci) throws EventException {
        BedRacket.EVENT_BUS.post(EntityLeaveWorldEvent.class, new EntityLeaveWorldEvent(entity, entity.getWorld()));
    }
}
