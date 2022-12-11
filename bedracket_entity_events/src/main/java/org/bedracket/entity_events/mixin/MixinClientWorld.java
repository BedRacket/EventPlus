package org.bedracket.entity_events.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.bedracket.entity_events.EntityEventHooks;
import org.bedracket.eventbus.event.EventException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

    @Inject(method = "addEntityPrivate", at = @At("HEAD"), cancellable = true)
    private void onEntityAdded(int id, Entity entity, CallbackInfo ci) throws EventException {
        if (EntityEventHooks.onEntityJoinWorld(entity, entity.getWorld())) {
            ci.cancel();
        }
    }
}
