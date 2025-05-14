package fr.cel.eldenrpg;

import fr.cel.eldenrpg.block.ModBlocks;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.event.ServerEvents;
import fr.cel.eldenrpg.item.ModItemGroups;
import fr.cel.eldenrpg.item.ModItems;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EldenRPG implements ModInitializer {

	public static final String MOD_ID = "eldenrpg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean isIDE = true;

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModItemGroups.registerItemGroups();

		ModSounds.registerModSounds();

		ModEntities.registerModEntities();

		ModMessages.registerCommonPayload();
		ModMessages.registerC2SPackets();

		ServerEvents.registerEvents();
	}

}