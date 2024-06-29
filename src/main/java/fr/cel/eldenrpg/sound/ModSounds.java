package fr.cel.eldenrpg.sound;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent LOST_GRACE_DISCOVERED = registerSoundEvent("lost_grace_discovered");
    public static final SoundEvent DEATH = registerSoundEvent("death");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(EldenRPG.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    /**
     * Permet d'enregistrer tous les sons de la classe
     */
    public static void registerModSounds() {
        EldenRPG.LOGGER.info("Enregistrement des Sons pour " + EldenRPG.MOD_ID);
    }

}
