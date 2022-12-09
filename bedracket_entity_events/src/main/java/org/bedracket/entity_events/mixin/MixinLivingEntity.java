package org.bedracket.entity_events.mixin;

import net.minecraft.entity.LivingEntity;
import org.bedracket.entity_events.event.living.LivingJumpEvent;
import org.bedracket.entity_events.event.living.LivingTickEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@EventInfo(events = "LivingTickEvent, LivingJumpEvent")
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "tick", at = @At("HEAD"))
    private void callLivingTickEvent(CallbackInfo ci) {
        BedRacket.EVENT_BUS.post(LivingTickEvent.class, new LivingTickEvent(((LivingEntity) (Object) this)));
    }

    @Inject(method = "jump", at = @At("TAIL"))
    private void callLivingJumpEvent(CallbackInfo ci) {
        BedRacket.EVENT_BUS.post(LivingJumpEvent.class, new LivingJumpEvent(((LivingEntity) (Object) this)));
    }
}
