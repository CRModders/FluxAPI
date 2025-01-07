package dev.crmodders.flux.api.input.client;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface MouseProcessorEx {
    boolean mouseEntered(float screenX, float screenY);

    boolean mouseExited(float screenX, float screenY);
}
