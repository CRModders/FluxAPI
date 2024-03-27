package dev.crmodders.flux.api.toast;

import java.util.ArrayList;
import java.util.List;

public class ToastManager {
	private static List<ToastRenderable> active = new ArrayList<>();

	public static void toast(String toast) {
		toast(new Toast(toast));
	}

	public static void toast(Toast toast) {
		toast(toast, 10f);
	}

	public static void toast(Toast toast, float time) {
		active.add(new ToastRenderable(toast, time));
	}

	public static List<ToastRenderable> getActive() {
		return active;
	}

	public static void removeInactive() {
		List<ToastRenderable> remove = new ArrayList<>();
		for (ToastRenderable toast : active) {
			if (!toast.isActive()) {
				remove.add(toast);
			}
		}
		active.removeAll(remove);
	}
}
