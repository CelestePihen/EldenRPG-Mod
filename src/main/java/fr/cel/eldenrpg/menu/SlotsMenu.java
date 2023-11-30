package fr.cel.eldenrpg.menu;

import fr.cel.eldenrpg.capabilities.slots.PlayerSlotsProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotsMenu extends AbstractContainerMenu {

    private IItemHandler slotsInventory = null;

    public SlotsMenu(int pContainerId, Player player) {
        super(ModMenus.SLOTS_MENU.get(), pContainerId);

        addPlayerInventory(player.getInventory());
        addPlayerHotbar(player.getInventory());

        player.getCapability(PlayerSlotsProvider.PLAYER_SLOTS).ifPresent(playerSlots -> {
            slotsInventory = playerSlots.getStacks();
            int slot = 0;
            int size = slotsInventory.getSlots();
            while (slot < size) {
                int x = slot % 9;
                int y = slot / 9;
                this.addSlot(new SlotItemHandler(this.slotsInventory, slot, 8 + 18 * x, 17 + 18 * y));
                slot++;
            }
        });

    }

    // TODO à faire plus tard
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return slotsInventory != null;
    }

    public int getSlotAmount() {
        return slotsInventory.getSlots();
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

}
