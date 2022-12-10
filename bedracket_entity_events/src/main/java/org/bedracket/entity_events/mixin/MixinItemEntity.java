package org.bedracket.entity_events.mixin;

import net.minecraft.entity.ItemEntity;
import org.bedracket.entity_events.event.item.ItemTickEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@EventInfo(events = "ItemTickEvent")
@Mixin(ItemEntity.class)
public class MixinItemEntity {

    @Inject(method = "tick", at = @At("TAIL"))
    private void callItemTickEvent(CallbackInfo ci) throws EventException {
        BedRacket.EVENT_BUS.post(ItemTickEvent.class, new ItemTickEvent(((ItemEntity) (Object) this)));
    }
}
