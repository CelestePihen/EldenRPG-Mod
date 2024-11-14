package fr.cel.eldenrpg.sound;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {

    public static final SoundEvent LOST_GRACE_DISCOVERED = registerSoundEvent("lost_grace_discovered");
    public static final SoundEvent DEATH = registerSoundEvent("death");
    public static final SoundEvent DRINK_FLASK = registerSoundEvent("drink_flask");
    public static final SoundEvent MAP_FOUND = registerSoundEvent("map_found");

    public static final SoundEvent BLACKSMITH_1 = registerSoundEvent("blacksmith_1");
    public static final SoundEvent BLACKSMITH_2 = registerSoundEvent("blacksmith_2");
    public static final SoundEvent BLACKSMITH_3 = registerSoundEvent("blacksmith_3");
    public static final SoundEvent BLACKSMITH_4 = registerSoundEvent("blacksmith_4");
    public static final SoundEvent BLACKSMITH_5 = registerSoundEvent("blacksmith_5");
    public static final SoundEvent BLACKSMITH_6 = registerSoundEvent("blacksmith_6");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(EldenRPG.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    /**
     * Permet d'enregistrer tous les sons de la classe
     */
    public static void registerModSounds() {
        EldenRPG.LOGGER.info("Register Sounds for " + EldenRPG.MOD_ID);
    }

}