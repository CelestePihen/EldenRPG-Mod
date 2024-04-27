package fr.cel.eldenrpg.quest.task;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class Task {

    private final String id;
    private final String langName;
    private final Component translatedName;

    private int progress;

    public Task(String id, String langName) {
        this(id, langName, 0);
    }

    public Task(String id, String langName, int progress) {
        this.id = id;
        this.langName = langName;
        this.translatedName = Component.translatable(langName);
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public String getLangName() {
        return langName;
    }

    public Component getTranslatedName() {
        return translatedName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public CompoundTag saveNBTData() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", id);
        compoundTag.putInt("progress", progress);
        return compoundTag;
    }

}