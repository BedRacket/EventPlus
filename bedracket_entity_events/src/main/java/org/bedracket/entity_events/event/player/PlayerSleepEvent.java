package org.bedracket.entity_events.event.player;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.bedracket.eventbus.event.Cancellable;

public class PlayerSleepEvent extends PlayerEvent implements Cancellable {

    private final BlockPos bedPos;
    private final long time;
    private boolean cancel;

    public PlayerSleepEvent(PlayerEntity player, BlockPos bedPos, long time) {
        super(player);
        this.bedPos = bedPos;
        this.time = time;
    }

    public BlockPos getBedPos() {
        return bedPos;
    }

    public long getTime() {
        return time;
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
