package org.bedracket.entity_events.event;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityLeaveWorldEvent extends EntityEvent {

    private final World world;

    public EntityLeaveWorldEvent(Entity entity, World world) {
        super(entity);
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
