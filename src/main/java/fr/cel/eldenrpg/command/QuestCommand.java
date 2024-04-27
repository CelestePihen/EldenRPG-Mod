package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class QuestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("quests").requires(source -> source.hasPermission(Commands.LEVEL_ADMINS))

                // Add
                .then(Commands.literal("add").then(Commands.argument("questId", StringArgumentType.word())
                        .executes(source -> addQuest(source.getSource().getPlayerOrException(), StringArgumentType.getString(source, "questId")))))

                .then(Commands.literal("add").then(Commands.argument("questId", StringArgumentType.word()).then(Commands.argument("player", EntityArgument.player())
                        .executes(source -> {
                            ServerPlayer target = EntityArgument.getPlayer(source, "player");
                            String questIdArg = StringArgumentType.getString(source, "questId");

                            source.getSource().getPlayerOrException().sendSystemMessage(Component.literal("Vous avez donné la quête ")
                                    .append(Quests.getQuest(questIdArg).getTranslatedName()).append(" à " + target.getName().getString()));

                            return addQuest(target, questIdArg);
                }))))

                // Remove
                .then(Commands.literal("remove").then(Commands.argument("questId", StringArgumentType.word())
                        .executes(source -> {
                            ServerPlayer player = source.getSource().getPlayerOrException();
                            String questIdArg = StringArgumentType.getString(source, "questId");

                            player.sendSystemMessage(Component.literal("Tu t'es retiré la quête ").append(Quests.getQuest(questIdArg).getTranslatedName()));
                            return removeQuest(player, questIdArg);
                        })))

                .then(Commands.literal("remove").then(Commands.argument("questId", StringArgumentType.word()).then(Commands.argument("player", EntityArgument.player()).executes(source -> {
                    ServerPlayer target = EntityArgument.getPlayer(source, "player");
                    String questIdArg = StringArgumentType.getString(source, "questId");

                    source.getSource().getPlayerOrException().sendSystemMessage(Component.literal("Vous avez retiré la quête ")
                            .append(Quests.getQuest(questIdArg).getTranslatedName()));
                    return removeQuest(target, questIdArg);
                }))))

                // List
                .then(Commands.literal("list").executes(source -> getQuestList(source.getSource().getPlayerOrException())))

                .then(Commands.literal("list").then(Commands.argument("player", EntityArgument.player())
                        .executes(source -> getQuestList(EntityArgument.getPlayer(source, "player")))))
        );
    }

    private static int addQuest(Player player, String questId) {
        Quest quest = Quests.getQuest(questId);
        if (quest == null) return 0;

        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> playerQuests.addQuest(quest));
        return 1;
    }

    private static int removeQuest(Player player, String questId) {
        Quest quest = Quests.getQuest(questId);
        if (quest == null) return 0;

        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> playerQuests.removeQuest(questId));
        return 1;
    }

    private static int getQuestList(Player player) {
        player.sendSystemMessage(Component.literal("Voici la liste de quêtes de " + player.getName().getString() + " : "));
        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> playerQuests.getQuests()
                .forEach(quest -> {
                    Component message = quest.getTranslatedName().copy().append(" | ").append(quest.getQuestState().toString());
                    player.sendSystemMessage(message);
                }));
        return 1;
    }

}