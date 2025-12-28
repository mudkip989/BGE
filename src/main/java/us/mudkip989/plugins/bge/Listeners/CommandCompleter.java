package us.mudkip989.plugins.bge.Listeners;

import net.kyori.adventure.text.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.jetbrains.annotations.*;
import us.mudkip989.plugins.bge.*;

import java.util.*;

public class CommandCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> returns = new ArrayList<>();
        if(strings.length == 1){
            returns = new ArrayList<>(Arrays.asList("create", "about", "delete", "reload"));
        }
        switch (strings[0]){
            case "create" -> {
                if(strings.length == 2) {
                    returns = BGE.instance.getGameIds().stream().filter(str -> str.startsWith(strings[1])).toList();
                }
            }
            case "about" -> {
            }
            case "delete" -> {
                if(strings.length == 2) {
                    List<UUID> list = BGE.gameInstances.keySet().stream().filter(uuid -> uuid.toString().startsWith(strings[1])).toList();
                    returns = new ArrayList<>();
                    for(UUID uid: list){
                        returns.add(uid.toString());
                    }
                }
            }

        }
        return returns;
    }
}
