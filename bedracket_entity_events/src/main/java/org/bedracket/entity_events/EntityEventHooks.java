package org.bedracket.entity_events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.bedracket.entity_events.event.EntityJoinWorldEvent;
import org.bedracket.entity_events.event.living.LivingHealEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;

public class EntityEventHooks {

    public static boolean onEntityJoinWorld(Entity entity, World world) throws EventException {
        return BedRacket.EVENT_BUS.post(EntityJoinWorldEvent.class, new EntityJoinWorldEvent(entity, world)).isAsynchronous();
    }

    public static float onLivingHeal(LivingEntity entity, float amount) throws EventException {
        LivingHealEvent bedracketEvent =
                (LivingHealEvent) BedRacket.EVENT_BUS
                        .post(LivingHealEvent.class,
                                new LivingHealEvent(entity, amount));
        return !bedracketEvent.isCancelled() ? 0 : bedracketEvent.getAmount();
    }

}
