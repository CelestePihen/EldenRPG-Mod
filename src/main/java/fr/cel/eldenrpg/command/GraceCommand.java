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

import java.util.List;

/**
 * Permet d'avoir la liste des grâces que le joueur a
 */
public class GraceCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("grace").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("list").executes(source -> getQuestList(source.getSource().getPlayerOrThrow())))

                .then(CommandManager.literal("list").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> getQuestList(EntityArgumentType.getPlayer(source, "player")))))
        );
    }

    private static int getQuestList(ServerPlayerEntity player) {
        player.sendMessage(Text.literal("Les Graces : "));

        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        List<Long> posLong = GracesData.getGraces(playerDataSaver);
        for (long l : posLong) {
            player.sendMessage(GracesData.getGraceName(BlockPos.fromLong(l)));
        }

        return 1;
    }

}