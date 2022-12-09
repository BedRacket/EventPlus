package org.bedracket.entity_events.event.living;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import org.bedracket.eventbus.event.Event;

public class MobInitGoalEvent extends Event {

    private final GoalSelector goalSelector;
    private final GoalSelector targetSelector;
    private final MobEntity mob;

    public MobInitGoalEvent(MobEntity mob, GoalSelector goalSelector, GoalSelector targetSelector) {
        this.mob = mob;
        this.goalSelector = goalSelector;
        this.targetSelector = targetSelector;
    }

    public GoalSelector getGoalSelector() {
        return goalSelector;
    }

    public GoalSelector getTargetSelector() {
        return targetSelector;
    }

    public MobEntity getMob() {
        return mob;
    }
}
