package fr.cel.eldenrpg.quest.task;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class Task {

    private final String id;
    private final String langName;
    private final Text translatedName;

    private int progress;

    public Task(String id, String langName) {
        this(id, langName, 0);
    }

    public Task(String id, String langName, int progress) {
        this.id = id;
        this.langName = langName;
        this.translatedName = Text.translatable(langName);
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public String getLangName() {
        return langName;
    }

    public Text getTranslatedName() {
        return translatedName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public NbtCompound writeNbt() {
        NbtCompound compoundTag = new NbtCompound();
        compoundTag.putString("id", id);
        compoundTag.putInt("progress", progress);
        return compoundTag;
    }

}