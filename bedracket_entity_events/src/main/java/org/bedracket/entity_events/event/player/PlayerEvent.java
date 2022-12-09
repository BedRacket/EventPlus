package org.bedracket.entity_events.event.player;

import net.minecraft.entity.player.PlayerEntity;
import org.bedracket.eventbus.event.Event;

public class PlayerEvent extends Event {

    private final PlayerEntity player;

    public PlayerEvent(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
