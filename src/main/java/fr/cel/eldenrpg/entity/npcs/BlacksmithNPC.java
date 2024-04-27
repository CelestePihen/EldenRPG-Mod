package fr.cel.eldenrpg.entity.npcs;

import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlacksmithNPC extends EldenNPC {

    public BlacksmithNPC(EntityType<? extends AgeableMob> entityType, Level world) {
        super(entityType, world);
        this.quest = Quests.BLACKSMITH;
    }

    @Override
    protected void playerInteract(Player player, InteractionHand hand) {
        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> {
            Quest q = playerQuests.getQuest("blacksmith");

            // TODO faire translatable
            if (q == null) {
                playerQuests.addQuest(quest);
                player.sendSystemMessage(Component.literal("Bonjour aventurier. Pourriez-vous tuer ces 5 slimes dans ma maison. Vous aurez une belle récompense si vous le faites !"));
                for (int i = 0; i < 5; i++) {
                    Slime slime = new Slime(EntityType.SLIME, level());
                    slime.setPos(new Vec3(207.5, 69, -49.5));
                    slime.setSize(1, true);
                    level().addFreshEntity(slime);
                }
                return;
            }

            if (q.getQuestState() == QuestState.ACTIVE) {
                // TODO faire translatable
                player.sendSystemMessage(Component.literal("Allez tuer ces 5 slimes pour avoir la récompense, aventurier."));
                return;
            }

            if (q.getQuestState() == QuestState.FINISHED) {
                // TODO faire translatable
                player.sendSystemMessage(Component.literal("Merci aventurier. Voici votre récompense."));
                player.addItem(new ItemStack(Items.DIAMOND, 2));
                q.setQuestState(QuestState.COMPLETED);
                return;
            }

            if (q.getQuestState() == QuestState.COMPLETED) {
                // TODO faire translatable
                player.sendSystemMessage(Component.literal("Encore merci d'avoir dégagé ces bestioles hors de chez moi, aventurier !"));
            }
        });
    }

}
