package fr.cel.eldenrpg.entity.custom.npcs;

import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.DialogueManager;
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

import java.util.List;

public class BlacksmithNPC extends EldenNPC {

    public BlacksmithNPC(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setPos(206.5, 65, -63.5);
        this.setCustomName(Text.translatable("entity.eldenrpg.blacksmith"));
        this.quest = Quests.BLACKSMITH;
    }

    @Override
    protected void playerInteract(ServerPlayerEntity player, Hand hand) {
        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        Quest q = QuestsData.getQuest(playerDataSaver, "blacksmith");

        if (q == null) {
            QuestsData.addQuest(playerDataSaver, quest);
            List<DialogueManager.MessageWithSound> messages = List.of(
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue1", 0, ModSounds.BLACKSMITH_1),
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue2", 30, ModSounds.BLACKSMITH_2), // comme le 1er message dure 1.5 seconde alors on met le 2ème dialogue au bout de 1.5 seconde
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue3", 80, ModSounds.BLACKSMITH_3)
            );
            DialogueManager.sendMessages(player, messages);

            for (int i = 0; i < 5; i++) {
                SlimeEntity slime = new SlimeEntity(EntityType.SLIME, getWorld());
                slime.setPos(207.5, 69, -49.5);
                slime.setSize(1, true);
                getWorld().spawnEntity(slime);
            }

            return;
        }

        if (q.getQuestState() == QuestState.ACTIVE) {
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BLACKSMITH_4.getId(), SoundCategory.VOICE));
            List<DialogueManager.MessageWithSound> messages = List.of(
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue4", 0, ModSounds.BLACKSMITH_4)
            );
            DialogueManager.sendMessages(player, messages);
            return;
        }

        if (q.getQuestState() == QuestState.FINISHED) {
            List<DialogueManager.MessageWithSound> messages = List.of(
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue5", 0, ModSounds.BLACKSMITH_5)
            );
            DialogueManager.sendMessages(player, messages);

            player.giveItemStack(new ItemStack(Items.LEVER));
            q.setQuestState(QuestState.COMPLETED);
            return;
        }

        if (q.getQuestState() == QuestState.COMPLETED) {
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BLACKSMITH_6.getId(), SoundCategory.VOICE));
            List<DialogueManager.MessageWithSound> messages = List.of(
                    new DialogueManager.MessageWithSound("entity.eldenrpg.blacksmith.dialogue6", 40, ModSounds.BLACKSMITH_6) // 3 seconde de délai
            );
            DialogueManager.sendMessages(player, messages);
        }
    }

}
