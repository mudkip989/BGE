package us.mudkip989.plugins.bge.api;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.*;
import java.util.HashMap;

public abstract class BGEAddon {
    private URLClassLoader classLoader;
    private File addonFile;
    private AddonInfo addonInfo;

    @NotNull
    public AddonInfo getAddonInfo() {
        return addonInfo;
    }

    public void onAddonLoad() {
        // Code for this addon which runs after the addon is enabled.
    }

    public void onAddonUnload() {
        // Code for this addon which runs before the addon is disabled.
    }
}