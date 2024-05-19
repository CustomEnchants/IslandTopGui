package IslandTopGui.Objects;

import IslandTop.Objects.IslandTopValue;
import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class IndividualGui {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();
    public final Inventory inventory;

    public IndividualGui(IslandTopValue islandTopValue) {
        inventory = Bukkit.createInventory(null, instance.getGuiFileUtil().individual_gui_size, instance.getUtil().parsePlaceHolders(islandTopValue.getRank(), instance.getGuiFileUtil().individual_gui_name));
        instance.getGuiFileUtil().individual_filler_block_item_slots.forEach(slot -> inventory.setItem(slot, instance.getGuiFileUtil().individual_filler_block_item));
        instance.getGuiItems().forEach(guiItem -> inventory.setItem(guiItem.slot, guiItem.createItem(islandTopValue.getRank())));
    }

}
