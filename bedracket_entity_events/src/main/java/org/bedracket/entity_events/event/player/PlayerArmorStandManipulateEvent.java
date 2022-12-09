package org.bedracket.entity_events.event.player;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.bedracket.eventbus.event.Cancellable;
import org.jetbrains.annotations.NotNull;


/**
 * Called when a player interacts with an armor stand and will either swap, retrieve or
 * place an item.
 */
public class PlayerArmorStandManipulateEvent extends PlayerEvent implements Cancellable {

    private final ItemStack playerItem;
    private final ItemStack armorStandItem;
    private final EquipmentSlot slot;
    private final ArmorStandEntity clickedEntity;
    private boolean cancelled = false;

    public PlayerArmorStandManipulateEvent(@NotNull PlayerEntity player, @NotNull final ArmorStandEntity clickedEntity, @NotNull final ItemStack playerItem, @NotNull final ItemStack armorStandItem, @NotNull final EquipmentSlot slot) {
        super(player);
        this.playerItem = playerItem;
        this.armorStandItem = armorStandItem;
        this.slot = slot;
        this.clickedEntity = clickedEntity;
    }

    public ItemStack getPlayerItem() {
        return playerItem;
    }

    /**
     * Returns the item held by the armor stand.
     * <p>
     * If this item is empty and the player's item is also empty, there will be no
     * transaction between the player and the armor stand. If the player's item is empty
     * but the armor stand item is not, then the player will obtain the armor stand item.
     * In the case that the player's item is not empty but the armor stand item is empty,
     * the player's item will be placed on the armor stand. If both items are not empty,
     * the items will be swapped.
     * <p>
     * In the case that the event is cancelled the original items will remain the same.
     * @return the item held by the armor stand.
     */
    @NotNull
    public ItemStack getArmorStandItem() {
        return armorStandItem;
    }

    /**
     * Returns the raw item slot of the armor stand in this event.
     *
     * @return the index of the item obtained or placed of the armor stand.
     */
    @NotNull
    public EquipmentSlot getSlot() {
        return slot;
    }

    @NotNull
    public ArmorStandEntity getClickedEntity() {
        return clickedEntity;
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
