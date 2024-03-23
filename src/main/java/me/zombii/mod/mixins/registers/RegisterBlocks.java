package me.zombii.mod.mixins.registers;

import finalforeach.cosmicreach.BlockGame;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.world.blocks.Block;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.blocks.ModBlock;
import io.github.crmodders.flux.api.generators.data.BlockDataGen;
import io.github.crmodders.flux.api.registries.BuiltInRegistries;
import io.github.crmodders.flux.api.registries.Identifier;
import me.zombii.mod.blocks.TestBlock;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGame.class)
public class RegisterBlocks {

    @Inject(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/world/blockevents/BlockEvents;initBlockEvents()V", shift = At.Shift.AFTER))
    void load(CallbackInfo ci) {
        BlockDataGen test = BlockDataGen.createGenerator(new Identifier(FluxAPI.MOD_ID, "test"));
//        test.setData("stringId", JsonValue.valueOf("fluxapi:test"));
//        test.setData("blockStates",
//                new JsonObject()
//                        .set("default", new JsonObject()
//                                .set("modelName", JsonValue.valueOf("model_c4"))
//                                .set("blockEventsId", JsonValue.valueOf("base:block_events_c4"))
//                        )
//        );

        BuiltInRegistries.MODDED_BLOCKS.register(
                new Identifier(FluxAPI.MOD_ID, "test"),
                new TestBlock(test::Generate)
        );
    }

}
