package fr.cel.eldenrpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.entity.ModEntities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class NPCCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("summonnpc").requires(source -> source.hasPermission(2))

                // seulement le nom et met à la position du joueur
                .then(Commands.argument("name", StringArgumentType.word()).executes(source -> spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), source.getSource().getPlayerOrException().blockPosition())))

                .then(Commands.argument("name", StringArgumentType.word()).then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .executes(source ->
                                        spawnNPC(source.getSource(), StringArgumentType.getString(source, "name"), BlockPosArgument.getLoadedBlockPos(source, "pos")))
                        )));
    }

    private static int spawnNPC(CommandSourceStack source, String name, BlockPos pos) {
        Level world = source.getLevel();

        EldenNPC npc = new EldenNPC(ModEntities.NPC.get(), world);

        npc.setCustomName(Component.literal(name));
        npc.setCustomNameVisible(true);
        npc.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world.random.nextFloat() * 360.0F, 0.0F);

        world.addFreshEntity(npc);

        return 1;
    }

}