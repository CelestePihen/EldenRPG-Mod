package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.areas.Areas;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.util.DialogueManager;
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

            for (Area<?> area : Areas.getAreas()) {
                area.detectPlayerInArea(player);
            }

            if (isCraftingInInventory(player)) {
                cancelCrafting(player);
            }

            for (Quest quest : QuestsData.getItemQuests((IPlayerDataSaver) player)) {
                ((ItemTask) quest.getTask()).checkItems(player, quest);
            }

        }
    }

    private boolean isCraftingInInventory(ServerPlayerEntity player) {
        for (int i = 1; i <= 4; i++) {
            ItemStack stack = player.playerScreenHandler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void cancelCrafting(ServerPlayerEntity player) {
        for (int i = 1; i <= 4; i++) {
            ItemStack stack = player.playerScreenHandler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                player.getInventory().offerOrDrop(stack);
                player.playerScreenHandler.getSlot(i).setStack(ItemStack.EMPTY);
            }
        }
        player.playerScreenHandler.sendContentUpdates();
    }

}
