package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.GracesData;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class GraceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("grace").requires(source -> source.hasPermissionLevel(3))
                // Permet d'avoir votre liste de grâces
                .then(CommandManager.literal("list").executes(source -> getQuestList(source.getSource().getPlayerOrThrow())))

                // Permet d'avoir la liste de grâces d'un joueur
                .then(CommandManager.literal("list").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> getQuestList(EntityArgumentType.getPlayer(source, "player")))))
        );
    }

    private static int getQuestList(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.graces.list", player.getName()));

        for (long l : GracesData.getGraces((IPlayerDataSaver) player)) {
            player.sendMessage(GracesData.getGraceName(BlockPos.fromLong(l)));
        }

        return 1;
    }

}