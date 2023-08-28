package classes;


public class Timer extends Thread {
    private int t = 0;
    public void run() {
        loop();
    }

    private void loop() {
        while(SoundBarMain.running) {
            try {
                sleep(1000 / SoundBarMain.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tick();
        }
    }

    public void tick() {
        for (Script s : SoundBarMain.scripts) {
            s.tick();
        }
        for (Sound s : SoundBarMain.sounds) {
            s.tick();
        }
        
        if (t == 0) {
            updateView();
        }
        t = (t+1) % SoundBarMain.updateSeq;
    }

    public void updateView() {
        if (Window.window != null) {
            Window.window.repaint();
        }
    }
}