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

                // Donne toutes les grâces au joueur
                .then(CommandManager.literal("giveall").executes(source -> giveGraces(source.getSource().getPlayerOrThrow())))

                // Donne toutes les grâces à un joueur
                .then(CommandManager.literal("giveall").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> giveGraces(EntityArgumentType.getPlayer(source, "player")))))

                // Retire toutes les grâces au joueur
                .then(CommandManager.literal("removeall").executes(source -> removeGraces(source.getSource().getPlayerOrThrow())))

                // Retire toutes les grâces à un joueur
                .then(CommandManager.literal("removeall").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> removeGraces(EntityArgumentType.getPlayer(source, "player")))))
        );
    }



    private static int getQuestList(ServerPlayerEntity player) {
        for (long gracePos : GracesData.getPlayerGraces((IPlayerDataSaver) player)) {
            player.sendMessage(GracesData.getGraceName(BlockPos.fromLong(gracePos)));
        }

        player.sendMessage(Text.translatable("eldenrpg.command.graces.list", player.getName()));
        return 1;
    }

    private static int giveGraces(ServerPlayerEntity player) {
        GracesData.addGraces((IPlayerDataSaver) player);
        player.sendMessage(Text.translatable("eldenrpg.command.graces.giveall", player.getName()));
        return 1;
    }

    private static int removeGraces(ServerPlayerEntity player) {
        GracesData.removeGraces((IPlayerDataSaver) player);
        player.sendMessage(Text.translatable("eldenrpg.command.graces.removeall", player.getName()));
        return 1;
    }

}