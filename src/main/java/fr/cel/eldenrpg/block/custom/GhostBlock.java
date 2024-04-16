package fr.cel.eldenrpg.block.custom;

import net.minecraft.world.level.block.Block;

public class GhostBlock extends Block {

    public GhostBlock(Block block) {
        super(Properties.copy(block).noOcclusion().noLootTable().noCollission());
    }

}