package org.bedracket.entity_events.event;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.bedracket.eventbus.event.Cancellable;

public class EntityJoinWorldEvent extends EntityEvent implements Cancellable {

    private boolean cancel = false;

    private final World world;
    private final boolean loadedFromDisk;

    public EntityJoinWorldEvent(Entity entity, World world) {
        this(entity, world, false);
    }

    public EntityJoinWorldEvent(Entity entity, World world, boolean loadedFromDisk) {
        super(entity);
        this.world = world;
        this.loadedFromDisk = loadedFromDisk;
    }

    /**
     * {@return the level that the entity is set to join}
     */
    public World getWorld() {
        return world;
    }

    public boolean loadedFromDisk() {
        return loadedFromDisk;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
