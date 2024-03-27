package dev.crmodders.flux.api.toast;

public class Toast {

	private String text;

	public Toast(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}

}
