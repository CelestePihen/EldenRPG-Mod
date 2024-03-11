package fr.cel.eldenrpg.menu;

import fr.cel.eldenrpg.capabilities.slots.PlayerBackpackProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackMenu extends AbstractContainerMenu {

    private IItemHandler slotsInventory = null;

    public BackpackMenu(int windowId, Player player) {
        super(ModMenus.BACKPACK_MENU.get(), windowId);

        addPlayerInventory(player.getInventory());
        addPlayerHotbar(player.getInventory());

        player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(playerSlots -> {
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

    // TODO
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        int size = slotsInventory.getSlots();

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (0 <= pIndex && pIndex < size) {// shift-click from slots inventory
                if (!this.moveItemStackTo(slotStack, size, 36 + size, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (size <= pIndex && pIndex < 36 + size) {// shift-click from player inventory
                if (!this.moveItemStackTo(slotStack, 0, size, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, slotStack);
        }
        return itemstack;
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
