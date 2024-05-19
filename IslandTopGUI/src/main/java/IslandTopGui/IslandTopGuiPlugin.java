package IslandTopGui;

import IslandTopGui.Tasks.CreateIslandInventories;
import IslandTopGui.Tasks.CreateTop10Inventory;
import IslandTopGui.Tasks.CreateTop16Inventory;
import IslandTopGui.Utils.*;
import IslandTopGui.Commands.IslandTopGuiCommand;
import IslandTopGui.Commands.OpenIslandValueGui;
import IslandTopGui.Events.IslandTopGuiEvents;
import IslandTopGui.Objects.GuiItem;
import IslandTopGui.Objects.IndividualGui;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class IslandTopGuiPlugin extends JavaPlugin {

    private static IslandTopGuiPlugin instance;
    public final HashMap<Integer, String> top10IslandAtSlot = new HashMap<>();
    public final HashMap<Integer, String> top16IslandAtSlot = new HashMap<>();
    public final ArrayList<UUID> playersInGui = new ArrayList<>();
    private Util util;
    private FileUtil fileutil;
    private GuiFileUtil guifileutil;
    private Top10GuiFileUtil top10guifileutil;
    private Top16GuiFileUtil top16guifileutil;
    private final ArrayList<GuiItem> guiItems = new ArrayList<>();
    private final HashMap<String, IndividualGui> guis = new HashMap<>();

    public static IslandTopGuiPlugin getInstance() {
        return instance;
    }

    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("IslandTop")) {
            Bukkit.getConsoleSender().sendMessage("[IslandTopGui] Didn't find  IslandTop! Shutting down.");
            setEnabled(false);
            return;
        }
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getConsoleSender().sendMessage("[IslandTopGui] Didn't find correct PlaceholderAPI! Shutting down.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        fileutil = new FileUtil();
        guifileutil = new GuiFileUtil();
        top10guifileutil = new Top10GuiFileUtil();
        top16guifileutil = new Top16GuiFileUtil();
        util = new Util();
        getFileUtil().setup(getDataFolder());
        getTop10GuiFileUtil().setup(getDataFolder());
        getTop16GuiFileUtil().setup(getDataFolder());
        getGuiFileUtil().setup(getDataFolder());
        if (getFileUtil().display_top10_gui) {
            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new CreateTop10Inventory(), 0L, getTop10GuiFileUtil().top10_resync_interval_in_seconds * 20L);
        }
        if (getFileUtil().display_top16_gui) {
            Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new CreateTop16Inventory(), 0L, getTop16GuiFileUtil().top16_resync_interval_in_seconds * 20L);
        }
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new CreateIslandInventories(), 0L, 15 * 20L);
        if(!getFileUtil().do_not_override_islandtop_command) {
            Bukkit.getPluginManager().registerEvents(new IslandTopGuiCommand(), this);
        }
        Bukkit.getPluginManager().registerEvents(new IslandTopGuiEvents(), this);
        getCommand("OpenIslandWorthGui").setExecutor(new OpenIslandValueGui());
    }

    public void onDisable() {
        Bukkit.getServer().getScheduler().cancelTasks(this);
    }

    public FileUtil getFileUtil() {
        return fileutil;
    }

    public Util getUtil() {
        return util;
    }

    public Top10GuiFileUtil getTop10GuiFileUtil() {
        return top10guifileutil;
    }

    public Top16GuiFileUtil getTop16GuiFileUtil() {
        return top16guifileutil;
    }

    public GuiFileUtil getGuiFileUtil() {
        return guifileutil;
    }

    public ArrayList<GuiItem> getGuiItems() {
        return guiItems;
    }

    public HashMap<String, IndividualGui> getGuis() {
        return guis;
    }


}
