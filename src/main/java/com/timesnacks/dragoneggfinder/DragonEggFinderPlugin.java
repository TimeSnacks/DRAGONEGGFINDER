package com.timesnacks.dragoneggfinder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DragonEggFinderPlugin extends JavaPlugin {
    private BukkitRunnable task;
    private int worldIndex = 0;
    private int chunkX = 0;
    private int chunkZ = 0;

    @Override
    public void onEnable() {
        getLogger().info("DragonEggFinder enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You must be an operator to use this command.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("stop")) {
            if (task != null) {
                task.cancel();
                task = null;
                sender.sendMessage("Dragon Egg search stopped.");
            }
            return true;
        }

        if (task != null) {
            sender.sendMessage("A search is already running.");
            return true;
        }

        List<World> worlds = new ArrayList<>(Bukkit.getWorlds());
        if (worlds.isEmpty()) {
            sender.sendMessage("No worlds loaded.");
            return true;
        }

        worldIndex = 0;
        chunkX = 0;
        chunkZ = 0;

        sender.sendMessage("Dragon Egg search started. This may take a long time.");

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (worldIndex >= worlds.size()) {
                    Bukkit.broadcastMessage("Dragon Egg search finished. No Dragon Egg found in scanned chunks.");
                    cancel();
                    task = null;
                    return;
                }

                World world = worlds.get(worldIndex);
                int radius = 1875; // 30,000 blocks / 16 blocks per chunk
                int x = chunkX;
                int z = chunkZ;

                chunkZ++;
                if (chunkZ > radius) {
                    chunkZ = -radius;
                    chunkX++;
                }
                if (chunkX > radius) {
                    worldIndex++;
                    chunkX = -radius;
                    chunkZ = -radius;
                    return;
                }

                if (!world.isChunkLoaded(x, z) && !world.isChunkGenerated(x, z)) {
                    return;
                }

                if (!world.isChunkLoaded(x, z)) {
                    return; // Never force-load chunks
                }

                for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                    for (int bx = 0; bx < 16; bx++) {
                        for (int bz = 0; bz < 16; bz++) {
                            Block block = world.getChunkAt(x, z).getBlock(bx, y, bz);
                            if (block.getState() instanceof Container container) {
                                for (ItemStack item : container.getInventory().getContents()) {
                                    if (item != null && item.getType() == Material.DRAGON_EGG) {
                                        String coords = "Dragon Egg found in " + world.getName()
                                                + " at X:" + block.getX()
                                                + " Y:" + block.getY()
                                                + " Z:" + block.getZ();
                                        for (int i = 0; i < 3; i++) {
                                            Bukkit.broadcastMessage(coords);
                                        }
                                        cancel();
                                        task = null;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };

        task.runTaskTimer(this, 1L, 1L);
        return true;
    }
}
