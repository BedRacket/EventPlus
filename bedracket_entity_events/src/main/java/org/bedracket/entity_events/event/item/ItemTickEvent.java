package org.bedracket.entity_events.event.item;

import net.minecraft.entity.ItemEntity;

public class ItemTickEvent extends ItemEvent {

    /**
     * Creates a new event for an {@link ItemEntity}.
     *
     * @param itemEntity The ItemEntity for this event
     */
    public ItemTickEvent(ItemEntity itemEntity) {
        super(itemEntity);
    }
}
