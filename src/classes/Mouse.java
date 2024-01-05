package classes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Mouse implements MouseListener, MouseWheelListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        
        int y = e.getY() - SoundBarMain.scroll - Draw.boarderDist * 2;
        y = y % (Draw.h + Draw.boarderDist);
        int x = e.getX() - Draw.boarderDist * 3;
        
        int w = Window.draw.getWidth() - Draw.boarderDist * 4;
        int h = Draw.h;
        
        if ((e.getY() - SoundBarMain.scroll - Draw.boarderDist * 2) / (Draw.h + Draw.boarderDist) >= SoundBarMain.backgrounds.length) {
            SoundBarMain.changeFlag = true;
            Window.window.repaint();
            return;
        }

        int index = SoundBarMain.backgrounds[(e.getY() - SoundBarMain.scroll - Draw.boarderDist * 2) / (Draw.h + Draw.boarderDist)].index;
        if (x > 0 && x < w / 10 && y > h - Draw.boarderDist - h / 3 && y < h - Draw.boarderDist) {
            
            SoundBarMain.use("res " + SoundBarMain.sounds[index].name);

        } else if (x > w / 10 && x < w / 2 - w / 10 &&  y > h - Draw.boarderDist - h / 3 && y < h - Draw.boarderDist) {
            
            if (SoundBarMain.sounds[index].clip != null) {
                int frame = (int) ((float) (x - w / 10) / (float) (w / 2 - w / 5) * (float) (SoundBarMain.sounds[index].clip.getFrameLength()));
                SoundBarMain.sounds[index].clip.setFramePosition(frame);
            }

        } else if (x > w / 2 - w / 10 && x < w / 2 && y > h - Draw.boarderDist - h / 3 && y < h - Draw.boarderDist) {
            
            if (SoundBarMain.sounds[index].clip != null) {
                SoundBarMain.sounds[index].clip.setFramePosition(SoundBarMain.sounds[index].clip.getFrameLength());
            }

        } else if (x > w - (h + Draw.boarderDist) * 3 && x < w - (h + Draw.boarderDist) * 2 && y > Draw.boarderDist && y < h - Draw.boarderDist) {
            
            if (SoundBarMain.playingBackground == index) {
                SoundBarMain.use("stopfade " + SoundBarMain.sounds[index].name + " " + SoundBarMain.standartFadeOutTime);
                SoundBarMain.playingBackground = -1;
                if (SoundBarMain.fadeToBackground != -1) {
                    SoundBarMain.use("fade " + SoundBarMain.sounds[SoundBarMain.fadeToBackground].name + " 1 " + SoundBarMain.standartFadeOutTime);
                    SoundBarMain.playingBackground = SoundBarMain.fadeToBackground;
                    SoundBarMain.fadeToBackground = -1;
                }

            } else if (SoundBarMain.playingBackground == -1) {

                SoundBarMain.use("start " + SoundBarMain.sounds[index].name + " 1 " + SoundBarMain.standartFadeOutTime);
                SoundBarMain.playingBackground = index;

            } else {
                SoundBarMain.use("stopfade " + SoundBarMain.sounds[SoundBarMain.playingBackground].name + " " + SoundBarMain.standartFadeOutTime);
                SoundBarMain.use("fade " + SoundBarMain.sounds[index].name + " 1 " + SoundBarMain.standartFadeOutTime);
                SoundBarMain.playingBackground = index;
                if (SoundBarMain.playingBackground == SoundBarMain.fadeToBackground) {
                    SoundBarMain.fadeToBackground = -1;
                }
            }
        } else if (x > w - (h + Draw.boarderDist) * 2 && x < w - (h + Draw.boarderDist) && y > Draw.boarderDist && y < h - Draw.boarderDist) {
            
            if (SoundBarMain.playingBackground == index) {
                SoundBarMain.backgroundRepeat = !SoundBarMain.backgroundRepeat;
            } else if (SoundBarMain.playingBackground == -1) {
                SoundBarMain.fadeToBackground = index;
            } else {
                SoundBarMain.fadeToBackground = index;
            }

        } else if (x > w - (h + Draw.boarderDist) && x < w && y > Draw.boarderDist && y < h - Draw.boarderDist) {
            
            if (SoundBarMain.playingBackground == index) {
                if (SoundBarMain.sounds[index].clip != null) {
                    if (SoundBarMain.sounds[index].clip.isRunning()) {
                        SoundBarMain.use("stopfade " + SoundBarMain.sounds[index].name + " " + SoundBarMain.standartFadeOutTime);
                    } else {
                        SoundBarMain.use("fade " + SoundBarMain.sounds[index].name + " 1 " + SoundBarMain.standartFadeOutTime);
                    }
                }
            }
        }
        SoundBarMain.changeFlag = true;
        Window.window.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        SoundBarMain.scroll -= e.getWheelRotation() * SoundBarMain.scrollMulti;
        if (SoundBarMain.scroll > 0) {
            SoundBarMain.scroll = 0;
        }
        SoundBarMain.changeFlag = true;
        Window.window.repaint();
    }
}