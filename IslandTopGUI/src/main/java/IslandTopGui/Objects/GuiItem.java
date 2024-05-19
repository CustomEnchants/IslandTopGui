package IslandTopGui.Objects;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GuiItem {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    public ItemStack itemStack;
    public final int slot;

    public GuiItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public ItemStack createItem(int rank) {
        ItemStack result = itemStack.clone();
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.setDisplayName(instance.getUtil().parsePlaceHolders(rank,itemStack.getItemMeta().getDisplayName()));
        if(itemStack.getItemMeta().hasLore()){
            ArrayList<String> newLore = itemStack.getItemMeta().getLore().stream().map(string -> instance.getUtil().parsePlaceHolders(rank, string)).collect(Collectors.toCollection(ArrayList::new));
            itemMeta.setLore(newLore);
        }
        result.setItemMeta(itemMeta);
        return result;
    }

    private int getProperAmount(int number) {
        return number == 0 ? 1 : Math.min(number, 64);
    }


}
