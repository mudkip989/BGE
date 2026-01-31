package us.mudkip989.plugins.bge.api;

import org.jetbrains.annotations.*;

import java.io.*;
import java.net.*;

public abstract class BGEAddon {

    private URLClassLoader classLoader;
    private File addonFile;
    private static AddonInfo addonInfo;

    @NotNull
    public static AddonInfo getAddonInfo() {
        return addonInfo;
    }


    public void onAddonLoad() {
        // Code for this addon which runs after the addon is enabled.
    }

    public void onAddonUnload() {
        // Code for this addon which runs before the addon is disabled.
    }


}
