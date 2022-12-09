package org.bedracket.entity_events.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.bedracket.entity_events.event.player.RightClickBlockEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@EventInfo(events = "RightClickBlockEvent")
@Mixin(ServerPlayerInteractionManager.class)
public abstract class MixinServerPlayerInteractionManager {

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void callRightClickBlockEvent(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        RightClickBlockEvent bedracketEvent = (RightClickBlockEvent) BedRacket.EVENT_BUS.post(RightClickBlockEvent.class,
                new RightClickBlockEvent(player, hand, hitResult.getBlockPos(), hitResult));
        if (bedracketEvent.isCancelled()) cir.setReturnValue(bedracketEvent.getCancellationResult());
    }
}
