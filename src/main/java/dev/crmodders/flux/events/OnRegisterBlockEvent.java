package dev.crmodders.flux.events;

import dev.crmodders.flux.block.IModBlock;
import dev.crmodders.flux.factories.IFactory;

import java.util.List;

public class OnRegisterBlockEvent {

    private final List<IFactory<IModBlock>> blockFactories;

    public OnRegisterBlockEvent(List<IFactory<IModBlock>> blockFactories) {
        this.blockFactories = blockFactories;
    }

    public void registerBlock(IFactory<IModBlock> blockFactory) {
        blockFactories.add(blockFactory);
    }

}
