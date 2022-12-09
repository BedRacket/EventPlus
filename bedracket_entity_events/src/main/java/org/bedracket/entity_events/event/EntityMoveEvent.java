package org.bedracket.entity_events.event;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.bedracket.eventbus.event.Cancellable;

public class EntityMoveEvent extends EntityEvent implements Cancellable {

    private final World world;
    private final Vec3d direction;
    private boolean cancelled;

    public EntityMoveEvent(Entity entity, World world, Vec3d direction) {
        super(entity);
        this.world = world;
        this.direction = direction;
    }

    public World getWorld() {
        return world;
    }

    public Vec3d getDirection() {
        return direction;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
