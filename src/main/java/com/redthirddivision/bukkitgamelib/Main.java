/*
 * Copyright 2014 TheJeterLP.
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
package com.redthirddivision.bukkitgamelib;

import com.redthirddivision.bukkitgamelib.utils.Config;
import com.redthirddivision.bukkitgamelib.utils.Metrics;
import de.thejeterlp.bukkit.updater.Updater;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <strong>Project:</strong> R3DBukkitGameLib <br>
 * <strong>File:</strong> Main.java
 *
 * @author <a href="http://jpeter.redthirddivision.com">TheJeterLP</a>
 */
public class Main extends JavaPlugin {

    private static Main INSTANCE;

    @Override
    public void onEnable() {
        long currentTimeMillisStart = System.currentTimeMillis();
        getLogger().info("Minigame Library has been started!");

        INSTANCE = this;

        Config.load();

        if (Config.METRICS_ENABLED.getBoolean()) {
            try {
                Metrics m = new Metrics(this);
                m.start();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Updater u = new Updater(this, -1, "r3dbukkitgamelib");
        u.search();

        long timeTook = System.currentTimeMillis() - currentTimeMillisStart;
        getLogger().info("Minigame library has been started. It took " + timeTook + " miliseconds.");
    }

    @Override
    public void onDisable() {
        long currentTimeMillisStart = System.currentTimeMillis();
        getLogger().info("Minigame Library is shutting down!");

        INSTANCE = null;

        long timeTook = System.currentTimeMillis() - currentTimeMillisStart;
        getLogger().info("Minigame Library has been shut down. It took " + timeTook + " miliseconds.");
    }

    public static Main getInstance() {
        return INSTANCE;
    }

}