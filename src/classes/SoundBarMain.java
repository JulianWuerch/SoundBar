package classes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class SoundBarMain {
    public static boolean running = true, preload = false, backgroundRepeat = false, backgroundCascade = true;
    public static Sound[] sounds;
    public static Script[] scripts;
    public static short FPS = 10, updateSeq = FPS, forceUpdate = 10;
    public static Timer timer;
    public static BackgroundMusic[] backgrounds;
    public static int playingBackground = -1, fadeToBackground = -1, softNext = -1;
    public static int scroll = 0, scrollMulti = 8;
    public static BufferedImage[] uiElement;
    public static short standartFadeOutTime = 3;
    public static String srcPath = "";
    public static String[] keyword = {"window", "start", "fade", "stop", "pause", "run", "res", "repeat", "spfade", "stopfade", "setRePa", "addRePa", "setDel", "addDel", "autoRep", "setFPS", "setUpdateSeq", "replaceCommand", "setSrcPath", "setStandardFadeTime", "restard", "end"};
    public static String[] KEYWORD = {"window", "start", "fade", "stop", "pause", "run", "res", "repeat", "spfade", "stopfade", "setRePa", "addRePa", "setDel", "addDel", "autoRep", "setFPS", "setUpdateSeq", "replaceCommand", "setSrcPath", "setStandardFadeTime", "restard", "end"};
    public static boolean runSetupScripts = true;
    public static int maxSoundsToRead = 40;
    public static boolean changeFlag = true;


    public static void main(String[] args) throws Exception {
        System.out.println("Starting SoundBar");
        setup();
        timer = new Timer();
        timer.start();
        Window.openWindow();
        Scanner in = new Scanner(System.in);
        while (running) {
            use(in.nextLine());
        }
        in.close();
    }

    public static void use(String s) {
        System.out.println(s);
        String[] order = s.split(" ");
        if (order.length < 4) {
            String[] or = new String[4];
            for (int i = 0; i < 4; i++) {
                if (i < order.length) {
                    or[i] = order[i];
                } else {
                    or[i] = "0";
                }
            }
            order = or;
        }
        

        int ind = 0;
        for (String test : keyword) {
            if (test.equals(order[0])) {
                order[0] = KEYWORD[ind];
            }
            ind++;
        }

        switch (order[0]) {
            case "window":
                if (Window.window != null) {
                    Window.window.dispose();
                }
                Window.openWindow();
            break;
            case "start":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        if (order.length < 4) {
                            so.start();
                            so.fade(1f, (short) 0);
                        } else {
                            so.start();
                            so.fade(Float.parseFloat(order[2]), Short.parseShort(order[3]));
                        }
                        return;
                    }
                }

                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        if (order.length < 4) {
                            so.start();
                            so.fade(1f, (short) 0);
                        } else {
                            so.fade(Float.parseFloat(order[2]), Short.parseShort(order[3]));
                        }
                        return;
                    }
                }
            break;
            case "fade":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.fade(Float.parseFloat(order[2]), Short.parseShort(order[3]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.fade(Float.parseFloat(order[2]), Short.parseShort(order[3]));
                        return;
                    }
                }
            break;
            case "stop": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.stop();
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.stop();
                        return;
                    }
                }
            break;
            case "pause":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.pause(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.pause(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "run":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.run();
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.run();
                        return;
                    }
                }
            break;
            case "res":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.reset();
                        System.gc();
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.reset();
                        System.gc();
                        return;
                    }
                }
            break;
            case "repeat": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.repeat(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.repeat(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "spfade":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.pauseFade(Short.parseShort(order[2]), Short.parseShort(order[3]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.pauseFade(Short.parseShort(order[2]), Short.parseShort(order[3]));
                        return;
                    }
                }
            break;
            case "stopfade":
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.stFade(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.stFade(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "setRePa": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.setRePause(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.setRePause(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "addRePa": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.addRePause(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.addRePause(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "setDel": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.setDelay(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.setDelay(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "addDel": 
                for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.addDelay(Short.parseShort(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.addDelay(Short.parseShort(order[2]));
                        return;
                    }
                }
            break;
            case "autoRep":
                if (order[2].equals("0")) {
                    order[2] = "True";
                }
                 for (Sound so : sounds) {
                    if (so.name.equals(order[1])) {
                        so.setAutoRep(Boolean.parseBoolean(order[2]));
                        return;
                    }
                }
                for (Sound so : sounds) {
                    if (so.name.startsWith(order[1])) {
                        so.setAutoRep(Boolean.parseBoolean(order[2]));
                        return;
                    }
                }
            break;
            case "setFPS":
                FPS = Short.parseShort(order[1]);
                updateSeq = FPS;
            break;
            case "setUpdateSeq":
                updateSeq = (Short.parseShort(order[1]));
            break;
            case "replaceCommand":
                int indexer = 0;
                for (String search : keyword) {
                    if (search.equals(order[1])) {
                        keyword[indexer] = order[2];
                        return;
                    }
                    indexer++;
                }
            break;
            case "setSrcPath":
                srcPath = order[1];
            break;
            case "setStandardFadeTime":
                standartFadeOutTime = Short.parseShort(order[1]);
            break;
            case "restard": 
                setup();
                Window.openWindow();
            break;
            case "end": System.exit(0);
            break;
            default: 
                for (Script sc : scripts) {
                    if (sc.name.equals(order[0] + ".txt")) {
                        sc.run(order);
                        return;
                    }
                }
                System.out.println("SoundMain.use: No script found. exeting  [X]");
            break;
        }
    }

    public static void playNext() {
        
        if (fadeToBackground == -1 && softNext != -1) {
            fadeToBackground = softNext;
            softNext = -1;
        }
        if (fadeToBackground == -1 && backgroundCascade) {
            fadeToBackground = (playingBackground + 1) % backgrounds.length; 
        }
        if (fadeToBackground != -1) {
            use("fade " + sounds[fadeToBackground].name + " 1 " + standartFadeOutTime);
			playingBackground = fadeToBackground;
			fadeToBackground = -1;
        }
    }

    public static void setup() {
        File f = new File("src/images");
        if (f.exists()) {
            String[] fileNames = {"CrossFade.png", "Repeat.png", "FadeIn.png", "FadeOut.png", "InRow.png", "Stop.png", "Icon.png"};
            uiElement = new BufferedImage[fileNames.length];
            for (int i = 0; i < fileNames.length; i++) {
                try {
                    uiElement[i] = ImageIO.read(new File("src/images/" + fileNames[i]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }
        srcPath = new File("").getAbsolutePath();
        srcPath = srcPath.substring(0, srcPath.length() - 8) + "resources";

        File file = new File(srcPath + "/sounds");
        System.out.println("SoundMain.setup: Searching in Folder: " + file.getAbsolutePath() + "  [I]");
        if (file.exists()) {
            ArrayList<Integer> backs = new ArrayList<>();
            String[] fileNames = file.list();
            int countToLoad = Math.min(fileNames.length, maxSoundsToRead);

            sounds = new Sound[countToLoad];
            for (int i = 0; i < countToLoad; i++) {
                if (fileNames[i].startsWith("Background")) {
                    backs.add(i);
                }
                sounds[i] = new Sound(fileNames[i], i);
            }
            
            backgrounds = new BackgroundMusic[backs.size()];
            for (int i = 0; i < backgrounds.length; i++) {
                backgrounds[i] = new BackgroundMusic(backs.get(i));
            }
        }

        file = new File(srcPath + "/files");
        if (file.exists()) {
            String[] fileNames = file.list();
            scripts = new Script[fileNames.length];
            for (int i = 0; i < fileNames.length; i++) {
                scripts[i] = new Script(fileNames[i]);
            }
        }
    }
}