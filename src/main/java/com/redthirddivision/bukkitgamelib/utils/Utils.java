/*
 * Copyright 2014 RedThirdDivision.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redthirddivision.bukkitgamelib.utils;

import com.redthirddivision.bukkitgamelib.Game;
import com.redthirddivision.bukkitgamelib.arena.GameManager;
import com.redthirddivision.bukkitgamelib.arena.PlayerData;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * <strong>Project:</strong> R3DBukkitGameLib <br>
 * <strong>File:</strong> Utils.java
 *
 * @author <a href="http://jeter.vc-network.com">TheJeterLP</a>
 */
public class Utils {

    /**
     * Replaces all possible ColorCodes starting with &amp; to a String minecraft understands
     * @param message the message which contains the unreplaced color codes
     * @return the message with replaced color codes
     */
    public static String replaceColors(String message) {
        return message.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
    }

    /**
     * Removes all color codes form a String
     * @param string the message with replaced color codes
     * @return the message without any colors
     */
    public static String removeColors(String string) {
        return string.replaceAll("§((?i)[0-9a-fk-or])", "");
    }

    /**
     * Used to transform a Location object to a String
     * @param l the Location we want to transform
     * @return a String which can be stored easiliy
     */
    public static String serialLocation(Location l) {
        int pitch = Integer.valueOf(String.valueOf(l.getPitch()).split("\\.")[0]);
        int yaw = Integer.valueOf(String.valueOf(l.getYaw()).split("\\.")[0]);
        return l.getX() + ";" + l.getY() + ";" + l.getZ() + ";" + l.getWorld().getName() + ";" + yaw + ";" + pitch;
    }

    /**
     * Used to get a Location object from a serialized String using {@link #serialLocation(org.bukkit.Location)}
     * @param s the String we want to get the Location from
     * @return a Location made out of the String
     */
    public static Location deserialLocation(String s) {
        String[] a = s.split(";");
        World w = Bukkit.getWorld(a[3]);
        Validate.notNull(w, "The world cannot be null!");
        double x = Double.parseDouble(a[0]);
        double y = Double.parseDouble(a[1]);
        double z = Double.parseDouble(a[2]);
        int yaw = Integer.parseInt(a[4]);
        int pitch = Integer.parseInt(a[5]);
        Location l = new Location(w, x, y, z, yaw, pitch);
        return l;
    }

    /**
     * Gets the Block a Player is looking at
     * @param player the player who looks at a block
     * @param range the range of the looking
     * @return the Block the player looks at the given range
     */
    public static Block getBlockLooking(Player player, int range) {
        Block b = player.getTargetBlock(null, range);
        return b;

    }

   /**
     * Gets the Location a Player is looking at
     * @param player the player who looks at a location
     * @param range the range of the looking
     * @return the Location the player looks at the given range
     */
    public static Location getLocationLooking(Player player, int range) {
        Block b = getBlockLooking(player, range);
        return b.getLocation();
    }

    /**
     * Completely cleares a players inventory including armor
     * @param p the player which inventory will be cleared
     */
    public static void clearInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setBoots(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
    }

    /**
     * Creates an inventory containing all player heads to use for spectator mode.
     * @param a the game we want to create the inventory from
     * @return an Inventory which is used internally
     */
    public static Inventory getSpectatorInventory(Game a) {
        Inventory ret = Bukkit.createInventory(null, 9 * 5, "Alive: ");
        for (PlayerData pd : a.getAlivePlayers()) {
            final ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setDisplayName(pd.getPlayer().getName());
            meta.setOwner(pd.getPlayer().getName());
            stack.setItemMeta(meta);
            ret.addItem(stack);
        }
        return ret;
    }

    /**
     * Sends a message to a player
     * @param p the receiver of the message
     * @param type the type of the message, used for coloring
     * @param msg the message to send
     */
    public static void sendMessage(Player p, MessageType type, String msg) {
        String pre = "";
        switch (type) {
            case INFO:
                pre = "§a[" + GameManager.getInstance().getPlugin().getName() + "]§7 ";
                break;
            case ERROR:
                pre = "§4[" + GameManager.getInstance().getPlugin().getName() + "]§7 ";
                break;
        }
        p.sendMessage(pre + msg);
    }

    public enum MessageType {

        INFO,
        ERROR

    }
}
