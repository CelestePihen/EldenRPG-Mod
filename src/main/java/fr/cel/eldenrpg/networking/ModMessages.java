package fr.cel.eldenrpg.networking;

import fr.cel.eldenrpg.networking.packets.animations.RollC2SPacket;
import fr.cel.eldenrpg.networking.packets.animations.WaveC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.AddChargeC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.IncreaseFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.*;
import fr.cel.eldenrpg.networking.packets.graces.GracesSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.graces.MapTeleportationC2SPacket;
import fr.cel.eldenrpg.networking.packets.graces.screen.OpenChestC2SPacket;
import fr.cel.eldenrpg.networking.packets.graces.screen.OpenGraceScreenS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.MapsSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ModMessages {

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(AddChargeC2SPacket.ID, AddChargeC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(IncreaseFlaskC2SPacket.ID, IncreaseFlaskC2SPacket::handle);

        ServerPlayNetworking.registerGlobalReceiver(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket::handle);

        ServerPlayNetworking.registerGlobalReceiver(OpenChestC2SPacket.ID, OpenChestC2SPacket::handle);

        ServerPlayNetworking.registerGlobalReceiver(RollC2SPacket.ID, RollC2SPacket::handle);
        ServerPlayNetworking.registerGlobalReceiver(WaveC2SPacket.ID, WaveC2SPacket::handle);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(FlasksSyncS2CPacket.ID, FlasksSyncS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(MaxFlasksSyncS2CPacket.ID, MaxFlasksSyncS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(LevelFlasksSyncS2CPacket.ID, LevelFlasksSyncS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(GoldenSeedSyncS2CPacket.ID, GoldenSeedSyncS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(SacredTearSyncS2CPacket.ID, SacredTearSyncS2CPacket::handle);

        ClientPlayNetworking.registerGlobalReceiver(MapsSyncS2CPacket.ID, MapsSyncS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(GracesSyncS2CPacket.ID, GracesSyncS2CPacket::handle);

        ClientPlayNetworking.registerGlobalReceiver(OpenGraceScreenS2CPacket.ID, OpenGraceScreenS2CPacket::handle);
    }

    public static void registerCommonPayload() {
        PayloadTypeRegistry.playC2S().register(DrinkFlaskC2SPacket.ID, DrinkFlaskC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(AddChargeC2SPacket.ID, AddChargeC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(IncreaseFlaskC2SPacket.ID, IncreaseFlaskC2SPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(FlasksSyncS2CPacket.ID, FlasksSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(MaxFlasksSyncS2CPacket.ID, MaxFlasksSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(LevelFlasksSyncS2CPacket.ID, LevelFlasksSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GoldenSeedSyncS2CPacket.ID, GoldenSeedSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SacredTearSyncS2CPacket.ID, SacredTearSyncS2CPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(MapsSyncS2CPacket.ID, MapsSyncS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(MapTeleportationC2SPacket.ID, MapTeleportationC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GracesSyncS2CPacket.ID, GracesSyncS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(OpenChestC2SPacket.ID, OpenChestC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenGraceScreenS2CPacket.ID, OpenGraceScreenS2CPacket.CODEC);

        PayloadTypeRegistry.playC2S().register(RollC2SPacket.ID, RollC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(WaveC2SPacket.ID, WaveC2SPacket.CODEC);
    }

}
