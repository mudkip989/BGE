package us.mudkip989.plugins.bge;

import us.mudkip989.plugins.bge.api.*;
import us.mudkip989.plugins.bge.util.exceptions.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.*;
import java.util.logging.*;

public class AddonLoader {


    private List<File> addonJars;
    private List<Class<? extends BGEAddon>> detectedAddons;
    private List<Class<? extends BGEAddon>> detectedPluginAddons;
    private HashMap<String, Class<? extends BGEAddon>> addons;
    private HashMap<String, AddonData> namespaceMap;

    private final File addonDir;

    /*
    -fetch jars to addonJars


     */


    public AddonLoader(){
        addons = new HashMap<>();
        addonJars = new ArrayList<>();
        detectedAddons = new ArrayList<>();
        detectedPluginAddons = new ArrayList<>();
        namespaceMap = new HashMap<>();

        addonDir = new File(BGE.instance.getDataFolder().getAbsolutePath() + File.separator + "addons");
        if(!addonDir.exists()){
            addonDir.mkdirs();
        }




    }

    void initialize() {

        fetchAddonJars();

        getAddonClasses();

    }



    /*
    Addon Management Features [/] = partially done,  [X] = done
    [] - Command to disable specific addons
    [/] - Get updated list of all Jars in the Addon folder
    [] - Send a Ping Plugin Addons event to automatically fetch addons from existing plugins
    [] - reload command only loads addons enabled in addons
    [] - when enabling/disabling specific addons, try not to reload everything and to only remove games from said addon.
    [] - reload specific addons

     */

    void fetchAddonJars(){
        addonJars = new ArrayList<>();
        //fetching addon jars
        File[] files = addonDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) {
            //return since no addons
            BGE.instance.logger.fine("No Addons found in addon folder.");
            return;
        }
        BGE.instance.logger.fine("Addon Jars found.");
        addonJars = Arrays.stream(files).toList();
    }

    public void catchPluginAddon(Class<? extends BGEAddon> addonClass){
        detectedPluginAddons.add(addonClass);
    }

    private void getAddonClasses(){
        detectedAddons = new ArrayList<>();

        for(File file: addonJars) {
            try {
                URLClassLoader classLoader = new URLClassLoader(
                        new URL[]{file.toURI().toURL()},
                        this.getClass().getClassLoader()
                );

                Class<? extends BGEAddon> addon = getMainClass(file, classLoader);
                detectedAddons.add(addon);

            }catch (Exception e){
                BGE.instance.getLogger().severe("Failed to load Addon: " + file.getName());
                BGE.instance.getLogger().severe(e.toString());

            }


        }



    }




    void nameSpaceMapping() {



        for(Class<? extends BGEAddon> clazz: detectedPluginAddons){
            try {
                Method getter = clazz.getDeclaredMethod("AddonInfo");
                Object result = getter.invoke(null);
                AddonInfo info = (AddonInfo) result;
                String namespace = info.namespace();
                if(addons.keySet().contains(namespace)){
                    throw new DuplicateNamespaceException();
                }else{
                    addons.put(namespace, clazz);
                }

            }catch (Exception e){
                BGE.instance.getLogger().severe("Failed to get AddonInfo for Class: " + clazz.getName());
                BGE.instance.getLogger().severe(e.toString());
            }
        }

        for(Class<? extends BGEAddon> clazz: detectedAddons){
            try {
                Method getter = clazz.getDeclaredMethod("AddonInfo");
                Object result = getter.invoke(null);
                AddonInfo info = (AddonInfo) result;
                String namespace = info.namespace();
                if(addons.keySet().contains(namespace)){
                    throw new DuplicateNamespaceException();
                }else{
                    addons.put(namespace, clazz);
                }

            }catch (Exception e){
                BGE.instance.getLogger().severe("Failed to get AddonInfo for Class: " + clazz.getName());
                BGE.instance.getLogger().severe(e.toString());
            }
        }


    }










    void oldunloadAddons(){

        for(BGEAddon addon: addons){
            addon.onAddonUnload();
        }
        addons = new ArrayList<>();

    }


    //Replacing with more robust methods
    void oldloadAddons(){

        //fetching addon jars
        File[] files = addonDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) {
            //return since no addons
            return;
        }

        for (File file : files) {
            oldmountAddon(file);
        }


    }



    //Read Through check
    private void oldmountAddon(File file){

        try{


            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{file.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
            Class<? extends BGEAddon> mainClass = getMainClass(file, classLoader);

            BGEAddon addon;
            try {
                addon = mainClass.getConstructor().newInstance(); // Instantiate our main class, the class shouldn't have constructor args.
            } catch (Exception e) {
                BGE.instance.logger.severe("Failed to load addon: " + file.getName());
                BGE.instance.logger.severe(e.toString());
                return;
            }

            try{
                Field classLoaderField = BGEAddon.class.getDeclaredField("classLoader");
                classLoaderField.setAccessible(true);
                Field addonFileField = BGEAddon.class.getDeclaredField("addonFile");
                addonFileField.setAccessible(true);

                classLoaderField.set(addon, classLoader);
                addonFileField.set(addon, file);

                addons.add(addon);
//                addon.onAddonLoad();  //We no longer will load in this method.
            } catch (Exception e) {
                BGE.instance.logger.severe(e.toString());
            }






        } catch (Throwable ex) {
            BGE.instance.logger.severe("Failed to load addon classes from jar " + file.getName());
            BGE.instance.logger.severe(ex.toString());
        }


    }


    /*
    -------------------------WARNING----------------------
    Keep this method. I dont wanna rewrite this right now.

    This was borrowed from BreweryX
     */
    private Class<? extends BGEAddon> getMainClass(File jarFile, ClassLoader classLoader){
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarFile))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) { // Just iterate through every file in the jar file and check if it's a compiled java class.
                if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("BGEAddon.class")) {

                    // We have to replace the '/' with '.' and remove the '.class' extension to get the canonical name of the class. (org.example.Whatever)
                    String className = jarEntry.getName().replaceAll("/", ".").replace(".class", "");
                    try {
                        Class<?> clazz;
                        try {
                            // We don't want to initialize any classes. We'll leave that up to the JVM.
                            clazz = Class.forName(className, false, classLoader);
                        } catch (ClassNotFoundException | NoClassDefFoundError e) {
                            BGE.instance.logger.severe("An exception occurred while trying to load a class from an addon");
                            BGE.instance.logger.severe(e.toString());
                            continue;
                        }
                        if (BGEAddon.class.isAssignableFrom(clazz)) {
                            // Found our main class, we're going to load it now.
                            classLoader.loadClass(className);
                            return clazz.asSubclass(BGEAddon.class);
                        }

                    } catch (ClassNotFoundException e) {
                        BGE.instance.getLogger().log(Level.SEVERE, "Failed to load class " + className, e);
                    }
                }
            }


        } catch (IOException e) {
            BGE.instance.getLogger().log(Level.SEVERE, "Failed to load classes from jar " + jarFile.getName(), e);
        }
        throw new IllegalStateException("No class extending BreweryAddon found in jar " + jarFile.getName());


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
