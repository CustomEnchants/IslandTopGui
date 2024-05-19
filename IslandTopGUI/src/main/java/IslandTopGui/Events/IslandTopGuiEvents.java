package IslandTopGui.Events;

import IslandTop.IslandTopPlugin;
import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class IslandTopGuiEvents implements Listener {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    private void handleClick(ClickType clickType, String islandId, Player player) {
        if(clickType !=ClickType.LEFT){
            return;
        }
        if (!instance.getGuis().containsKey(islandId)) return;
        player.openInventory(instance.getGuis().get(islandId).inventory);
        instance.playersInGui.add(player.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (!instance.playersInGui.contains(player.getUniqueId())) {
            return;
        }
        event.setCancelled(true);
        String id = "";
        String gui = "";
        if (event.getView().getTitle().equalsIgnoreCase(instance.getTop16GuiFileUtil().inventoryName)) {
            if (!instance.top16IslandAtSlot.containsKey(event.getSlot())) return;
            id = instance.top16IslandAtSlot.get(event.getSlot());
            gui = "top16";
        }
        if (event.getView().getTitle().equalsIgnoreCase(instance.getTop10GuiFileUtil().inventoryName)) {
            if (!instance.top10IslandAtSlot.containsKey(event.getSlot())) return;
            id = instance.top10IslandAtSlot.get(event.getSlot());
            gui = "top10";
        }
        if (!IslandTopPlugin.getPluginInstance().islandTopValueContainer.containsKey(id)) {
            switch(gui) {
                default:{
                    break;
                }
                case "top16": {
                    instance.getTop16GuiFileUtil().inventory.setItem(event.getSlot(), new ItemStack(Material.AIR));
                    instance.top16IslandAtSlot.remove(event.getSlot());
                    break;
                }
                case "top10": {
                    instance.getTop10GuiFileUtil().inventory.setItem(event.getSlot(), new ItemStack(Material.AIR));
                    instance.top10IslandAtSlot.remove(event.getSlot());
                    break;
                }
            }
            return;
        }
        if(id.isEmpty()){
            return;
        }
        handleClick(event.getClick(), id, player);
    }

    @EventHandler
    public void onInventoryDragItem(InventoryDragEvent event) {
        if (!instance.playersInGui.contains(event.getWhoClicked().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!instance.playersInGui.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        instance.playersInGui.remove(event.getPlayer().getUniqueId());
    }


}
