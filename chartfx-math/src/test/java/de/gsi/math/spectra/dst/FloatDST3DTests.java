package de.gsi.math.spectra.dst;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author rstein
 */
public class FloatDST3DTests {
    public static final float FFT_NUMERIC_LIMITS = 1e-3f;

    @ParameterizedTest
    @CsvSource({ "2, 2", "2, 4", "4, 2", "4, 4", "5, 5", "6, 6", "4, 8", "8, 4", "32, 32", "32, 64", "64, 32", "65536,2" })
    public void basicReal2dIdentityTests(final int nRows, final int nCols) {
        FloatDST_3D fft = new FloatDST_3D(nRows, nCols, nCols);

        final int nSamples = nRows * nCols * nCols;
        float[][][] testSignal1Ref = generateDelta(nRows, nCols, nCols);
        float[][][] testSignal1 = generateDelta(nRows, nCols, nCols);

        // basic identity tests
        fft.forward(testSignal1, true);
        fft.inverse(testSignal1, true);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                assertArrayEquals(testSignal1Ref[i][j], testSignal1[i][j], nSamples * FFT_NUMERIC_LIMITS,
                        "delta identity " + i + " x " + j);
            }
        }

    }

    @ParameterizedTest
    @CsvSource({ "2, 2", "2, 4", "4, 2", "4, 4", "5, 5", "6, 6", "4, 8", "8, 4", "32, 32", "32, 64", "64, 32", "65536,2" })
    public void basicRealIdentityTests(final int nRows, final int nCols) {
        FloatDST_3D fft = new FloatDST_3D(nRows, nCols, nCols);

        final int nSamples = nRows * nCols * nCols;
        float[] testSignal1Ref = generateDelta(nSamples);
        float[] testSignal1 = generateDelta(nSamples);
        float[] testSignal2Ref = generateRamp(nSamples, nSamples);
        float[] testSignal2 = generateRamp(nSamples, nSamples);

        // basic identity tests
        fft.forward(testSignal1, true);
        fft.inverse(testSignal1, true);
        assertArrayEquals(testSignal1Ref, testSignal1, nSamples * FFT_NUMERIC_LIMITS, "delta identity");

        fft.forward(testSignal2, true);
        fft.inverse(testSignal2, true);
        assertArrayEquals(testSignal2Ref, testSignal2, nSamples * FFT_NUMERIC_LIMITS, "ramp identity");

    }

    private static float[] generateDelta(final int nSamples) {
        final float[] retVal = new float[nSamples];
        retVal[0] = 1.0f;
        return retVal;
    }

    private static float[][][] generateDelta(final int nRows, final int nCols, final int nCols2) {
        final float[][][] retVal = new float[nRows][nCols][nCols2];
        retVal[0][0][0] = 1.0f;
        return retVal;
    }

    private static float[] generateRamp(final int nSamples, final int nRamp) {
        final float[] retVal = new float[nSamples];
        for (int i = 0; i < nRamp; i++) {
            retVal[i] = i;
        }
        return retVal;
    }

}
