package org.bedracket.entity_events.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.bedracket.eventbus.event.Cancellable;

public class EntityDeathEvent extends EntityEvent implements Cancellable {

    private final World world;
    private final PlayerEntity player;
    private final BlockPos pos;
    private boolean cancelled;

    public EntityDeathEvent(Entity entity, World world, PlayerEntity player, BlockPos pos) {
        super(entity);
        this.world = world;
        this.player = player;
        this.pos = pos;
    }

    public World getWorld() {
        return world;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public BlockPos getPos() {
        return pos;
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
