package fr.cel.eldenrpg.networking;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.networking.packets.bonfires.GracesSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.bonfires.MapTeleportationC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.FlasksSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.MapsSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.PickMapC2SPacket;
import fr.cel.eldenrpg.networking.packets.npc.NPCDataC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier DRINK_FLASK_ID = Identifier.of(EldenRPG.MOD_ID, "drinkflask");
    public static final Identifier SYNC_FLASK_ID = Identifier.of(EldenRPG.MOD_ID, "syncflasks");

    public static final Identifier PICK_MAP_ID = Identifier.of(EldenRPG.MOD_ID, "pickmap");
    public static final Identifier SYNC_MAP_ID = Identifier.of(EldenRPG.MOD_ID, "syncmaps");

    public static final Identifier MAP_TELEPORTATION_ID = Identifier.of(EldenRPG.MOD_ID, "mapteleportation");
    public static final Identifier GRACES_SYNC_ID = Identifier.of(EldenRPG.MOD_ID, "syncgraces");

    public static final Identifier NPC_DATA_ID = Identifier.of(EldenRPG.MOD_ID, "npcdata");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(PickMapC2SPacket.ID, PickMapC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(NPCDataC2SPacket.ID, NPCDataC2SPacket::handle);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(FlasksSyncDataS2CPacket.ID, FlasksSyncDataS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(MapsSyncDataS2CPacket.ID, MapsSyncDataS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(GracesSyncDataS2CPacket.ID, GracesSyncDataS2CPacket::handle);
    }

    public static void registerCommonPayload() {
        PayloadTypeRegistry.playC2S().register(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(FlasksSyncDataS2CPacket.ID, FlasksSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(PickMapC2SPacket.ID, PickMapC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(MapsSyncDataS2CPacket.ID, MapsSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GracesSyncDataS2CPacket.ID, GracesSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(NPCDataC2SPacket.ID, NPCDataC2SPacket.CODEC);
    }

}
