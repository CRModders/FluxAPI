package dev.crmodders.flux.gui;

import finalforeach.cosmicreach.settings.IntSetting;

import java.util.Arrays;
import java.util.Comparator;

public class SteppedIntSliderElement extends IntSliderElement {

	private static int min(int[] values) {
		int min = Integer.MAX_VALUE;
        for (int j : values) {
            min = Math.min(min, j);
        }
		return min;
	}

	private static int max(int[] values) {
		int max = Integer.MIN_VALUE;
        for (int j : values) {
            max = Math.max(max, j);
        }
		return max;
	}

	private final int[] values;

	public SteppedIntSliderElement(int[] values, IntSetting setting) {
		super(min(values), max(values), setting);
		this.values = values;
	}


	@Override
	public float validate(float value) {
		int n = (int) super.validate(value);
		return Arrays.stream(values).boxed().min(Comparator.comparingInt(i -> Math.abs(i - n))).orElse((int)min);
	}

}
