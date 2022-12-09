package org.bedracket.entity_events.event.item;

import net.minecraft.entity.ItemEntity;
import org.bedracket.entity_events.event.EntityEvent;

/**
 * Base class for all {@link ItemEntity} events. Contains a reference to the
 * ItemEntity of interest. For most ItemEntity events, there's little to no
 * additional useful data from the firing method that isn't already contained
 * within the ItemEntity instance.
 */
public class ItemEvent extends EntityEvent {
    private final ItemEntity itemEntity;

    /**
     * Creates a new event for an {@link ItemEntity}.
     *
     * @param itemEntity The ItemEntity for this event
     */
    public ItemEvent(ItemEntity itemEntity) {
        super(itemEntity);
        this.itemEntity = itemEntity;
    }

    /**
     * The relevant {@link ItemEntity} for this event.
     */
    @Override
    public ItemEntity getEntity() {
        return itemEntity;
    }
}
