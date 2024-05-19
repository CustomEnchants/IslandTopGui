package IslandTopGui.Utils;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import spawnerapi.SpawnerUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class Top10GuiFileUtil {

    public String inventoryName;
    public Inventory inventory;
    public ItemStack top10Item;

    public String member_members;
    public String member_nomembers;
    public int top10_resync_interval_in_seconds;

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();
    public HashMap<Integer,Integer> factionItemSlot = new HashMap<>();

    private File guiconfig;

    private void updateExistingConfiguration() {
        boolean madeChanges = false;
        FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
        if (!madeChanges) {
            return;
        }
        try {
            config.save(guiconfig);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
        }
    }

    private void loadValues() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
        top10Item = config.getItemStack("Top10.item");
        inventoryName = instance.getUtil().fixColour(config.getString("Top10.inventory.name"));
        top10_resync_interval_in_seconds = config.getInt("Top10.resync-interval-in-seconds");
        inventory = Bukkit.createInventory(null, config.getInt("Top10.inventory.size"), inventoryName);
        ItemStack filler = config.getItemStack("fillerblock.item.item");
        ArrayList<Integer> slots = new ArrayList<>();
        config.getStringList("fillerblock.item.slots").forEach(string -> slots.add(Integer.parseInt(string)));
        slots.forEach(slot -> inventory.setItem(slot, filler));
        for(int i = 1; i<=10; i++){
            factionItemSlot.put(i,config.getInt("Top10."+i+".itemSlot"));
        }
        member_members = instance.getUtil().fixColour(config.getString("Member.members"));
        member_nomembers = instance.getUtil().fixColour(config.getString("Member.no-members"));
    }

    public void setup(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        guiconfig = new File(dir + File.separator + "Top10GuiConfig.yml");
        if (!guiconfig.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
            config.set("Top10.resync-interval-in-seconds", 15);
            config.set("Top10.inventory.name", "&4&lTop 10 Islands");
            config.set("Top10.inventory.size", 27);
            config.set("Top10.item",SpawnerUtils.getDefaultTop10Skull());
            config.set("fillerblock.item.item", SpawnerUtils.getFillerBlock());
            config.set("fillerblock.item.slots", Arrays.asList("0", "1", "2", "3", "5", "6", "7", "8", "9", "10", "11", "13", "15", "16", "17", "18", "26"));
            config.set("Top10.1.itemslot", 4);
            config.set("Top10.2.itemslot", 12);
            config.set("Top10.3.itemslot", 14);
            config.set("Top10.4.itemslot", 19);
            config.set("Top10.5.itemslot", 20);
            config.set("Top10.6.itemslot", 21);
            config.set("Top10.7.itemslot", 22);
            config.set("Top10.8.itemslot", 23);
            config.set("Top10.9.itemslot", 24);
            config.set("Top10.10.itemslot", 25);
            config.set("Member.members", "&f- &7%name%");
            config.set("Member.no-members", "&f- &cNone");
            try {
                config.save(guiconfig);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
            }
        }
        updateExistingConfiguration();
        loadValues();
    }




}
