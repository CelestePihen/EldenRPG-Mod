package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.custom.npcs.BlacksmithEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

public class NPCCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("summonnpc").requires(source -> source.hasPermissionLevel(3))
                // Fait apparaitre tous les NPCs
                .then(CommandManager.literal("spawnall").executes(source -> spawnAllNPCs(source.getSource())))
        );
    }

    private static int spawnAllNPCs(ServerCommandSource source) {
        ServerWorld world = source.getWorld();

        // Blacksmith
        world.spawnEntity(new BlacksmithEntity(ModEntities.BLACKSMITH_NPC, world));

        return 1;
    }

}