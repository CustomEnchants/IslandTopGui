package IslandTopGui.Tasks;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class CreateTop10Inventory implements Runnable {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    public synchronized void run() {
        if (!instance.getFileUtil().display_top10_gui) return;
        instance.top10IslandAtSlot.clear();
        for (int rank = 1; rank <= 10; rank++) {
            if (instance.getUtil().getLeader(rank).isEmpty()) continue;
            int slot = getSlotBasedOnTop10Rank(rank);
            instance.getTop10GuiFileUtil().inventory.setItem(slot, createItem(rank));
            instance.top10IslandAtSlot.put(slot, instance.getUtil().getIslandId(rank));
        }
    }

    private ItemStack createItem(int rank) {
        ItemStack result = instance.getTop10GuiFileUtil().top10Item.clone();
        ItemMeta itemMeta = result.getItemMeta();
        if (itemMeta instanceof SkullMeta) {
            itemMeta = result.getItemMeta();
            ((SkullMeta) itemMeta).setOwner(instance.getUtil().getLeader(rank));
        }
        itemMeta.setDisplayName(instance.getUtil().parsePlaceHolders(rank, instance.getTop10GuiFileUtil().top10Item.getItemMeta().getDisplayName()));
        ArrayList<String> tooltip = new ArrayList<>();
        instance.getTop10GuiFileUtil().top10Item.getItemMeta().getLore().forEach(string -> tooltip.add(instance.getUtil().parsePlaceHolders(rank, string)));
        itemMeta.setLore(tooltip);
        result.setItemMeta(itemMeta);
        return result;
    }

    private int getSlotBasedOnTop10Rank(int rank) {
        return instance.getTop10GuiFileUtil().factionItemSlot.getOrDefault(rank,0);
    }


}
