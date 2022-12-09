package org.bedracket.entity_events.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.bedracket.entity_events.event.living.LivingJumpEvent;
import org.bedracket.entity_events.event.living.LivingTickEvent;
import org.bedracket.entity_events.event.living.LivingVisibilityEvent;
import org.bedracket.eventbus.event.BedRacket;
import org.bedracket.eventbus.event.EventInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@EventInfo(events = "LivingTickEvent, LivingJumpEvent, LivingVisibilityEvent")
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "tick", at = @At("HEAD"))
    private void callLivingTickEvent(CallbackInfo ci) {
        BedRacket.EVENT_BUS.post(LivingTickEvent.class, new LivingTickEvent(((LivingEntity) (Object) this)));
    }

    @Inject(method = "jump", at = @At("TAIL"))
    private void callLivingJumpEvent(CallbackInfo ci) {
        BedRacket.EVENT_BUS.post(LivingJumpEvent.class, new LivingJumpEvent(((LivingEntity) (Object) this)));
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("TAIL"))
    private void callLivingVisibilityEvent(Entity entity, CallbackInfoReturnable<Double> cir) {
        double prefix$d = 1.0;
        if (((LivingEntity) (Object) this).isSneaky()) {
            prefix$d *= 0.8;
        }

        if (((LivingEntity) (Object) this).isInvisible()) {
            float prefix$f = ((LivingEntity) (Object) this).getArmorVisibility();
            if (prefix$f < 0.1F) {
                prefix$f = 0.1F;
            }

            prefix$d *= 0.7 * (double)prefix$f;
        }

        if (entity != null) {
            ItemStack itemStack = ((LivingEntity) (Object) this).getEquippedStack(EquipmentSlot.HEAD);
            EntityType<?> entityType = entity.getType();
            if (entityType == EntityType.SKELETON && itemStack.isOf(Items.SKELETON_SKULL)
                    || entityType == EntityType.ZOMBIE && itemStack.isOf(Items.ZOMBIE_HEAD)
                    || entityType == EntityType.PIGLIN && itemStack.isOf(Items.PIGLIN_HEAD)
                    || entityType == EntityType.PIGLIN_BRUTE && itemStack.isOf(Items.PIGLIN_HEAD)
                    || entityType == EntityType.CREEPER && itemStack.isOf(Items.CREEPER_HEAD)) {
                prefix$d *= 0.5;
            }
        }

        LivingVisibilityEvent bedracketEvent = (LivingVisibilityEvent) BedRacket.EVENT_BUS.post(LivingVisibilityEvent.class,
                new LivingVisibilityEvent(((LivingEntity) (Object) this), entity, prefix$d));
        cir.setReturnValue(Math.max(0, bedracketEvent.getVisibilityModifier()));
    }

}
