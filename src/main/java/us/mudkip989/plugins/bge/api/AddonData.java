package us.mudkip989.plugins.bge.api;

import us.mudkip989.plugins.bge.*;

import java.lang.reflect.*;

public class AddonData {

    private BGEAddon addon;
    private AddonInfo addonInfo;
    private boolean enabled = false;

//    try{
//        Method getter = mClass.getDeclaredMethod("getAddonInfo");
//        Object result = getter.invoke(null);
//        addonInfo = (AddonInfo) result;
//
//    } catch (Exception e) {
//        System.out.println(e);
//        return;
//    }

    public AddonData(BGEAddon mClass) {
        addon = mClass;
        addonInfo = addon.AddonInfo();
        this.enabled = true;
    }

    public AddonData(BGEAddon mClass, boolean enabled){
        addon = mClass;
        addonInfo = addon.AddonInfo();
        this.enabled = enabled;
    }


    public BGEAddon getMainClass() {
        return addon;
    }

    public AddonInfo getAddonInfo() {
        return addonInfo;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
