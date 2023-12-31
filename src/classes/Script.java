package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Script {
    public String name;
    public String[] actions;
    public boolean running = false;
    public int ticks = 0;
    public int workVal;
    public String[] input;

    public Script(String name) {
        this.name = name;
        File f = new File(SoundBarMain.srcPath + "/files/" + name);
        try {
            Scanner scanner = new Scanner(f);
            ArrayList<String> ac = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ac.add(scanner.nextLine());
            }
            actions = (String[]) ac.toArray();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (SoundBarMain.runSetupScripts && name.equals("setupScript.txt")) {
            String[] in = {};
            run(in);
        }
    }

    public void run(String[] in) {
        running = true;
        ticks = 0;
        input = in;
        for (int i = 0; i < input.length; i++) {
            for (int ii = 0; ii < actions.length; ii++) {
                actions[ii] = actions[ii].replace("%" + i + "%", input[i]);
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void tick() {
        if (running) {
            for (String s : actions) {
                if (s.startsWith(ticks + " ")) {
                    eval(s);
                }
            }
            ticks++;
        }
    }

    public void eval(String s) {
        String[] task = s.split(" ");
        if (task[0].startsWith("#")) {
            command(s);
        } else {
            String toUse = "";
            for (int i = 1; i < task.length; i++) {
                toUse = task[i] + " ";
            }
            SoundBarMain.use(toUse);
        }
    }

    public void command(String s) {
        String[] task = s.split(" ");
        switch (task[1]) {
            case "save": workVal = Integer.parseInt(task[2]);
            break;
            case "add": workVal += Integer.parseInt(task[2]);
            break;
            case "sub": workVal -= Integer.parseInt(task[2]);
            break;
            case "mult": workVal *= Integer.parseInt(task[2]);
            break;
            case "div": workVal /= Integer.parseInt(task[2]);
            break;
            case "clear": workVal = 0;
            break;
            case "if": 
                if (workVal == Integer.parseInt(task[2])) {
                    String toUse = "";
                    for (int i = 3; i < task.length; i++) {
                        toUse = task[i] + " ";
                    }
                    eval(toUse.replace("§", " "));
                }
            break;
            case "smaler": 
                if (workVal > Integer.parseInt(task[2])) {
                    String toUse = "";
                    for (int i = 3; i < task.length; i++) {
                        toUse = task[i] + " ";
                    }
                    eval(toUse.replace("§", " "));
                }
            break;
            case "greater": 
                if (workVal < Integer.parseInt(task[2])) {
                    String toUse = "";
                    for (int i = 3; i < task.length; i++) {
                        toUse = task[i] + " ";
                    }
                    eval(toUse.replace("§", " "));
                }
            break;
            case "bitAnd": workVal = workVal & Integer.parseInt(task[2]);
            break;
            case "bitOr": workVal = workVal | Integer.parseInt(task[2]);
            break;
            case "bitXOr": workVal = workVal ^ Integer.parseInt(task[2]);
            break;
            case "bitComp": workVal = ~workVal;
            break;
            case "bitShift<": workVal = workVal << Integer.parseInt(task[2]);
            break;
            case "bitShift>": workVal = workVal >> Integer.parseInt(task[2]);
            break;
            case "bitShift>>>": workVal = workVal >>> Integer.parseInt(task[2]);
            break;
            case "settick": ticks = Integer.parseInt(task[2]);
            break;
            case "addtick": ticks += Integer.parseInt(task[2]);
            break;
            case "subtick": ticks -= Integer.parseInt(task[2]);
            break;
            case "sysout": 
                    String toUse = "";
                    for (int i = 2; i < task.length; i++) {
                        toUse = task[i] + " ";
                    }
                System.out.println("ToUse: " + toUse);
            break;
        }
    }
}