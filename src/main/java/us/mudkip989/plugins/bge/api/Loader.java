package us.mudkip989.plugins.bge.api;

import us.mudkip989.plugins.bge.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Loader {


    private List<BGEAddon> addons;

    private File addonDir;


    public Loader(){

        addonDir = new File(BGE.instance.getDataFolder().getAbsolutePath() + File.separator + "addons");
        if(!addonDir.exists()){
            addonDir.mkdirs();
        }

    }

    private void loadAddons(){
        //fetching addon jars
        File[] files = addonDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) {
            //return since no addons
            return;
        }


    }

    private void loadAddon(File file){

        try{

            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{file.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
            Class mainClass =

        } catch (Throwable ex) {
            BGE.instance.logger.severe("Failed to load addon classes from jar " + file.getName());
            BGE.instance.logger.severe(ex.toString());
        }


    }

    private Class<? extends BGEAddon> getMainClass(File jarFile, ClassLoader classLoader){



    }



//    public Set<Class<?>> findClasses(String packageName) throws IOException {
//        URLClassLoader classLoader = new URLClassLoader(
//                new URL[]{ getAddonFile().toURI().toURL() },
//                this.getClass().getClassLoader()
//        );
//        return ClassPath.from(classLoader)
//                .getAllClasses()
//                .stream()
//                .filter(clazz -> clazz.getPackageName()
//                        .equalsIgnoreCase(packageName)) // should just be equals instead of equalsIgnoreCase probs
//                .map(ClassPath.ClassInfo::load)
//                .collect(Collectors.toSet());
//    }



//    public static void main() throws IOException {
//        Path dir = Paths.get("Games");
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.jar")) {
//            for (Path jarPath : stream) {
//
//                try (JarFile jar = new JarFile(jarPath.toFile())) {
//                    Manifest manifest = jar.getManifest();
//                    if (manifest == null) continue;
//
//                    String mainClassName = manifest
//                            .getMainAttributes()
//                            .getValue("Main-Class");
//
//                    if (mainClassName == null) continue;
//
//                    URLClassLoader loader = new URLClassLoader(
//                            new URL[]{ jarPath.toUri().toURL() },
//                            ClassLoader.getSystemClassLoader()
//                    );
//
//                    Class<?> mainClass = Class.forName(mainClassName, true, loader);
//
//                    Method main = mainClass.getMethod("main", String[].class);
//
//                    System.out.println("Running " + jarPath.getFileName());
//                    main.invoke(null, (Object) new String[0]);
//                } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
//                         IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
}
