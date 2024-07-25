package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public class QuestCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("quests").requires(source -> source.hasPermissionLevel(3))
                // Permet d'avoir votre liste de quêtes
                .then(CommandManager.literal("list").executes(source -> getQuestList(source.getSource().getPlayerOrThrow())))

                // Permet d'avoir votre liste de quêtes d'un joueur
                .then(CommandManager.literal("list").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> getQuestList(EntityArgumentType.getPlayer(source, "player")))))
        );
    }

    private static int getQuestList(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.quest.list", player.getName()));

        List<Quest> playerQuests = QuestsData.getQuests((IPlayerDataSaver) player);

        if (!playerQuests.isEmpty()) {
            for (Quest quest : playerQuests) {
                Text message = Text.literal(quest.getId()).append(" | ").append(quest.getQuestState().toString())
                        .append(" | ").append(quest.getTask().getClass().getSimpleName());
                player.sendMessage(message);
            }
        }
        return 1;
    }

}