package org.bedracket.entity_events.event.player;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.bedracket.eventbus.event.Cancellable;

public class RightClickBlockEvent extends PlayerInteractionEvent implements Cancellable {

    private Result useBlock = Result.DEFAULT;
    private Result useItem = Result.DEFAULT;
    private final BlockHitResult hitVec;
    private boolean cancel;
    private final Hand hand;
    private final BlockPos pos;
    private final Block block;

    public RightClickBlockEvent(PlayerEntity player, Hand hand, BlockPos pos, BlockHitResult hitVec) {
        super(player);
        this.hitVec = hitVec;
        this.hand = hand;
        this.pos = pos;
        this.block = player.getBlockStateAtPos().getBlock();
    }

    public Result getUseBlock() {
        return useBlock;
    }

    public Result getUseItem() {
        return useItem;
    }

    public BlockHitResult getHitVec() {
        return hitVec;
    }

    public void setUseBlock(Result triggerBlock) {
        this.useBlock = triggerBlock;
    }

    public void setUseItem(Result triggerItem) {
        this.useItem = triggerItem;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
        if (cancel) {
            useBlock = Result.DENY;
            useItem = Result.DENY;
        }
    }

    public Hand getHand() {
        return hand;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Block getBlock() {
        return block;
    }

}
