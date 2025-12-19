package us.mudkip989.plugins.bge.game.processors;

public class CommandOptionParser {

    public String parseOptionString(String options, String option){

        String[] vals = options.split(" ");

        String val = "";

        for(String thing: vals){
            if(thing.startsWith("-" + option)){
                val = thing.split("=", 1)[1];
            }
        }

        return val;
    }

    public Integer parseOptionInt(String options, String option){

        String[] vals = options.split(" ");

        String val = "";

        for(String thing: vals){
            if(thing.startsWith("-" + option)){
                val = thing.split("=", 1)[1];
            }
        }

        return Integer.parseInt(val);
    }

}
