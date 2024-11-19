package main;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;

public class Sound {
    private Clip clip;
    private HashMap<Integer, Clip> soundMap = new HashMap<>();
    private HashMap<Integer, Integer> pausedPositions = new HashMap<>();
    private URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/Resources/Sound/Menu/menuMusic.wav");

        soundURL[1] = getClass().getResource("/Resources/Sound/Game/musicNormalGame.wav");
        soundURL[2] = getClass().getResource("/Resources/Sound/Game/musicExtraLevel.wav");
        soundURL[3] = getClass().getResource("/Resources/Sound/Game/musicGameOver.wav");
        soundURL[4] = getClass().getResource("/Resources/Sound/Game/musicTimeOut.wav");

        soundURL[5] = getClass().getResource("/Resources/Sound/Cursor/Cursor.wav");
        soundURL[6] = getClass().getResource("/Resources/Sound/Cursor/CursorSelect.wav");

        soundURL[7] = getClass().getResource("/Resources/Sound/Effects/PlayerDeath.wav");
        soundURL[8] = getClass().getResource("/Resources/Sound/Effects/BombExplo.wav");
        soundURL[9] = getClass().getResource("/Resources/Sound/Effects/PickItem.wav");
        soundURL[10] = getClass().getResource("/Resources/Sound/Effects/soundPause.wav");
        soundURL[11] = getClass().getResource("/Resources/Sound/Effects/placeBomb.wav");

        soundURL[12] = getClass().getResource("/Resources/Sound/Game/musicStageClear.wav");

        soundURL[13] = getClass().getResource("/Resources/Sound/Effects/enemyDie.wav");
        soundURL[14] = getClass().getResource("/Resources/Sound/Effects/hitObject.wav");

        loadSounds();
    }

    private void loadSounds() {
        for (int i = 0; i < soundURL.length; i++) {
            if (soundURL[i] != null) {
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    soundMap.put(i, clip);
                    ais.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFile(int index) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }

            if (soundURL[index] == null) {
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            ais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playX(int index) {
        Clip clip = soundMap.get(index);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stopX(int index) {
        Clip clip = soundMap.get(index);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    public void loopX(int index) {
        Clip clip = soundMap.get(index);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void pauseX(int index) {
        Clip clip = soundMap.get(index);
        if (clip != null && clip.isRunning()) {
            pausedPositions.put(index, clip.getFramePosition());
            clip.stop();
        }
    }

    public void resume(int index) {
        Clip clip = soundMap.get(index);
        Integer pausedPosition = pausedPositions.get(index);
        if (clip != null && pausedPosition != null) {
            clip.setFramePosition(pausedPosition);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            pausedPositions.remove(index);
        }
    }
}
