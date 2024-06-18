package fr.cel.eldenrpg.quest.task;

import fr.cel.eldenrpg.item.ModItems;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class Tasks {

    private static final Map<String, Task> tasks = new HashMap<>();

    // Zone Tasks
    public static final ZoneTask LEAVE_TUTORIAL_CAVE = registerZone("leaveTutorialCave", "eldenrpg.task.leave_tutorial_cave");

    // Kill Tasks
    public static final KillTask KILL_5_SLIMES = registerKill("kill5Slimes", "eldenrpg.task.kill_5_slimes", Slime.class, 5);

    // Item Tasks
    public static final ItemTask OBTAIN_TEST_ITEM = registerItem("obtainTestItem", "eldenrpg.task.obtain_test_item", ModItems.TEST_ITEM.get(), 2);


    public static Map<String, Task> getTasks() {
        return tasks;
    }

    private static ZoneTask registerZone(String id, String langName) {
        ZoneTask task = new ZoneTask(id, langName);
        tasks.put(id, task);
        return task;
    }

    private static KillTask registerKill(String id, String langName, Class<? extends Mob> type, int amount) {
        KillTask task = new KillTask(id, langName, type, amount);
        tasks.put(id, task);
        return task;
    }

    private static ItemTask registerItem(String id, String langName, Item item, int amount) {
        ItemTask task = new ItemTask(id, langName, item, amount);
        tasks.put(id, task);
        return task;
    }

    public static Task loadNBTDataTask(CompoundTag nbt) {
        CompoundTag taskTag = nbt.getCompound("task");

        String id = taskTag.getString("id");
        int progress = taskTag.getInt("progress");

        Task task = Tasks.getTasks().get(id);

        if (task instanceof KillTask killTask) {
            killTask.setProgress(progress);
            return killTask;
        }

        return task;
    }

}