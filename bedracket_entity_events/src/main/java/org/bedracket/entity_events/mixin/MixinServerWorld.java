package org.bedracket.entity_events.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;
import org.bedracket.entity_events.event.EntityJoinWorldEvent;
import org.bedracket.entity_events.event.item.ItemSpawnEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@EventInfo(events = "EntityJoinWorldEvent, ItemSpawnEvent")
@Mixin(ServerWorld.class)
public abstract class MixinServerWorld {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void callEntityJoinWorldEvent(Entity entity, CallbackInfoReturnable<Boolean> cir) throws EventException {
        EntityJoinWorldEvent bedracketEvent =
                (EntityJoinWorldEvent) BedRacket.EVENT_BUS.post(
                EntityJoinWorldEvent.class,
                new EntityJoinWorldEvent(entity, entity.getWorld()));
        if (bedracketEvent.isCancelled()) {
           cir.cancel();
        }
    }

    @Inject(method = "spawnEntity", at = @At("HEAD"))
    private void callItemEntitySpawnEvent(Entity entity, CallbackInfoReturnable<Boolean> cir) throws EventException {
        if (entity instanceof ItemEntity) {
            BedRacket.EVENT_BUS.post(ItemSpawnEvent.class, new ItemSpawnEvent((ItemEntity) entity));
        }
    }

}
