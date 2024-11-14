package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class FlaskCommand {

    public static void registerFlask(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("flask").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("get")
                        .then(CommandManager.literal("max").executes(source -> getMaxFlasks(source.getSource().getPlayerOrThrow())))
                        .then(CommandManager.literal("current").executes(source -> getFlasks(source.getSource().getPlayerOrThrow())))
                        .then(CommandManager.literal("level").executes(source -> getLevelFlasks(source.getSource().getPlayerOrThrow())))
                )

                .then(CommandManager.literal("get")
                        .then(CommandManager.literal("max").then(CommandManager.argument("player",
                                        EntityArgumentType.player()).executes(source -> getMaxFlasks(EntityArgumentType.getPlayer(source, "player"))))
                        )

                        .then(CommandManager.literal("current").then(CommandManager.argument("player",
                                        EntityArgumentType.player()).executes(source -> getFlasks(EntityArgumentType.getPlayer(source, "player"))))
                        )

                        .then(CommandManager.literal("level").then(CommandManager.argument("player",
                                EntityArgumentType.player()).executes(source -> getLevelFlasks(EntityArgumentType.getPlayer(source, "player"))))
                        )
                )
        );
    }

    public static void registerGoldenSeed(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("gseed").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("current").executes(source -> getGoldenSeed(source.getSource().getPlayerOrThrow())))
                .then(CommandManager.literal("removeallareas").executes(source -> removeAllAreasGS(source.getSource().getPlayerOrThrow())))
                .then(CommandManager.literal("add").executes(source -> addGoldenSeed(source.getSource().getPlayerOrThrow())))

                .then(CommandManager.literal("current").then(CommandManager.argument("player",
                        EntityArgumentType.player()).executes(source -> getGoldenSeed(EntityArgumentType.getPlayer(source, "player"))))
                )

                .then(CommandManager.literal("removeallareas").then(CommandManager.argument("player",
                        EntityArgumentType.player()).executes(source -> removeAllAreasGS(EntityArgumentType.getPlayer(source, "player"))))
                )

                .then(CommandManager.literal("add").then(CommandManager.argument("player",
                        EntityArgumentType.player()).executes(source -> addGoldenSeed(EntityArgumentType.getPlayer(source, "player"))))
                )
        );
    }

    public static void registerSacredTear(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("stear").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("current").executes(source -> getSacredTear(source.getSource().getPlayerOrThrow())))
                .then(CommandManager.literal("removeallareas").executes(source -> removeAllAreasST(source.getSource().getPlayerOrThrow())))
                .then(CommandManager.literal("add").executes(source -> addSacredTear(source.getSource().getPlayerOrThrow())))

                .then(CommandManager.literal("current").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> getSacredTear(EntityArgumentType.getPlayer(source, "player"))))
                )

                .then(CommandManager.literal("removeallareas").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> removeAllAreasST(EntityArgumentType.getPlayer(source, "player"))))
                )

                .then(CommandManager.literal("add").then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(source -> addSacredTear(EntityArgumentType.getPlayer(source, "player"))))
                )
        );
    }

    private static int addGoldenSeed(ServerPlayerEntity player) {
        FlasksData.addGoldenSeed((IPlayerDataSaver) player);
        player.sendMessage(Text.translatable("eldenrpg.area.goldenseed"), true);
        return 1;
    }

    private static int addSacredTear(ServerPlayerEntity player) {
        FlasksData.addSacredTear((IPlayerDataSaver) player);
        player.sendMessage(Text.translatable("eldenrpg.area.sacredtear"), true);
        return 1;
    }

    private static int getMaxFlasks(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.flask.getMax", player.getName(), FlasksData.getMaxFlasks((IPlayerDataSaver) player)));
        return 1;
    }

    private static int getFlasks(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.flask.current", player.getName(), FlasksData.getFlasks((IPlayerDataSaver) player)));
        return 1;
    }

    private static int getLevelFlasks(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.flask.level", player.getName(), FlasksData.getLevelFlasks((IPlayerDataSaver) player)));
        return 1;
    }

    private static int getSacredTear(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.sacredtear.current", player.getName(), FlasksData.getSacredTears((IPlayerDataSaver) player)));
        return 1;
    }

    private static int getGoldenSeed(ServerPlayerEntity player) {
        player.sendMessage(Text.translatable("eldenrpg.command.goldenseed.current", player.getName(), FlasksData.getGoldenSeeds((IPlayerDataSaver) player)));
        return 1;
    }

    private static int removeAllAreasST(ServerPlayerEntity player) {
        FlasksData.removeAllAreasST((IPlayerDataSaver) player);
        return 1;
    }

    private static int removeAllAreasGS(ServerPlayerEntity player) {
        FlasksData.removeAllAreasGS((IPlayerDataSaver) player);
        return 1;
    }

}