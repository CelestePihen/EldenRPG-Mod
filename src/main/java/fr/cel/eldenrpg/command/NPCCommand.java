package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.npcs.BlacksmithNPC;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class NPCCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("summonnpc").requires(source -> source.hasPermission(2))

                .then(Commands.literal("spawnall").executes(source -> spawnAllNPCs(source.getSource())))

                // seulement le nom et met à la position du joueur
                .then(Commands.argument("name", StringArgumentType.word()).executes(source -> spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), source.getSource().getPlayerOrException().blockPosition())))

                .then(Commands.argument("name", StringArgumentType.word()).then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(source ->
                                        spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), BlockPosArgument.getLoadedBlockPos(source, "pos")))
                        )));
    }

    private static int spawnNPC(CommandSourceStack source, String name, BlockPos pos) {
        Level level = source.getLevel();

        EldenNPC npc = new EldenNPC(ModEntities.ELDEN_NPC.get(), level);

        npc.setCustomName(Component.literal(name));
        npc.setCustomNameVisible(true);
        npc.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);

        level.addFreshEntity(npc);

        return 1;
    }

    private static int spawnAllNPCs(CommandSourceStack source) {
        Level level = source.getLevel();

        // Blacksmith
        BlacksmithNPC blacksmithNPC = new BlacksmithNPC(ModEntities.BLACKSMITH.get(), level);
        blacksmithNPC.setPos(206.5, 65, -63.5);
        level.addFreshEntity(blacksmithNPC);

        return 1;
    }

}