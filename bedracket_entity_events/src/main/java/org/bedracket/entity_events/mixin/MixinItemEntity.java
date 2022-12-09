package org.bedracket.entity_events.mixin;

import net.minecraft.entity.ItemEntity;
import org.bedracket.entity_events.event.item.ItemTickEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class MixinItemEntity {

    @Inject(method = "tick", at = @At("TAIL"))
    private void callItemTickEvent(CallbackInfo ci) {
        BedRacket.EVENT_BUS.post(ItemTickEvent.class, new ItemTickEvent(((ItemEntity) (Object) this)));
    }
}
