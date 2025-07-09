package fr.cel.eldenrpg.block.custom;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class GhostBlock extends Block {

    /**
     * Classe du Ghost Block / Bloc où l'on peut passer à travers.
     * Ici, on va copier tous les paramètres du bloc (genre la lumière qu'il émet, sa couleur sur une carte, ses sons).
     * On va aussi lui ajouter le fait que l'on puisse passer à travers, qu'on "voit" quand on est à l'intérieur, et qu'il ne puisse pas drop sous forme de bloc.
     * @param blockName Le nom du bloc de base
     * @param block Le {@link net.minecraft.block.Block} à copier
     */
    public GhostBlock(String blockName, Block block) {
        super(Settings.copy(block).noCollision().dropsNothing()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(EldenRPG.MOD_ID, blockName + "_ghost_block")))
                .allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)
        );
    }

}