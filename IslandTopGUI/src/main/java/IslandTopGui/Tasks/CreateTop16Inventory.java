package IslandTopGui.Tasks;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class CreateTop16Inventory implements Runnable {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    public synchronized void run() {
        if (!instance.getFileUtil().display_top16_gui) return;
        instance.top16IslandAtSlot.clear();
        for (int rank = 1; rank <= 16; rank++) {
            if (instance.getUtil().getLeader(rank).isEmpty()) continue;
            int slot = getSlotBasedOnTop16Rank(rank);
            instance.getTop16GuiFileUtil().inventory.setItem(slot, createItem(rank));
            instance.top16IslandAtSlot.put(slot, instance.getUtil().getIslandId(rank));
        }
    }

    private ItemStack createItem(int rank) {
        ItemStack result = instance.getTop16GuiFileUtil().top16Item.clone();
        ItemMeta itemMeta = result.getItemMeta();
        if (itemMeta instanceof SkullMeta) {
            itemMeta = result.getItemMeta();
            ((SkullMeta) itemMeta).setOwner(instance.getUtil().getLeader(rank));
        }
        itemMeta.setDisplayName(instance.getUtil().parsePlaceHolders(rank, instance.getTop16GuiFileUtil().top16Item.getItemMeta().getDisplayName()));
        ArrayList<String> tooltip = new ArrayList<>();
        instance.getTop16GuiFileUtil().top16Item.getItemMeta().getLore().forEach(string -> tooltip.add(instance.getUtil().parsePlaceHolders(rank, string)));
        itemMeta.setLore(tooltip);
        result.setItemMeta(itemMeta);
        return result;
    }

    private int getSlotBasedOnTop16Rank(int rank) {
        return instance.getTop16GuiFileUtil().factionItemSlot.getOrDefault(rank,0);
    }

}
