package fr.cel.eldenrpg.sound;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * Gère l'envoi de dialogues vocaux et textuels à un joueur avec gestion de délai.
 * Utilisable pour les PNJ ou séquences narratives.
 */
public final class DialogueManager {
    private static final Queue<DelayedMessage> messageQueue = new LinkedList<>();

    /**
     * Map contenant une estimation de la durée (en ticks) de chaque son utilisé.
     * 20 ticks = 1 seconde.
     * Si un son n'est pas trouvé dans cette map, une valeur par défaut de 40 ticks est utilisée.
     */
    private static final Map<SoundEvent, Integer> ESTIMATED_DURATIONS = new HashMap<>();

    static {
        ESTIMATED_DURATIONS.put(ModSounds.BLACKSMITH_1, 30); // 1.5 sec
        ESTIMATED_DURATIONS.put(ModSounds.BLACKSMITH_2, 70); // 3.5 sec
        ESTIMATED_DURATIONS.put(ModSounds.BLACKSMITH_3, 60); // 3 sec
    }

    /**
     * Envoie une suite de messages et sons à un joueur avec des délais automatiques calculés
     * en fonction de la durée estimée de chaque son.
     *
     * @param player   Le joueur cible.
     * @param messages Liste des messages avec leur son associé. L’ordre détermine la séquence.
     * <br>
     *
     */
    public static void sendSequencedMessages(ServerPlayerEntity player, SequencedMessage... messages) {
        if (player.isDisconnected()) return;

        long currentTick = player.getServer().getTicks();
        long accumulatedDelay = 0;

        for (SequencedMessage message : messages) {
            SoundEvent sound = message.sound();
            int estimatedDuration = sound != null ? ESTIMATED_DURATIONS.getOrDefault(sound, 40) : 40;
            messageQueue.add(new DelayedMessage(
                    player, new MessageWithSound(message.message(), 0, sound), currentTick + accumulatedDelay
            ));
            accumulatedDelay += estimatedDuration;
        }
    }

    /**
     * Envoie une suite de messages et sons à un joueur avec un délai manuel entre chaque message.
     *
     * @param player   Le joueur cible.
     * @param messages Liste des messages avec leur délai relatif (entre ce message et le précédent).
     * <br>
     * Utile pour pour un contrôle manuel précis des délais
     */
    public static void sendMessages(ServerPlayerEntity player, MessageWithSound... messages) {
        if (player.isDisconnected()) return;

        long currentTick = player.getServer().getTicks();
        long accumulatedDelay = 0;

        for (MessageWithSound messageWithSound : messages) {
            accumulatedDelay += messageWithSound.delayTicks();
            messageQueue.add(new DelayedMessage(player, messageWithSound, currentTick + accumulatedDelay));
        }
    }

    /**
     * À appeler sur chaque tick serveur (via l'event).
     * Traite les messages en attente et les envoie s’ils sont prêts.
     *
     * @param server Le serveur Minecraft.
     */
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

    /**
     * Un message pour enchaînement automatique, utilisé avec {@link #sendSequencedMessages}.
     * @param message Clé de traduction du message.
     * @param sound   Son à jouer (peut être null). La durée estimée sera utilisée comme délai automatique.
     */
    public record SequencedMessage(String message, SoundEvent sound) { }

    /**
     * Un message avec son, utilisé pour {@link #sendMessages}.
     * @param message     Clé de traduction du message.
     * @param delayTicks  Nombre de ticks à attendre **avant** d'envoyer ce message.
     * @param sound       Son à jouer en même temps (peut être null).
     */

    public record MessageWithSound(String message, int delayTicks, SoundEvent sound) { }

    /**
     * Message programmé pour un tick donné.
     */
    private record DelayedMessage(ServerPlayerEntity player, MessageWithSound messageWithSound, long tickToSend) { }

}