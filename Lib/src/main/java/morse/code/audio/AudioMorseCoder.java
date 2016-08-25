package morse.code.audio;

import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import morse.code.Signal;

public final class AudioMorseCoder {

    private final double                               frequency;
    private final int                                  samplingFrequency;
    private final int                                  channels;
    private final int                                  sampleSizeInBytes;
    private final double                               normalisedFreq;

    private final int                                  dotTimeInSamples;

    private static final int                           MAX_VOLUME          = 100;
    private static final int                           SIGNAL_SPACING_TIME = 1;
    private static final ImmutableMap<Signal, Integer> SIGNAL_TO_TIME      = ImmutableMap.<Signal, Integer> builder()
            .put(Signal.DOT, 1)
            .put(Signal.DASH, 3)
            .put(Signal.SIGN_STOP, 2 /* shold be 3 but whet it's 2 the processing is easier */)
            .put(Signal.WORD_STOP, 7 /* shold be 8 but whet it's 7 the processing is easier */)
            .build();

    private final int                                  volume;

    public AudioMorseCoder(final int volume, final double beepFrequency, final int samplingFrequency, final int channels,
            final int sampleSizeInBytes, final double dotTimeInSeconds) {
        this.volume = volume > 100 ? 100 : volume < 0 ? 0 : volume;
        this.frequency = beepFrequency;
        this.samplingFrequency = samplingFrequency;
        this.channels = channels;
        this.sampleSizeInBytes = sampleSizeInBytes;
        this.normalisedFreq = frequency / this.samplingFrequency;
        this.dotTimeInSamples = (int) (dotTimeInSeconds * this.samplingFrequency);
    }

    public AudioMorseCoder(final int volume) {
        this(volume, 440.0, 44100, 2, 2, 0.25);
    }

    public AudioMorseCoder() {
        this(40);
    }

    public ByteBuffer getWave(final List<Signal> message) {

        final int numberOfSamples = dotTimeInSamples * computeNormalisedMessageTime(message);

        final ByteBuffer result = ByteBuffer.allocate(channels * sampleSizeInBytes * numberOfSamples);

        for (int i = 0; i < message.size(); ++i) {
            final Signal s = message.get(i);

            if (Signal.DOT == s || Signal.DASH == s) {
                result.put(constructBeep(SIGNAL_TO_TIME.get(s)).array());
                result.put(constructSilence(SIGNAL_SPACING_TIME).array());
            }
            else {
                result.put(constructSilence(SIGNAL_TO_TIME.get(s)));
            }

        }

        return result;
    }

    private ByteBuffer constructSilence(final int multiplicityOfdotSamples) {
        return ByteBuffer.allocate((multiplicityOfdotSamples * dotTimeInSamples * channels * sampleSizeInBytes));
    }

    private ByteBuffer constructBeep(final int multiplicityOfdotSamples) {
        final double samplesPerChannel = multiplicityOfdotSamples * dotTimeInSamples;
        final ByteBuffer result = ByteBuffer.allocate((int) (samplesPerChannel * channels * sampleSizeInBytes));

        for (int i = 0; i < samplesPerChannel; ++i) {
            for (int c = 0; c < channels; ++c) {
                result.putShort((short) (((Short.MAX_VALUE * volume) / MAX_VOLUME) * Math.sin(2 * Math.PI * i * normalisedFreq)));
            }
        }

        return result;
    }

    private int computeNormalisedMessageTime(final List<Signal> message) {
        int result = 0;
        if (null != message && false == message.isEmpty()) {
            for (final Signal s : message) {
                result += SIGNAL_TO_TIME.get(s) + SIGNAL_SPACING_TIME;
            }
        }
        return result;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public int getChannels() {
        return channels;
    }

    public int getSampleSizeInBytes() {
        return sampleSizeInBytes;
    }

    public double getNormalisedFreq() {
        return normalisedFreq;
    }

    public int getDotTimeInSamples() {
        return dotTimeInSamples;
    }

    public int getVolume() {
        return volume;
    }

}
