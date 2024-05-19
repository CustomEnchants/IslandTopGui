package IslandTopGui.Utils;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileUtil {

    public boolean do_not_override_islandtop_command;
    public boolean display_top10_gui;
    public boolean display_top16_gui;
    private File conf;

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    private String fixColour(String input) {
        return input.isEmpty() ? "" : ChatColor.translateAlternateColorCodes('&', input);
    }

    private ArrayList<String> fixColours(List<String> input) {
        ArrayList<String> result = new ArrayList<>();
        input.forEach(string -> result.add(fixColour(string)));
        return result;
    }


    private void updateConfigurations() {
    }

    private void loadValues(boolean reload) {
        boolean updated = false;
        FileConfiguration config = YamlConfiguration.loadConfiguration(conf);
        ConfigurationSection options = config.getConfigurationSection("Options");
        do_not_override_islandtop_command = options.getBoolean("do-not-override-islandtop-command");
        display_top10_gui = options.getBoolean("gui.display.top10");
        display_top16_gui = options.getBoolean("gui.display.top16");
        if(!updated){
            return;
        }
        try{
            config.save(conf);
        }catch(IOException e){
            Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
        }
    }

    public void setup(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        conf = new File(dir + File.separator + "Config.yml");
        if (!conf.exists()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(conf);
                config.set("Author", "CustomEnchants");
                ConfigurationSection options = config.createSection("Options");
                options.set("do-not-override-islandtop-command",false);
                options.set("gui.display.top10", false);
                options.set("gui.display.top16", true);
                config.save(conf);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
            }
        }
        updateConfigurations();
        loadValues(false);
    }

}
