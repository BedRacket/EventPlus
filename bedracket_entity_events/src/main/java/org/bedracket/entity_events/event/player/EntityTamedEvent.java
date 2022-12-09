package org.bedracket.entity_events.event.player;


import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.bedracket.eventbus.event.Cancellable;

public class EntityTamedEvent extends PlayerEvent implements Cancellable {

    private final World world;
    private final TameableEntity entity;
    private boolean cancel;

    public EntityTamedEvent(World world, PlayerEntity player, TameableEntity entity) {
        super(player);
        this.world = world;
        this.entity = entity;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public World getWorld() {
        return world;
    }

    public TameableEntity getEntity() {
        return entity;
    }
}