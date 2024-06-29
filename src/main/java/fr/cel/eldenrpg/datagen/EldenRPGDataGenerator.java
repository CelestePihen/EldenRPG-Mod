package fr.cel.eldenrpg.datagen;

import fr.cel.eldenrpg.datagen.client.ModModelProvider;
import fr.cel.eldenrpg.datagen.server.ModBlockTabProvider;
import fr.cel.eldenrpg.datagen.server.ModItemTagProvider;
import fr.cel.eldenrpg.datagen.server.ModLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EldenRPGDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);

		pack.addProvider(ModBlockTabProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
	}

}