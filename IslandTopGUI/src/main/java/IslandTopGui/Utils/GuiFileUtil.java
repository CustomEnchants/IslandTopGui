package IslandTopGui.Utils;

import IslandTopGui.IslandTopGuiPlugin;
import IslandTopGui.Objects.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import spawnerapi.SpawnerUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class GuiFileUtil {

    public String individual_gui_name;
    public int individual_gui_size;
    public ItemStack individual_filler_block_item;
    public final ArrayList<Integer> individual_filler_block_item_slots = new ArrayList<>();
    private File guiconfig;

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    private void updateExistingConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
        boolean madeChanges = false;
        if (!madeChanges) {
            return;
        }
        try {
            config.save(guiconfig);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
        }
    }

    private void loadValues(boolean reload) {
        if (reload) {
            individual_filler_block_item_slots.clear();
            instance.getGuiItems().clear();
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
        individual_gui_name = config.getString("individual.gui.name");
        individual_gui_size = config.getInt("individual.gui.size");

        for (String string : config.getStringList("individual.fillerblockitem.itemslots")) {
            int slot = Integer.parseInt(string);
            individual_filler_block_item_slots.add(slot);
        }
        individual_filler_block_item = config.getItemStack("individual.fillerblockitem.item");


        ConfigurationSection guiitems = config.getConfigurationSection("GuiItems");
        ConfigurationSection items = guiitems.getConfigurationSection("Items");
        for (String string : items.getKeys(false)) {
            ConfigurationSection item = items.getConfigurationSection(string);
            instance.getGuiItems().add(new GuiItem(item.getItemStack("item"), item.getInt("itemSlot")));
        }
    }

    public void setup(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        guiconfig = new File(dir + File.separator + "GuiConfig.yml");
        if (!guiconfig.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(guiconfig);
            config.set("individual.gui.name", "&bIsland Breakdown: &b&n%islandtop_islandtop$rank$%");
            config.set("individual.gui.size", 45);
            config.set("individual.fillerblockitem.item", SpawnerUtils.getFillerBlock());
            config.set("individual.fillerblockitem.itemslots", Arrays.asList("0", "8", "9", "10", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44"));
            ConfigurationSection guiitems = config.createSection("GuiItems");
            ConfigurationSection items = guiitems.createSection("Items");
            int key = 0;
            for (int i = 0; i < 44; i++) {
                if (i != 0 && i != 8 && i != 9 && i != 10 && i != 17 && i != 18 && i != 19 && i != 20 && i != 21 && i != 22 && i != 23 && i != 24 && i != 25 && i != 26 && i != 27 && i != 35 && i != 36 && i != 37 && i != 38 && i != 39 && i != 40 && i != 41 && i != 42 && i != 43) {
                    key += 1;
                    ItemStack itemStack;
                    Material material = getDefaultItemMaterialBasedOnSlot(i);
                    if(material == SpawnerUtils.getSkullItem()){
                        itemStack = SpawnerUtils.getSkull();
                        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                        skullMeta.setOwner(getSkullOwnerBasedOnSlot(i));
                        skullMeta.setDisplayName(getDefaultItemNameBasedOnSlot(i));
                        skullMeta.setLore(getDefaultItemLoreBasedOnSlot(i));
                        itemStack.setItemMeta(skullMeta);
                    }else{
                        itemStack = new ItemStack(material);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(getDefaultItemNameBasedOnSlot(i));
                        itemMeta.setLore(getDefaultItemLoreBasedOnSlot(i));
                        itemStack.setItemMeta(itemMeta);
                    }
                    items.set(key + ".item",itemStack);
                    items.set(key + ".itemSlot", i);
                }
            }

            try {
                config.save(guiconfig);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE,e.getMessage());
            }
        }
        updateExistingConfig();
        loadValues(false);
    }

    private String getSkullOwnerBasedOnSlot(int slot) {
        if (slot == 2) {
            return "irongolem";
        }
        if (slot == 3) {
            return "MHF_Blaze";
        }
        if (slot == 4) {
            return "MHF_Creeper";
        }
        if (slot == 5) {
            return "MHF_Enderman";
        }
        if (slot == 6) {
            return "MHF_Zombie";
        }
        if (slot == 7) {
            return "MHF_Skeleton";
        }
        if (slot == 11) {
            return "MHF_Cow";
        }
        if (slot == 12) {
            return "MHF_Slime";
        }
        if (slot == 13) {
            return "MHF_Spider";
        }
        if (slot == 14) {
            return "MagmaCube";
        }
        if (slot == 15) {
            return "MHF_Horse";
        }
        if (slot == 16) {
            return "MHF_Squid";
        }
        return "";
    }

    private boolean isSkullBasedOnSlot(int slot) {
        return slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 11 || slot == 12 || slot == 13 || slot == 14 || slot == 15 || slot == 16;
    }

    private Material getDefaultItemMaterialBasedOnSlot(int slot) {
        if (slot == 1) {
            return SpawnerUtils.getSpawner();
        }
        if (slot == 2) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 3) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 4) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 5) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 6) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 7) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 11) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 12) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 13) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 14) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 15) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 16) {
            return SpawnerUtils.getSkullItem();
        }
        if (slot == 28) {
            return Material.BOOK;
        }
        if (slot == 29) {
            return Material.HOPPER;
        }
        if (slot == 30) {
            return Material.DROPPER;
        }
        if (slot == 31) {
            return Material.DISPENSER;
        }
        if (slot == 32) {
            return Material.CHEST;
        }
        if (slot == 33) {
            return Material.TRAPPED_CHEST;
        }
        if (slot == 34) {
            return Material.BEACON;
        }
        return Material.AIR;
    }

    private String getDefaultItemNameBasedOnSlot(int slot) {
        if (slot == 1) {
            return "&e&lSPAWNERS ->";
        }
        if (slot == 2) {
            return "&e&l[!]&7 IronGolem Spawner";
        }
        if (slot == 3) {
            return "&e&l[!]&7 Blaze Spawner";
        }
        if (slot == 4) {
            return "&e&l[!]&7 Creeper Spawner";
        }
        if (slot == 5) {
            return "&e&l[!]&7 Enderman Spawner";
        }
        if (slot == 6) {
            return "&e&l[!]&7 Zombie Spawner";
        }
        if (slot == 7) {
            return "&e&l[!]&7 Skeleton Spawner";
        }
        if (slot == 11) {
            return "&e&l[!]&7 Cow Spawner";
        }
        if (slot == 12) {
            return "&e&l[!]&7 Slime Spawner";
        }
        if (slot == 13) {
            return "&e&l[!]&7 Spider Spawner";
        }
        if (slot == 14) {
            return "&e&l[!]&7 MagmaCube Spawner";
        }
        if (slot == 15) {
            return "&e&l[!]&7 Horse Spawner";
        }
        if (slot == 16) {
            return "&e&l[!]&7 Squid Spawner";
        }
        if (slot == 28) {
            return "&e&lITEMS ->";
        }
        if (slot == 29) {
            return "&e&l[!]&7 Hopper";
        }
        if (slot == 30) {
            return "&e&l[!]&7 Dropper";
        }
        if (slot == 31) {
            return "&e&l[!]&7 Dispenser";
        }
        if (slot == 32) {
            return "&e&l[!]&7 Chest";
        }
        if (slot == 33) {
            return "&e&l[!]&7 TrappedChest";
        }
        if (slot == 34) {
            return "&e&l[!]&7 Beacon";
        }
        return "";
    }

    private ArrayList<String> getDefaultItemLoreBasedOnSlot(int slot) {
        ArrayList<String> result = new ArrayList<>();
        if (slot == 2) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#IRON_GOLEM%");
        }
        if (slot == 3) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#BLAZE%");
        }
        if (slot == 4) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#CREEPER%");
        }
        if (slot == 5) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#ENDERMAN%");
        }
        if (slot == 6) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#ZOMBIE%");
        }
        if (slot == 7) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#SKELETON%");
        }
        if (slot == 11) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#COW%");
        }
        if (slot == 12) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#SLIME%");
        }
        if (slot == 13) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#SPIDER%");
        }
        if (slot == 14) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#MAGMA_CUBE%");
        }
        if (slot == 15) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#HORSE%");
        }
        if (slot == 16) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-spawnercount#SQUID%");
        }
        if (slot == 29) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#HOPPER%");
        }
        if (slot == 30) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#DROPPER%");
        }
        if (slot == 31) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#DISPENSER%");
        }
        if (slot == 32) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#CHEST%");
        }
        if (slot == 33) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#TRAPPED_CHEST%");
        }
        if (slot == 34) {
            result.add("&6&l* &e&lQuantity &fx%islandtop_islandtop$rank$-blockcount#BEACON%");
        }
        return result;
    }

}
