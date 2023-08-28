package classes;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

public class Draw extends JLabel {
    public static int BOARDER_DIST = 5;
    public static int X = BOARDER_DIST * 2;
    public static int Y = 0;
    public static int W = 0;
    public static int H = 50;
    public static int boarderDist = 5;
    public static int x = boarderDist * 2;
    public static int y = 0;
    public static int w = 0;
    public static int h = 50;

    public void paintComponent(Graphics g) {
        w = Window.draw.getWidth() - boarderDist * 4;
        y = boarderDist * 2 + SoundBarMain.scroll;
        g.setColor(Color.gray);
        g.fillRect(0, 0, Window.draw.getWidth(), Window.draw.getHeight());
        for (BackgroundMusic m : SoundBarMain.backgrounds) {
            Sound s = SoundBarMain.sounds[m.index];
            g.setColor(Color.lightGray);
            g.fillRect(x, y, w, h);
            g.setColor(Color.BLACK);
            g.drawString(s.name.substring(10), x + boarderDist, (int) (y + boarderDist + g.getFontMetrics().getStringBounds(s.name, g).getHeight() * 2 / 3));
            g.setColor(Color.GREEN);
            g.fillRect(x + boarderDist, y + h - boarderDist - h / 3, w / 10, h / 3);
            g.setColor(Color.RED);
            g.fillRect(x + boarderDist + w / 2 - w / 10, y + h - boarderDist - h / 3, w / 10, h / 3);
            g.setColor(Color.DARK_GRAY);
            if (s.clip != null) {
                g.fillRect(x + boarderDist + w / 10, y + h - boarderDist - h / 3, (int) ((float) ((float) s.clip.getFramePosition() / (float) s.clip.getFrameLength()) * (float) (w / 2 - w / 5)), h / 3);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x + boarderDist, y + h  - boarderDist - h / 3, w / 2, h / 3);
            

            //Images 1:CrossFade 2:Repeat 3:FadeIn 4:FadeOut 5:InRow 6:Stop
            if (SoundBarMain.playingBackground == m.index) {
                g.drawImage(SoundBarMain.uiElement[3], x + w - (h + boarderDist) * 3, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                if (SoundBarMain.backgroundRepeat) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2);
                }
                g.drawImage(SoundBarMain.uiElement[1], x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                if (!SoundBarMain.sounds[SoundBarMain.playingBackground].clip.isRunning()) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x + w - (h + boarderDist), y + boarderDist, h - boarderDist * 2, h - boarderDist * 2);
                }
                g.drawImage(SoundBarMain.uiElement[5], x + w - (h + boarderDist), y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
            } else {
                if (SoundBarMain.playingBackground == -1) {
                    g.drawImage(SoundBarMain.uiElement[2], x + w - (h + boarderDist) * 3, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                    if (SoundBarMain.fadeToBackground == m.index) {
                        g.setColor(Color.YELLOW);
                        g.fillRect(x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2);
                    }
                    g.drawImage(SoundBarMain.uiElement[4], x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                } else {
                    g.drawImage(SoundBarMain.uiElement[0], x + w - (h + boarderDist) * 3, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                    if (SoundBarMain.fadeToBackground == m.index) {
                        g.setColor(Color.YELLOW);
                        g.fillRect(x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2);
                    }
                    g.drawImage(SoundBarMain.uiElement[4], x + w - (h + boarderDist) * 2, y + boarderDist, h - boarderDist * 2, h - boarderDist * 2, null);
                }
            }

            y += h + boarderDist;
        }
    }
}
