package fr.cel.eldenrpg.event.events;

import com.mojang.brigadier.CommandDispatcher;
import fr.cel.eldenrpg.command.FlaskCommand;
import fr.cel.eldenrpg.command.GraceCommand;
import fr.cel.eldenrpg.command.NPCCommand;
import fr.cel.eldenrpg.command.QuestCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandsRegisterEvent implements CommandRegistrationCallback {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(new CommandsRegisterEvent());
    }

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        NPCCommand.register(dispatcher);
        QuestCommand.register(dispatcher);

        GraceCommand.register(dispatcher);

        FlaskCommand.registerFlask(dispatcher);
        FlaskCommand.registerGoldenSeed(dispatcher);
        FlaskCommand.registerSacredTear(dispatcher);
    }

}