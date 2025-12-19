package us.mudkip989.plugins.bge.Listeners;


import net.kyori.adventure.text.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.*;

import java.util.*;

public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        Player p = (Player) sender;

        if(args.length == 0){
            args = new String[1];
            args[0] = "about";
        }

        switch (args[0]){
            case "create" -> {
                if(BGE.instance.startGame(args[1], p.getLocation())){
                    Bukkit.broadcast(Component.text("Done"));
                }
            }
            case "about" -> {
                p.sendMessage("Test");
            }

        }




        //Temporary Workaround
//        if(args.length == 1){
//            if(BGE.instance.startGame(args[0], p.getLocation())){
//                Bukkit.broadcast(Component.text("Done"));
//            }
//        }

        return true;
    }
}
