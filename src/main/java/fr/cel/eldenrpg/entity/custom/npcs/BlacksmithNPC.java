package fr.cel.eldenrpg.entity.custom.npcs;

import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BlacksmithNPC extends EldenNPC {

    public BlacksmithNPC(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setPos(206.5, 65, -63.5);
        this.setCustomName(Text.translatable("entity.eldenrpg.blacksmith"));
        this.quest = Quests.BLACKSMITH;
    }

    @Override
    protected void playerInteract(PlayerEntity player, Hand hand) {
        IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;
        Quest q = QuestsData.getQuest(playerDataSaver, "blacksmith");

        if (q == null) {
            playerDataSaver.eldenrpg$getQuests().add(quest);
            // TODO translatable
            player.sendMessage(Text.literal("Bonjour aventurier. Pourriez-vous tuer ces 5 créatures vertes et gluantes dans ma maison ? Vous aurez une belle récompense si vous le faites !"));

            // fait apparaître les 5 slimes
            for (int i = 0; i < 5; i++) {
                SlimeEntity slime = new SlimeEntity(EntityType.SLIME, getWorld());
                slime.setPos(207.5, 69, -49.5);
                slime.setSize(1, true);
                getWorld().spawnEntity(slime);
            }

            return;
        }

        if (q.getQuestState() == QuestState.ACTIVE) {
            // TODO translatable
            player.sendMessage(Text.literal("Allez tuer ces 5 slimes pour avoir la récompense, aventurier."));
            return;
        }

        if (q.getQuestState() == QuestState.FINISHED) {
            // TODO translatable
            player.sendMessage(Text.literal("Vous êtes mon héros ! Voici votre récompense."));
            player.giveItemStack(new ItemStack(Items.LEVER));
            q.setQuestState(QuestState.COMPLETED);
            return;
        }

        if (q.getQuestState() == QuestState.COMPLETED) {
            // TODO translatable
            player.sendMessage(Text.literal("Encore merci d'avoir dégagé ces bestioles hors de chez moi !"));
        }
    }

}
