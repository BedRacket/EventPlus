package org.bedracket.entity_events.mixin;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.bedracket.entity_events.event.player.EntityTamedEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@EventInfo(events = "EntityTamedEvent")
@Mixin(TameableEntity.class)
public abstract class MixinTameableEntity {

    @Inject(method = "setOwner", at=@At("HEAD"), cancellable = true)
    private void callEntityTamedEvent(PlayerEntity player, CallbackInfo ci) throws EventException {
        TameableEntity entity = ((TameableEntity) (Object) this);
        if (player != null){
            EntityTamedEvent bedracketEvent = (EntityTamedEvent) BedRacket.EVENT_BUS.post(
                    EntityTamedEvent.class, new EntityTamedEvent(entity.getWorld(),
                            player, entity));
            if (bedracketEvent.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
