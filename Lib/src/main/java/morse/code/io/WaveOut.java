package morse.code.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class WaveOut {

    private final float samplingFrequency;
    private final int   sampleSizeInBytes;
    private final int   channels;

    public WaveOut(final float

    samplingFrequency, final int sampleSizeInBytes, final int channels) {
        this.samplingFrequency = samplingFrequency;
        this.sampleSizeInBytes = sampleSizeInBytes;
        this.channels = channels;
    }

    public void save(final byte[] output, final String fileName) {
        final InputStream b_in = new ByteArrayInputStream(output);
        final AudioFormat format = new AudioFormat(samplingFrequency, 8 * sampleSizeInBytes, channels, true, true);
        final AudioInputStream stream = new AudioInputStream(b_in, format, output.length);

        final File file = new File(fileName);
        try {
            AudioSystem.write(stream, Type.WAVE, file);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
