package fr.cel.eldenrpg.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;

import java.util.ArrayList;
import java.util.List;

public class TestGoal extends MoveToBlockGoal {

    private final List<BlockPos> pathPoints;
    private int currentPathIndex;

    public TestGoal(PathfinderMob pMob, double pSpeedModifier, List<BlockPos> pathPoints) {
        super(pMob, pSpeedModifier, 32);
        this.pathPoints = new ArrayList<>(pathPoints);
        this.currentPathIndex = 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos blockPos) {
        return blockPos == this.pathPoints.get(this.currentPathIndex);
    }

    @Override
    protected boolean findNearestBlock() {
        if (this.pathPoints.isEmpty()) {
            return false;
        }

        this.blockPos = this.pathPoints.get(this.currentPathIndex);
        this.currentPathIndex = (this.currentPathIndex + 1) % this.pathPoints.size();

        this.nextStartTick = this.nextStartTick(this.mob);
        this.tryTicks = 0;

        return true;
    }

    @Override
    protected void moveMobToBlock() {
        if (this.pathPoints.isEmpty()) {
            return;
        }

        BlockPos targetPos = this.pathPoints.get(this.currentPathIndex);
        this.mob.getNavigation().moveTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D, this.speedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.pathPoints.isEmpty();
    }

}
