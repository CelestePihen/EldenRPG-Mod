package fr.cel.eldenrpg.networking;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.networking.packets.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.FlasksSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.graces.GracesSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.graces.MapTeleportationC2SPacket;
import fr.cel.eldenrpg.networking.packets.graces.screen.OpenChestC2SPacket;
import fr.cel.eldenrpg.networking.packets.graces.screen.OpenGraceScreenS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.MapsSyncDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public final class ModMessages {

    public static final Identifier DRINK_FLASK_ID = Identifier.of(EldenRPG.MOD_ID, "drinkflask");
    public static final Identifier SYNC_FLASK_ID = Identifier.of(EldenRPG.MOD_ID, "syncflasks");

    public static final Identifier SYNC_MAP_ID = Identifier.of(EldenRPG.MOD_ID, "syncmaps");

    public static final Identifier MAP_TELEPORTATION_ID = Identifier.of(EldenRPG.MOD_ID, "mapteleportation");
    public static final Identifier GRACES_SYNC_ID = Identifier.of(EldenRPG.MOD_ID, "syncgraces");

    public static final Identifier OPEN_SCREEN_GRACE_ID = Identifier.of(EldenRPG.MOD_ID, "openscreengrace");
    public static final Identifier OPEN_CHEST_ID = Identifier.of(EldenRPG.MOD_ID, "openchest");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket::handle);

        ServerPlayNetworking.registerGlobalReceiver(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket::handle);

        ServerPlayNetworking.registerGlobalReceiver(OpenChestC2SPacket.ID, OpenChestC2SPacket::handle);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(FlasksSyncDataS2CPacket.ID, FlasksSyncDataS2CPacket::handle);

        ClientPlayNetworking.registerGlobalReceiver(MapsSyncDataS2CPacket.ID, MapsSyncDataS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(GracesSyncDataS2CPacket.ID, GracesSyncDataS2CPacket::handle);

        ClientPlayNetworking.registerGlobalReceiver(OpenGraceScreenS2CPacket.ID, OpenGraceScreenS2CPacket::handle);
    }

    public static void registerCommonPayload() {
        PayloadTypeRegistry.playC2S().register(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(FlasksSyncDataS2CPacket.ID, FlasksSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(MapsSyncDataS2CPacket.ID, MapsSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GracesSyncDataS2CPacket.ID, GracesSyncDataS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(OpenChestC2SPacket.ID, OpenChestC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenGraceScreenS2CPacket.ID, OpenGraceScreenS2CPacket.CODEC);
    }

}
