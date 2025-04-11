package fr.cel.eldenrpg.sound;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.Queue;

public final class DialogueManager {
    private static final Queue<DelayedMessage> messageQueue = new LinkedList<>();

    public static void sendMessages(ServerPlayerEntity player, MessageWithSound... messages) {
        if (player.isDisconnected()) return;

        long currentTick = player.getServer().getTicks();
        long accumulatedDelay = 0;

        for (MessageWithSound messageWithSound : messages) {
            accumulatedDelay += messageWithSound.delayTicks;
            messageQueue.add(new DelayedMessage(player, messageWithSound, currentTick + accumulatedDelay));
        }
    }

    public static void onServerTick(MinecraftServer server) {
        long currentTick = server.getTicks();
        while (!messageQueue.isEmpty() && messageQueue.peek().tickToSend <= currentTick) {
            DelayedMessage dm = messageQueue.poll();
            if (!dm.player.isDisconnected()) {
                dm.player.sendMessage(Text.translatable(dm.messageWithSound.message), true);
                if (dm.messageWithSound.sound != null) {
                    dm.player.playSoundToPlayer(dm.messageWithSound.sound, SoundCategory.VOICE, 0.5F, 1.0F);
                }
            }
        }
    }

    public record MessageWithSound(String message, int delayTicks, SoundEvent sound) { }

    private record DelayedMessage(ServerPlayerEntity player, MessageWithSound messageWithSound, long tickToSend) { }
}