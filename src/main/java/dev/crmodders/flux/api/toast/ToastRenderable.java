package dev.crmodders.flux.api.toast;

public class ToastRenderable {
	private Toast toast;
	public float toastTimer;

	public ToastRenderable(Toast toast, float toastTimer) {
		super();
		this.toast = toast;
		this.toastTimer = toastTimer;
	}

	public boolean isActive() {
		return toastTimer > 0f;
	}

	public Toast getToast() {
		return toast;
	}

}
