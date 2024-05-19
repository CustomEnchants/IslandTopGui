package IslandTopGui.Commands;

import IslandTopGui.IslandTopGuiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenIslandValueGui implements CommandExecutor {

    private final IslandTopGuiPlugin instance = IslandTopGuiPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("OpenIslandWorthGui")){
            if(!(cs instanceof Player)) {
                if(args.length < 2){
                    cs.sendMessage("Command Usage: /"+command.getName() + " <player> <factionid>");
                    return false;
                }
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if(targetPlayer == null){
                    cs.sendMessage("The player you tried to open the gui for is offline!");
                    return false;
                }
                if(!instance.getGuis().containsKey(args[1])){
                    cs.sendMessage("Could not find GUI for Island with ID"+args[1]);
                    return false;
                }
                targetPlayer.openInventory(instance.getGuis().get(args[1]).inventory);
                instance.playersInGui.add(targetPlayer.getUniqueId());
                cs.sendMessage("Opened the gui for "+targetPlayer.getName() + " with information about island with the id of "+args[1]);
                return false;
            }
            if(args.length < 1){
                cs.sendMessage("Please specify a faction ID");
                return false;
            }
            if(!instance.getGuis().containsKey(args[0])){
                cs.sendMessage("Could not find GUI for Faction with ID "+args[0]);
                return false;
            }
            Player player = (Player) cs;
            player.openInventory(instance.getGuis().get(args[0]).inventory);
            instance.playersInGui.add(player.getUniqueId());
            return false;
        }
        return false;
    }
}
