package fr.cel.eldenrpg.entity.npcs;

import fr.cel.eldenrpg.entity.custom.AbstractNPCEntity;
import fr.cel.eldenrpg.item.ModItems;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.sound.DialogueManager;
import fr.cel.eldenrpg.sound.DialogueManager.MessageWithSound;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlacksmithEntity extends AbstractNPCEntity {

    public BlacksmithEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setPosition(206.5, 65, -63.5);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(Text.translatable("entity.eldenrpg.blacksmith_npc"));
    }

    @Override
    protected void playerInteract(ServerPlayerEntity player, Hand hand) {
        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        Quest q = QuestsData.getQuest(playerDataSaver, "blacksmith");

        if (q == null) {
            QuestsData.addQuest(playerDataSaver, Quests.BLACKSMITH);
            DialogueManager.sendMessages(player,
                    new MessageWithSound("entity.eldenrpg.blacksmith.dialogue1", 0, ModSounds.BLACKSMITH_1),
                    /* comme le 1er message dure 1.5 seconde alors on met le 2ème dialogue au bout de 1.5 seconde */
                    new MessageWithSound("entity.eldenrpg.blacksmith.dialogue2", 30, ModSounds.BLACKSMITH_2),

                    /* 1.5 secondes + 2.5 secondes du 2ème dialogue */
                    new MessageWithSound("entity.eldenrpg.blacksmith.dialogue3", 80, ModSounds.BLACKSMITH_3)
            );

            for (int i = 0; i < 5; i++) {
                SlimeEntity slime = new SlimeEntity(EntityType.SLIME, getWorld());
                slime.setPos(207.5, 69, -49.5);
                slime.setSize(1, true);

                // TODO DeathLootTable -> minecraft:empty

                getWorld().spawnEntity(slime);
            }

            return;
        }

        if (q.getQuestState() == QuestState.ACTIVE) {
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BLACKSMITH_4.id(), SoundCategory.VOICE));
            DialogueManager.sendMessages(player, new MessageWithSound("entity.eldenrpg.blacksmith.dialogue4", 0, ModSounds.BLACKSMITH_4));
            return;
        }

        if (q.getQuestState() == QuestState.FINISHED) {
            DialogueManager.sendMessages(player, new MessageWithSound("entity.eldenrpg.blacksmith.dialogue5", 0, ModSounds.BLACKSMITH_5));

            player.getInventory().remove(itemStack -> itemStack.isOf(Items.SLIME_BALL), 5, player.playerScreenHandler.getCraftingInput());
            player.giveItemStack(new ItemStack(ModItems.KEY));
            q.setQuestState(QuestState.COMPLETED);
            return;
        }

        if (q.getQuestState() == QuestState.COMPLETED) {
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BLACKSMITH_5.id(), SoundCategory.VOICE));
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BLACKSMITH_6.id(), SoundCategory.VOICE));

            DialogueManager.sendMessages(player, new MessageWithSound("entity.eldenrpg.blacksmith.dialogue6", 0, ModSounds.BLACKSMITH_6));
        }
    }

}
