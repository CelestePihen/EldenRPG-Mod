package fr.cel.eldenrpg.entity.goal;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.entity.EldenNPC;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class EldenNPCMoveGoal extends Goal {

    private final EldenNPC mob;
    private final BlockPos[] path;
    private final double speed;

    private int currentPathIndex;

    public EldenNPCMoveGoal(EldenNPC mob, BlockPos[] path, double speed) {
        this.mob = mob;
        this.path = path;
        this.speed = speed;

        this.currentPathIndex = 0;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return currentPathIndex < path.length;
    }

    @Override
    public void tick() {
        if (currentPathIndex < path.length) {
            BlockPos target = path[currentPathIndex];

            mob.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), speed);

            EldenRPGMod.LOGGER.error("distanceToSqr: " + mob.distanceToSqr(target.getX(), target.getY(), target.getZ()) + " x: " + target.getX() + " y: " + target.getY() + " z: " + target.getZ());
            if (mob.distanceToSqr(target.getX(), target.getY(), target.getZ()) < 4.0) {
                currentPathIndex++;

                if (currentPathIndex >= path.length) {
                    currentPathIndex = 0;
                }
            }
        }
    }

}