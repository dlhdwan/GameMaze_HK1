import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static Clip bgClip;

    public static void playBackgroundMusic(String filePath, float volume) {
        try {
            if (bgClip == null) {
                File file = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                bgClip = AudioSystem.getClip();
                bgClip.open(audioStream);

                // Điều chỉnh âm lượng
                FloatControl gainControl = (FloatControl) bgClip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = gainControl.getMinimum() + (range * volume);
                gainControl.setValue(gain);

                bgClip.loop(Clip.LOOP_CONTINUOUSLY); // Lặp liên tục
                bgClip.start();
            } else if (!bgClip.isRunning()) {
                bgClip.start(); // Nếu âm thanh đang dừng, tiếp tục phát
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (bgClip != null && bgClip.isRunning()) {
            bgClip.stop(); // Dừng nhạc
        }
    }

    public static boolean isMusicPlaying() {
        return bgClip != null && bgClip.isRunning();
    }
    public static Clip playSound(String soundFile, float volume) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Điều chỉnh âm lượng
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = gainControl.getMinimum() + (range * volume);
            gainControl.setValue(gain);

            clip.start();
            return clip; // Trả về đối tượng Clip để quản lý trạng thái
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}
