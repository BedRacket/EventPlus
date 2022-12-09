package org.bedracket.entity_events.event.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerInteractionEvent extends PlayerEvent {

    private final ActionResult cancellationResult = ActionResult.PASS;

    public PlayerInteractionEvent(PlayerEntity player) {
        super(player);
    }

    public ActionResult getCancellationResult() {
        return cancellationResult;
    }
}
