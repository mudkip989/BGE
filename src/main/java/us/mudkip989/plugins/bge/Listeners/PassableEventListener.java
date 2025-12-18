package us.mudkip989.plugins.bge.Listeners;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import us.mudkip989.plugins.bge.*;
import us.mudkip989.plugins.bge.game.*;

import java.util.*;

public class PassableEventListener implements Listener {

    @EventHandler
    public void interactEvent(PlayerInteractEntityEvent e){
        Entity ent = e.getRightClicked();
        Set<String> tags = ent.getScoreboardTags();
        UUID gameId = null;
        if(tags.contains("bge") && tags.contains("interaction")){

            for(String tag: tags){
                if(tag.startsWith("game:")){
                    gameId = UUID.fromString(tag.substring(5));
                    break;
                }
            }
            if(gameId != null) {
                Game gam = BGE.gameInstances.get(gameId);
                gam.ClickEvent(e);
            }
        }


    }

}
