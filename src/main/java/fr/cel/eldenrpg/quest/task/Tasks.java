package fr.cel.eldenrpg.quest.task;

import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class Tasks {

    private static final Map<String, Task> tasks = new HashMap<>();

    public static final ZoneTask LEAVE_TUTORIAL_CAVE = registerZone("leaveTutorialCave");

    public static final KillTask KILL_5_SLIMES = registerKill("kill5Slimes", SlimeEntity.class, 5);

    private static ZoneTask registerZone(String id) {
        ZoneTask task = new ZoneTask(id);
        tasks.put(id, task);
        return task;
    }

    private static KillTask registerKill(String id, Class<? extends MobEntity> type, int amount) {
        KillTask task = new KillTask(id, type, amount);
        tasks.put(id, task);
        return task;
    }

    private static ItemTask registerItem(String id, Item item, int amount) {
        ItemTask task = new ItemTask(id, item, amount);
        tasks.put(id, task);
        return task;
    }

    public static Task loadNbt(NbtCompound nbt) {
        NbtCompound taskTag = nbt.getCompound("task").get();
        Task task = Tasks.getTasks().get(taskTag.getString("id").get());
        task.setProgress(taskTag.getInt("progress").get());
        return task;
    }

    public static Map<String, Task> getTasks() {
        return tasks;
    }

}