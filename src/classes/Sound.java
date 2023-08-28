package classes;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
	
	public String name = "";
	public float vol = 0, volTarget = 0;
	public float ratio = 0f;
	public Clip clip;
	public FloatControl control;
	public int lastTimeStep = 0, index;
	public float valRatio, valOff, speRatio, stRatio;
	public int pause = 0, p = 0, targSpeed = 0, spTime = 0, stFadeTime, pauseT, delay, framesPerTick = 10, lastTick = 0;
	public boolean[] state = new boolean[6];
	public boolean loaded = false, autoRep = false, backgroundFading = false;
	public int min = -50, max = 1; //control.getMinimum()
	
	public Sound(String name, int ind) {
		this.name = name;
		this.index = ind;
		if (SoundBarMain.preload) {
			load();
		}
	}

	public void load() {
		File file = new File(SoundBarMain.srcPath + "/sounds/" + name);
		System.out.println("Sound.load: loading " + file.getAbsolutePath() + "  [I]");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			//vol = control.getMinimum();
			vol = min;
			control.setValue(vol);
			valRatio = (control.getMaximum() - control.getMinimum());
			valOff = control.getMinimum();
			loaded = true;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void setAutoRep(boolean set) {
		autoRep = set;
	}

	public void fade(float targVol, short time) {
		if (!loaded) {
			load();
		}
		if (!clip.isRunning()) {
			clip.start();
		}
		if (control.getValue() < min) {
			control.setValue(min);
		}
		if (time == 0) {
			vol = targVol;
			control.setValue(vol);
		} else {
			volTarget = targVol;
			int fadeLen = (time * SoundBarMain.FPS);
			ratio = (float) (volTarget - control.getValue()) / (float) fadeLen;
			if (volTarget > control.getValue()) {
				state[1] = true;
			} else {
				state[0] = true;
			}
		}
	}

	public void stFade(short time) {
		if (!loaded) {
			load();
		}
		if (time == 0) {
			control.setValue(control.getMinimum());
			clip.stop();
		} else {
			int fadeLen = (time * SoundBarMain.FPS);
			stRatio = (float) (control.getValue() + min) / (float) fadeLen;
			state[4] = true;
		}

	}

	public void start() {
		if (!loaded) {
			load();
		}
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		if (!loaded) {
			load();
		}
		clip.stop();
	}

	public void run() {
		if (!loaded) {
			load();
		}
		clip.start();
	}

	public void pause(short delay) {
		if (!loaded) {
			load();
		}
		if (delay != 0) {
			state[5] = true;
		}
		this.delay = delay;
		clip.stop();
	}

	public void reset() {
		if (!loaded) {
			load();
		}
		clip.setFramePosition(0);
	}

	public void pauseFade(short target, short time) {
		if (!loaded) {
			load();
		}
		if (time == 0) {
			pause = target;
		} else {
			pauseT = target;
			speRatio = (float) (pauseT - pause) / (float) (time * SoundBarMain.FPS);
			
			if (pauseT > pause) {
				state[3] = true;
			} else {
				state[2] = true;
			}
		}
	}

	public void repeat(short pause) {
		if (!loaded) {
			load();
		}
		if (!clip.isRunning()) {
			clip.start();
		}

		this.pause = pause;
		p = pause;
	}

	public void setRePause(short delay) {
		if (!loaded) {
			load();
		}
		this.pause = delay;
	}

	public void addRePause(short delay) {
		if (!loaded) {
			load();
		}
		this.pause += delay;
	}

	public void setDelay(short delay) {
		if (!loaded) {
			load();
		}
		this.delay = delay;
	}

	public void addDelay(short delay) {
		if (!loaded) {
			load();
		}
		this.delay += delay;
	}

	public void tick() {
		if (loaded) {
			framesPerTick = lastTick - clip.getFramePosition();
			lastTick = clip.getFramePosition();

			if (state[1]) {
				if (vol < volTarget) {
					vol += ratio;
					if (vol > control.getMaximum()) {
						vol = control.getMaximum();
					}
					if (vol > volTarget) {
						vol = volTarget;
					}
					control.setValue(vol);
				} else {
					state[1] = false;
				}
			} 
			if (state[0]) {
				if (vol > volTarget) {
					vol += ratio;
					if (vol < min) {
						vol = min;
					}
					if (vol < volTarget) {
						vol = volTarget;
					}
					control.setValue(vol);
				} else {
					state[0] = false;
				}
			} 
			if (state[3]) {
				if (pause < targSpeed) {
					pause += speRatio;
					if (pause > targSpeed) {
						pause = targSpeed;
					}
				} else {
					state[3] = false;
				}
			} 
			if (state[2]) {
				if (pause > targSpeed) {
					pause += speRatio;
					if (pause < targSpeed) {
						pause = targSpeed;
					}
				} else {
					state[2] = false;
				}
			} 
			if (state[4]) {
				vol += stRatio;
				if (vol < min) {
					vol = min;
					clip.stop();
					state[4] = false;
				}
				control.setValue(vol);
			} 
			if (state[5]) {
				if (delay > 0) {
					delay--;
				} else {
					clip.start();
					state[5] = false;
				}
			}
	
			if (pause > 0) {
				if (p <= 0) {
					clip.setFramePosition(0);
					clip.start();
					p = pause;
				} else {
					p--;
				}
			}
			

			if (clip.getFramePosition() >= clip.getFrameLength()) {
				if (SoundBarMain.playingBackground == index) {
					if (SoundBarMain.backgroundRepeat) {
						clip.setFramePosition(0);
						clip.start();
					} else {
						if (SoundBarMain.fadeToBackground != -1) {
							SoundBarMain.use("Sound.tick: starting " + SoundBarMain.sounds[SoundBarMain.fadeToBackground].name + " 1 5 [I]");
							SoundBarMain.playingBackground = SoundBarMain.fadeToBackground;
							SoundBarMain.fadeToBackground = -1;
						} else {
							SoundBarMain.playingBackground = -1;
						}
					}
				} else if (autoRep) {
					clip.setFramePosition(0);
					clip.start();
				}
			}
		}
	}
}