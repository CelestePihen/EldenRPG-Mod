package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.entity.custom.CatacombCarcassEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class BossArea extends Area<EntityType<?>> {

    private final BlockPos spawn;

    public BossArea(EntityType<?> entity, BlockPos spawn, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(entity, x1, y1, z1, x2, y2, z2);
        this.spawn = spawn;
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        if (isBoss(player)) return;
        player.getWorld().spawnEntity(getObject().spawn(player.getWorld(), spawn, SpawnReason.MOB_SUMMONED));
    }

    private boolean isBoss(ServerPlayerEntity player) {
        return !player.getWorld().getEntitiesByClass(CatacombCarcassEntity.class, getBox(), EntityPredicates.VALID_LIVING_ENTITY).isEmpty();
    }

}