package spawnerapi;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class SpawnerUtils {

    public static Material getSpawner() {
        Optional<Material> option = Arrays.stream(Material.values()).filter(material -> material.name().equalsIgnoreCase("SPAWNER")).findFirst();
        return option.orElseGet(() -> Material.getMaterial("MOB_SPAWNER"));
    }

    public static String fixColour(String input) {
        return input.isEmpty() ? "" : ChatColor.translateAlternateColorCodes('&', input);
    }


    public static ItemStack getFillerBlock(){
        Optional<Material> option = Arrays.stream(Material.values()).filter(material -> material.name().equalsIgnoreCase("BLACK_STAINED_GLASS_PANE")).findFirst();
        ItemStack filler = option.map(ItemStack::new).orElseGet(() -> new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 15));
        ItemMeta itemMeta = filler.getItemMeta();
        itemMeta.setDisplayName(fixColour("&d"));
        filler.setItemMeta(itemMeta);
        return filler;
    }

    public static ItemStack getDefaultTop10Skull(){
        ItemStack itemStack = getSkull().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("&e&l[!] &6&n%islandtop_islandtop$rank$-leader%'s Island&r &7(#$RANK$)");
        itemMeta.setLore(getDefaultTop10Lore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getDefaultTop16Skull(){
        ItemStack itemStack = getSkull().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("&e&l[!] &6&n%islandtop_islandtop$rank$-leader%'s Island&r &7(#$RANK$)");
        itemMeta.setLore(getDefaultTop16Lore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getSkull(){
        Optional<Material> option = Arrays.stream(Material.values()).filter(material -> material.name().equalsIgnoreCase("PLAYER_HEAD")).findFirst();
        return option.map(ItemStack::new).orElseGet(() -> new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) 3));
    }



    public static Material getSkullItem(){
        Optional<Material> option = Arrays.stream(Material.values()).filter(material -> material.name().equalsIgnoreCase("PLAYER_HEAD")).findFirst();
        return option.orElseGet(() -> Material.getMaterial("SKULL_ITEM"));
    }

    public static EntityType getEntityType(ItemStack is) {
        if (is.getType() != SpawnerUtils.getSpawner()) return null;
        if (!(is.getItemMeta() instanceof BlockStateMeta)) return null;
        BlockStateMeta bsm = (BlockStateMeta) is.getItemMeta();
        if (!(bsm.getBlockState() instanceof CreatureSpawner)) return null;
        return ((CreatureSpawner) bsm.getBlockState()).getSpawnedType();
    }

    public static String getEntityName(String spawnerName) {
        switch (spawnerName) {
            case "IRON_GOLEM": {
                return "IronGolem";
            }
            case "MAGMA_CUBE": {
                return "MagmaCube";
            }
            case "CAVE_SPIDER": {
                return "CaveSpider";
            }
            case "MUSHROOM_COW":{
                return "MushroomCow";
            }
            case "ENDER_DRAGON":{
                return "EnderDragon";
            }
            default: {
                return spawnerName.substring(0, 1).toUpperCase() + spawnerName.substring(1).toLowerCase();
            }
        }
    }

    public static EntityType getEntityType(String name) {
        switch (name.toLowerCase()) {
            case "irongolem": {
                return EntityType.IRON_GOLEM;
            }
            case "magmacube": {
                return EntityType.MAGMA_CUBE;
            }
            case "cavespider": {
                return EntityType.CAVE_SPIDER;
            }
            case "mushroomcow":{
                return EntityType.MUSHROOM_COW;
            }
            default: {
                return Arrays.stream(EntityType.values()).filter(entityType -> entityType.name().equalsIgnoreCase(name)).findFirst().orElse(null);
            }
        }
    }

    private static ArrayList<String> getDefaultTop10Lore() {
        ArrayList<String> result = new ArrayList<>();
        result.add("");
        result.add("&6&l* &e&lPlace &7#$rank$");
        result.add("&6&l* &e&lWorth &7$%islandtop_islandtop$rank$-totalworth%");
        result.add("");
        result.add("&7(( &fLeft-Click &7to view their items. ))");
        result.add("&7(( &fRight-Click &7to view their island info. ))");
        return result;
    }

    private static ArrayList<String> getDefaultTop16Lore() {
        ArrayList<String> result = new ArrayList<>();
        result.add("");
        result.add("&6&l* &e&lPlace &7#$rank$");
        result.add("&6&l* &e&lWorth &7$%islandtop_islandtop$rank$-totalworth%");
        result.add("");
        result.add("&7(( &fLeft-Click &7to view their items. ))");
        result.add("&7(( &fRight-Click &7to view their island info. ))");
        return result;
    }


}
