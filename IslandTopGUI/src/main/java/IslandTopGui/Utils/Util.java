package IslandTopGui.Utils;

import IslandTop.IslandTopPlugin;
import IslandTop.Objects.IslandTopValue;
import IslandTopGui.IslandTopGuiPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    public void openTop10Inventory(Player player) {
            player.openInventory(instance.getTop10GuiFileUtil().inventory);
    }

    public void openTop16Inventory(Player player) {
            player.openInventory(instance.getTop16GuiFileUtil().inventory);
    }

    public String fixColour(String input) {
        return input.isEmpty() ? "" : ChatColor.translateAlternateColorCodes('&', input);
    }

    public ArrayList<String> fixColours(List<String> text) {
        ArrayList<String> result = new ArrayList<>();
        text.forEach(string -> result.add(fixColour(string)));
        return result;
    }

    public String parsePlaceHolders(int rank, String string) {
        string = string
                .replace("%rank%", "" + rank)
                .replace("$rank$", "" + rank)
                .replace("$RANK$", "" + rank);
        string = PlaceholderAPI.setPlaceholders(null, string);
        return fixColour(string);
    }

    public synchronized String getLeader(int position) {
        return IslandTopPlugin.getPluginInstance().getUtil().getIslands().stream().filter(islandTopValue -> islandTopValue.getRank() == position).findFirst().map(IslandTopValue::getLeader).orElse("");
    }

    public synchronized String getIslandId(int position) {
        return IslandTopPlugin.getPluginInstance().getUtil().getIslands().stream().filter(islandTopValue -> islandTopValue.getRank() == position).findFirst().map(IslandTopValue::getID).orElse("");
    }


}
