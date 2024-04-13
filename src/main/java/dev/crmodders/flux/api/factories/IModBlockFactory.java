package dev.crmodders.flux.api.factories;

import dev.crmodders.flux.api.block.IModBlock;
import dev.crmodders.flux.tags.Identifier;

public interface IModBlockFactory {
    IModBlock generate();
}
