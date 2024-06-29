package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.entity.custom.npcs.BlacksmithNPC;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class NPCCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("summonnpc").requires(source -> source.hasPermissionLevel(3))

                // fait apparaitre tous les NPCs
                .then(CommandManager.literal("spawnall").executes(source -> spawnAllNPCs(source.getSource())))

                // seulement le nom et met à la position du joueur
                .then(CommandManager.argument("name", StringArgumentType.word()).executes(source -> spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), source.getSource().getPlayerOrThrow().getBlockPos())))

                // un nom et une position
                .then(CommandManager.argument("name", StringArgumentType.word()).then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                        .executes(source ->
                                spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), BlockPosArgumentType.getLoadedBlockPos(source, "pos")))
                )));
    }

    private static int spawnNPC(ServerCommandSource source, String name, BlockPos pos) {
        ServerWorld world = source.getWorld();

        EldenNPC npc = new EldenNPC(ModEntities.ELDEN_NPC, world);

        npc.setCustomName(Text.literal(name));
        npc.setCustomNameVisible(true);
        npc.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        world.spawnEntity(npc);
        return 1;
    }

    private static int spawnAllNPCs(ServerCommandSource source) {
        ServerWorld world = source.getWorld();

        // Blacksmith
        world.spawnEntity(new BlacksmithNPC(ModEntities.BLACKSMITH_NPC, world));

        return 1;
    }

}