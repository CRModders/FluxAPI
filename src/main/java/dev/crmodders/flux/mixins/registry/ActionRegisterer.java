package dev.crmodders.flux.mixins.registry;

import dev.crmodders.flux.api.generators.BlockEventGenerator;
import finalforeach.cosmicreach.blockevents.BlockEvents;
import finalforeach.cosmicreach.blockevents.actions.ActionId;
import finalforeach.cosmicreach.blockevents.actions.IBlockAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEvents.class)
public class ActionRegisterer {

    @Inject(method = "registerBlockEventAction", at = @At("TAIL"))
    private static void registerAction(Class<? extends IBlockAction> actionClass, CallbackInfo ci) {
        IBlockAction instance;
        try {
            instance = actionClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BlockEventGenerator.ALL_ACTION_INSTANCES.put(actionClass.getAnnotation(ActionId.class).id(), instance);
    }

}
