package fr.cel.eldenrpg.entity.npcs;

import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlacksmithNPC extends EldenNPC {

    public BlacksmithNPC(EntityType<? extends AgeableMob> entityType, Level world) {
        super(entityType, world);
        this.quest = Quests.getQuest("blacksmith");
    }

    @Override
    protected InteractionResult playerInteract(Player player, InteractionHand hand) {
        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> {
            Quest q = playerQuests.getQuest(quest.getId());

            // TODO faire translatable
            if (!playerQuests.hasQuest(q)) {
                playerQuests.addQuest(quest);
                player.sendSystemMessage(Component.literal("Bonjour aventurier. Pourriez-vous tuer ces 4 slimes dans ma maison. Vous aurez une belle récompense si vous le faites !"));
                for (int i = 1; i < 5; i++) {
                    Slime slime = new Slime(EntityType.SLIME, level());
                    slime.setSize(1, false);
                    slime.setPos(new Vec3(207.5, 69, -49.5));
                    level().addFreshEntity(slime);
                }
            }

            if (q.getQuestState() == QuestState.ACTIVE) {

            }

            if (q.getQuestState() == QuestState.FINISHED) {

            }

            if (q.getQuestState() == QuestState.COMPLETED) {

            }
        });
        return InteractionResult.SUCCESS;
    }

}
