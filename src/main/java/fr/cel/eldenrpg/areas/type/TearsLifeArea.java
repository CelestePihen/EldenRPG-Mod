package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import net.minecraft.server.network.ServerPlayerEntity;

public class TearsLifeArea extends Area {

    public TearsLifeArea(String string, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(string, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayerEntity player, String string) {
        // TODO Donner larme de vie (tear of life) au joueur
    }

}