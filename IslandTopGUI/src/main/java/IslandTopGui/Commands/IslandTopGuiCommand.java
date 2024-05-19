package IslandTopGui.Commands;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class IslandTopGuiCommand implements Listener {

    public final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        Player p = event.getPlayer();
        switch(command.toLowerCase()){
            case "/island top":
            case "/is top":
            case "/istop":{
                openGui(p);
                event.setCancelled(true);
                break;
            }
            default:{
                break;
            }
        }
    }

    private void openGui(Player player) {
        instance.playersInGui.add(player.getUniqueId());
        if (instance.getFileUtil().display_top10_gui && instance.getFileUtil().display_top16_gui) {
            instance.getUtil().openTop16Inventory(player);
            return;
        }
        if (instance.getFileUtil().display_top10_gui) {
            instance.getUtil().openTop10Inventory(player);
            return;
        }
        if (instance.getFileUtil().display_top16_gui) {
            instance.getUtil().openTop16Inventory(player);
        }

    }


}
