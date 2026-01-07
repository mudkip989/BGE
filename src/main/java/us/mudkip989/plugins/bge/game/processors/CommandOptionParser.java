package us.mudkip989.plugins.bge.game.processors;

import us.mudkip989.plugins.bge.dataTypes.*;

import javax.annotation.*;

public class CommandOptionParser {

    @Nullable
    public static FlagData parseOptionString(String options, String option){

        String[] vals = options.split(" ");

        String val = "";

        for(String thing: vals){
            if(thing.startsWith("-" + option)){
                val = thing.split("=", 2)[1];
                return new FlagData(val);
            }
        }

        return null;
    }

    @Nullable
    public static FlagData parseOptionInt(String options, String option){

        String[] vals = options.split(" ");

        String val = "";

        for(String thing: vals){
            if(thing.startsWith("-" + option)){
                val = thing.split("=", 2)[1];
                if(val == ""){
                    return new FlagData(0);
                }

                return new FlagData(Integer.parseInt(val));
            }
        }

        return null;
    }

    @Nullable
    public static FlagData parseOptionFloat(String options, String option){

        String[] vals = options.split(" ");

        String val = "";

        for(String thing: vals){
            if(thing.startsWith("-" + option)){
                val = thing.split("=", 2)[1];
                if(val == ""||val == null){
                    return new FlagData(0f);
                }

                return new FlagData(Float.parseFloat(val));
            }
        }

        return null;
    }

    public static Boolean hasFlag(String options, String option){
        String[] vals = options.split(" ");

        for(String thing: vals){
            if(thing == option){
                return true;
            }
        }
        return false;
    }


}
