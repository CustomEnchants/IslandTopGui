package IslandTopGui.Tasks;

import IslandTop.IslandTopPlugin;
import IslandTop.Objects.IslandTopValue;
import IslandTopGui.IslandTopGuiPlugin;
import IslandTopGui.Objects.IndividualGui;

import java.util.LinkedList;

public class CreateIslandInventories implements Runnable {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    public synchronized void run() {
        final LinkedList<IslandTopValue> islands = IslandTopPlugin.getPluginInstance().getUtil().getIslands();
        for (IslandTopValue islandTopValue : islands) {
            instance.getGuis().put(islandTopValue.getID(), new IndividualGui(islandTopValue));
        }
    }
}
