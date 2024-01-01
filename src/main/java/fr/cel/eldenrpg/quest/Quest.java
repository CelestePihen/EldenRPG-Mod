package fr.cel.eldenrpg.quest;

import net.minecraft.network.chat.Component;

public class Quest {

    private final String id;
    private final Component displayName;
    private final String description;

    public Quest(String id, Component displayName, String description) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
    }

}