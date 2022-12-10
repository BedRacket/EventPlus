package org.bedracket.entity_events.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.bedracket.entity_events.event.player.PlayerArmorStandManipulateEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventException;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@EventInfo(events = "PlayerArmorStandManipulateEvent")
@Mixin(ArmorStandEntity.class)
public class MixinArmorStandEntity {

    @Inject(method = "equip", cancellable = true,at = @At(value = "INVOKE", target =
            "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"))
    public void callPlayerArmorStandManipulateEvent(PlayerEntity player, EquipmentSlot slot, ItemStack stack, Hand hand, CallbackInfoReturnable<Boolean> cir) throws EventException {
        PlayerArmorStandManipulateEvent bedracketEvent =
                (PlayerArmorStandManipulateEvent) BedRacket.EVENT_BUS.post(PlayerArmorStandManipulateEvent.class,
                new PlayerArmorStandManipulateEvent(player, ((ArmorStandEntity) (Object) this),
                        player.getStackInHand(hand), stack, slot));
        if (bedracketEvent.isCancelled()) {
            cir.setReturnValue(true);
        }

    }

}
