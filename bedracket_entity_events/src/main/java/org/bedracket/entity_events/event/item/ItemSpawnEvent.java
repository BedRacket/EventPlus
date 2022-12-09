package org.bedracket.entity_events.event.item;

import net.minecraft.entity.ItemEntity;
import org.bedracket.eventbus.event.Cancellable;

public class ItemSpawnEvent extends ItemEvent implements Cancellable {

    private boolean canceled;

    /**
     * Creates a new event for an {@link ItemEntity}.
     *
     * @param itemEntity The ItemEntity for this event
     */
    public ItemSpawnEvent(ItemEntity itemEntity) {
        super(itemEntity);
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        canceled = cancel;
    }
}
