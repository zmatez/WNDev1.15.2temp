package com.matez.wildnature.world.gen.noise;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.matez.wildnature.world.gen.noise.another.Noise;

/**
 * Class that helps with various octaves and other noise stuff
 */
public class OctaveNoiseSampler<T extends Noise> {
	private Noise[] samplers;
	private double clamp;
	private double frequency, amplitudeLow, amplitudeHigh;

	private Constructor<T> getNoiseConstructor(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor(long.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	private T create(Constructor<T> constructor, long seed) {
		if (constructor == null) {
			return null;
		}

		try {
			return constructor.newInstance(seed);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public OctaveNoiseSampler(Class<T> classT, Random rand, int octaves, double frequency, double amplitudeHigh, double amplitudeLow) {
		samplers = new Noise[octaves];
		clamp = 1D / (1D - (1D / Math.pow(2, octaves)));

		Constructor<T> constructor = this.getNoiseConstructor(classT);

		for (int i = 0; i < octaves; ++i) {
			samplers[i] = create(constructor, rand.nextLong());
		}

		this.frequency = frequency;
		this.amplitudeLow = amplitudeLow;
		this.amplitudeHigh = amplitudeHigh;
	}

	public double sample(double x, double y) {
		double amplFreq = 0.5D;
		double result = 0;
		for (Noise sampler : samplers) {
			result += (amplFreq * sampler.sample(x / (amplFreq * frequency), y / (amplFreq * frequency)));

			amplFreq *= 0.5D;
		}

		result = result * clamp;
		return result > 0 ? result * amplitudeHigh : result * amplitudeLow;
	}

	public double sample(double x, double y, double z) {
		double amplFreq = 0.5D;
		double result = 0;
		for (Noise sampler : samplers) {
			double freq = amplFreq * frequency;
			result += (amplFreq * sampler.sample(x / freq, y / freq, z / freq));

			amplFreq *= 0.5D;
		}

		result = result * clamp;
		return result > 0 ? result * amplitudeHigh : result * amplitudeLow;
	}

	public double sampleCustom(double x, double y, double freqModifier, double amplitudeHMod, double amplitudeLMod, int octaves) {
		double amplFreq = 0.5D;
		double result = 0;

		double sampleFreq = frequency * freqModifier;

		for (int i = 0; i < octaves; ++i) {
			Noise sampler = samplers[i];

			double freq = amplFreq * sampleFreq;
			result += (amplFreq * sampler.sample(x / freq, y / freq));

			amplFreq *= 0.5D;
		}

		double sampleClamp = 1D / (1D - (1D / Math.pow(2, octaves)));
		result *= sampleClamp;
		return result > 0 ? result * amplitudeHigh * amplitudeHMod : result * amplitudeLow * amplitudeLMod;
	}

	public double sampleCustom(double x, double y, double z, double freqModifier, double amplitudeHMod, double amplitudeLMod, int octaves) {
		double amplFreq = 0.5D;
		double result = 0;

		double sampleFreq = frequency * freqModifier;

		for (int i = 0; i < octaves; ++i) {
			Noise sampler = samplers[i];

			double freq = amplFreq * sampleFreq;
			result += (amplFreq * sampler.sample(x / freq, y / freq, z / freq));

			amplFreq *= 0.5D;
		}

		double sampleClamp = 1D / (1D - (1D / Math.pow(2, octaves)));
		result = result * sampleClamp;
		return result > 0 ? result * amplitudeHigh * amplitudeHMod : result * amplitudeLow * amplitudeLMod;
	}
}