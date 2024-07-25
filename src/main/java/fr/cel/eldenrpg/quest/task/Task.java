package fr.cel.eldenrpg.quest.task;

import net.minecraft.nbt.NbtCompound;

public class Task {

    private final String id;
    private int progress;

    public Task(String id) {
        this(id, 0);
    }

    public Task(String id, int progress) {
        this.id = id;
        this.progress = progress;
    }

    public String getId() {
        return id;
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