package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class QuestCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("quests").requires(source -> source.hasPermissionLevel(3))

                // Add Quest
                .then(CommandManager.literal("add").then(CommandManager.argument("questId", StringArgumentType.word())
                        .executes(source -> addQuest(source.getSource().getPlayerOrThrow(), StringArgumentType.getString(source, "questId")))))

                .then(CommandManager.literal("add").then(CommandManager.argument("questId", StringArgumentType.word()).then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> addQuest(EntityArgumentType.getPlayer(source, "player"), source.getSource().getPlayerOrThrow(), StringArgumentType.getString(source, "questId"))))))

                // Remove Quest
                .then(CommandManager.literal("remove").then(CommandManager.argument("questId", StringArgumentType.word())
                        .executes(source -> removeQuest(source.getSource().getPlayerOrThrow(), StringArgumentType.getString(source, "questId")))))

                .then(CommandManager.literal("remove").then(CommandManager.argument("questId", StringArgumentType.word())
                        .then(CommandManager.argument("player", EntityArgumentType.player()).executes(source ->
                                removeQuest(EntityArgumentType.getPlayer(source, "player"), StringArgumentType.getString(source, "questId"))))))

                // List
                .then(CommandManager.literal("list").executes(source -> getQuestList(source.getSource().getPlayerOrThrow())))

                .then(CommandManager.literal("list").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> getQuestList(EntityArgumentType.getPlayer(source, "player")))))
        );
    }

    private static int addQuest(ServerPlayerEntity player, String questId) {
        return addQuest(player, player, questId);
    }

    private static int addQuest(ServerPlayerEntity target, ServerPlayerEntity player, String questId) {
        Quest quest = Quests.getQuest(questId);
        if (quest == null) return 0;

        player.sendMessage(Text.translatable("eldenrpg.command.quest.add", questId, target.getName().getString()));

        ((IPlayerDataSaver) target).eldenrpg$getQuests().add(quest);
        return 1;
    }

    private static int removeQuest(ServerPlayerEntity player, String questId) {
        Quest quest = Quests.getQuest(questId);
        if (quest == null) return 0;

        player.sendMessage(Text.translatable("eldenrpg.command.quest.remove", questId, player.getName().getString()));

        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        playerDataSaver.eldenrpg$getQuests().stream().filter(quest1 -> quest1.getId().equals(questId)).findFirst().ifPresent(quest1 -> playerDataSaver.eldenrpg$getQuests().remove(quest1));
        return 1;
    }

    private static int getQuestList(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.quest.list", player.getName()));

        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        for (Quest quest : playerDataSaver.eldenrpg$getQuests()) {
            Text message = Text.literal(quest.getId()).append(" | ").append(quest.getQuestState().toString()).append(" | ").append(quest.getTask().getClass().getSimpleName());
            player.sendMessage(message);
        }

        return 1;
    }

}