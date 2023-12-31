package classes;

public class SmallTimer extends Thread {
    
    public Sound sound;
    
    public void run() {
        cycle();
    }

    private void cycle() {
        while(SoundBarMain.running) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tick();
        }
    }

    private void tick() {
        if (sound.clip != null) {
            System.out.println("FrameLength: " + sound.clip.getFrameLength() + " " + sound.toDelete);
        } else {
            System.out.println("Empty");
        } 
    }
}
