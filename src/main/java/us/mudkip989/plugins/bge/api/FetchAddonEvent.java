package us.mudkip989.plugins.bge.api;

import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class FetchAddonEvent extends Event {

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }
}
