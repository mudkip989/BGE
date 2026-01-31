package us.mudkip989.plugins.bge.api;

import us.mudkip989.plugins.bge.*;

import java.lang.reflect.*;

public class AddonData {

    private Class<? extends BGEAddon> addon;
    private AddonInfo addonInfo;
    private boolean enabled = false;

    public AddonData(Class<? extends BGEAddon> mClass) {
        addon = mClass;
        try{
            Method getter = mClass.getDeclaredMethod("getAddonInfo");
            Object result = getter.invoke(null);
            addonInfo = (AddonInfo) result;

        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        this.enabled = true;
    }

    public AddonData(Class<? extends BGEAddon> mClass, boolean enabled){
        addon = mClass;
        try{
            Method getter = mClass.getDeclaredMethod("getAddonInfo");
            Object result = getter.invoke(null);
            addonInfo = (AddonInfo) result;

        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        this.enabled = enabled;
    }


    public Class<? extends BGEAddon> getMainClass() {
        return addon;
    }

    public AddonInfo getAddonInfo() {
        return addonInfo;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
