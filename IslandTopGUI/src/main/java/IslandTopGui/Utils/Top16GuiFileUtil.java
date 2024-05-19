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

public class Top16GuiFileUtil {

    public Inventory inventory;
    public String inventoryName;
    public ItemStack top16Item;

    public String memberMembers;
    public String memberNoMembers;

    public HashMap<Integer,Integer> factionItemSlot = new HashMap<>();
    public int top16_resync_interval_in_seconds;

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();
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
        inventoryName = instance.getUtil().fixColour(config.getString("Top16.inventory.name"));
        top16Item = config.getItemStack("Top16.item");
        top16_resync_interval_in_seconds = config.getInt("Top16.resync-interval-in-seconds");
        inventory = Bukkit.createInventory(null, config.getInt("Top16.inventory.size"), inventoryName);
        ItemStack filler = config.getItemStack("fillerblock.item.item");

        ArrayList<Integer> slots = new ArrayList<>();
        config.getStringList("fillerblock.item.slots").forEach(string -> slots.add(Integer.parseInt(string)));
        slots.forEach(slot -> inventory.setItem(slot, filler));


        for(int i = 1; i<=16; i++){
            factionItemSlot.put(i,config.getInt("Top16."+i+".itemslot"));
        }
        memberMembers = instance.getUtil().fixColour(config.getString("Member.members"));
        memberNoMembers = instance.getUtil().fixColour(config.getString("Member.no-members"));
    }

    public void setup(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        guiconfig = new File(dir + File.separator + "Top16GuiConfig.yml");
        if (!guiconfig.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
            config.set("Top16.resync-interval-in-seconds", 15);
            config.set("Top16.inventory.name", "&4&lTop 16 Islands");
            config.set("Top16.inventory.size", 54);
            config.set("Top16.item", SpawnerUtils.getDefaultTop16Skull());
            config.set("fillerblock.item.item", SpawnerUtils.getFillerBlock());
            config.set("fillerblock.item.slots", Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "27", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53"));
            config.set("Top16.1.itemslot", 18);
            config.set("Top16.2.itemslot", 19);
            config.set("Top16.3.itemslot", 20);
            config.set("Top16.4.itemslot", 21);
            config.set("Top16.5.itemslot", 22);
            config.set("Top16.6.itemslot", 23);
            config.set("Top16.7.itemslot", 24);
            config.set("Top16.8.itemslot", 25);
            config.set("Top16.9.itemslot", 26);
            config.set("Top16.10.itemslot", 28);
            config.set("Top16.11.itemslot", 29);
            config.set("Top16.12.itemslot", 30);
            config.set("Top16.13.itemslot", 31);
            config.set("Top16.14.itemslot", 32);
            config.set("Top16.15.itemslot", 33);
            config.set("Top16.16.itemslot", 34);
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
