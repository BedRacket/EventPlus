package org.bedracket.entity_events.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.bedracket.entity_events.event.EntityDeathEvent;
import org.bedracket.entity_events.event.EntityMoveEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@EventInfo(events = "EntityDeathEvent, EntityMoveEvent")
@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "remove",at=@At("HEAD"),cancellable = true)
    public void callEntityDeathEvent(CallbackInfo ci){
        EntityDeathEvent bedracketEvent =
                (EntityDeathEvent) BedRacket.EVENT_BUS.post(EntityDeathEvent.class,
                new EntityDeathEvent(((Entity) (Object) this),
                        ((Entity) (Object) this).getWorld(),
                        ((Entity) (Object) this).getWorld().getClosestPlayer((Entity)(Object)this, 32D),
                        ((Entity)(Object)this).getBlockPos()));
        if (bedracketEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "move",at=@At("HEAD"),cancellable = true)
    public void beforeMove(MovementType movementType, Vec3d vec3d, CallbackInfo ci){
        EntityMoveEvent bedracketEvent =
                (EntityMoveEvent) BedRacket.EVENT_BUS.post(EntityMoveEvent.class,
                new EntityMoveEvent(((Entity) (Object) this),
                        ((Entity) (Object) this).getWorld(), vec3d));
        if (bedracketEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
