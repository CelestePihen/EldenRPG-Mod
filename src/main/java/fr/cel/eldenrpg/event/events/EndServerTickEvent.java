package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.areas.Areas;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.sound.DialogueManager;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class EndServerTickEvent implements ServerTickEvents.EndTick {

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(new EndServerTickEvent());
        ServerTickEvents.END_SERVER_TICK.register(DialogueManager::onServerTick);
    }

    @Override
    public void onEndTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.getHungerManager().isNotFull()) {
                player.getHungerManager().setFoodLevel(20);
            }

            cancelCraftingInventory(player);

            for (Area<?> area : Areas.getAreas()) {
                area.detectPlayerInArea(player);
            }

            for (Quest quest : QuestsData.getItemQuests((IPlayerDataSaver) player)) {
                ((ItemTask) quest.getTask()).checkItems(player, quest);
            }

        }
    }

    private void cancelCraftingInventory(ServerPlayerEntity player) {
        boolean isCrafting = false;

        for (int i = 1; i <= 4; i++) {
            ItemStack stack = player.playerScreenHandler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                isCrafting = true;
                player.getInventory().offerOrDrop(stack);
                player.playerScreenHandler.getSlot(i).setStack(ItemStack.EMPTY);
            }
        }

        if (isCrafting) {
            player.playerScreenHandler.sendContentUpdates();
        }
    }

}
