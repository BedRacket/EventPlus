package org.bedracket.entity_events.event.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.bedracket.eventbus.event.Cancellable;

public class PlayerLevelUpEvent extends PlayerEvent implements Cancellable {

    private final World world;
    private boolean cancel;

    public PlayerLevelUpEvent(PlayerEntity player, World world) {
        super(player);
        this.world = world;
    }

    public World getWorld() {
        return world;
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
