package dev.crmodders.flux.api.resource.generation.block;

import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.JsonUtils;
import dev.crmodders.flux.api.resource.generation.block.trigger.TriggerSheet;
import org.hjson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a block action that can be performed in a {@link TriggerSheet}.
 *
 * @class BlockAction
 */
public class BlockAction {
    /**
     * Replaces a block at the specified offset from the affected block with the specified block state.
     *
     * @static
     * @param {number} x The x offset.
     * @param {number} y The y offset.
     * @param {number} z The z offset.
     * @param {Block} block The block to replace the existing block with.
     * @return BlockAction
     */

    public static BlockAction replaceBlock(int x, int y, int z, String blockStateId) {
        BlockAction action = new BlockAction("base:replace_block_state");
        action.parameters.put("xOff", x);
        action.parameters.put("yOff", y);
        action.parameters.put("zOff", z);
        action.parameters.put("blockStateId", blockStateId);
        return action;
    }

    public static BlockAction replaceBlock(int x, int y, int z, Identifier blockStateId) {
        return replaceBlock(x, y, z, blockStateId.toString());
    }

    /**
     * Creates an explosion at the specified offset from the affected block.
     *
     * @static
     * @param {number} x The x offset.
     * @param {number} y The y offset.
     * @param {number} z The z offset.
     * @param {Block} block The block to be used to replace all blocks affected by the explosion.
     * @return BlockAction
     */
    public static BlockAction explode(int x, int y, int z, String blockStateId) {
        BlockAction action = new BlockAction("base:explode");
        action.parameters.put("xOff", x);
        action.parameters.put("yOff", y);
        action.parameters.put("zOff", z);
        action.parameters.put("blockStateId", blockStateId);
        return action;
    }

    public static BlockAction explode(int x, int y, int z, Identifier blockStateId) {
        return explode(x, y, z, blockStateId.toString());
    }

    /**
     * Sets the block state parameters at the specified offset from the affected block.
     *
     * @static
     * @param {number} x The x offset.
     * @param {number} y The y offset.
     * @param {number} z The z offset.
     * @param {Object} params The block state parameters to be used.
     * @return BlockAction
     */
    public static BlockAction setBlockStateParams(int x, int y, int z, Map<String, Object> params) {
        BlockAction action = new BlockAction("base:set_block_state_params");
        action.parameters.put("xOff", x);
        action.parameters.put("yOff", y);
        action.parameters.put("zOff", z);
        action.parameters.put("params", JsonUtils.MapToJson(params));
        return action;
    }

    /**
     * Runs a trigger at the specified offset from the affected block.
     *
     * @static
     * @param {number} x The x offset.
     * @param {number} y The y offset.
     * @param {number} z The z offset.
     * @param {string} trigger The trigger to run.
     * @param {number} [tickDelay=0] The number of ticks to delay the trigger.
     * @return BlockAction
     */
    public static BlockAction runTrigger(int x, int y, int z, Identifier trigger, int tickDelay) {
        BlockAction action = new BlockAction("base:run_trigger");
        action.parameters.put("xOff", x);
        action.parameters.put("yOff", y);
        action.parameters.put("zOff", z);
        action.parameters.put("triggerId", trigger);
        action.parameters.put("tickDelay", tickDelay);
        return action;
    }

    /**
     * Plays a sound globally.
     *
     * @param {string} sound The sound to play.
     * @param {number} [volume=1] The volume of the sound.
     * @param {number} [pitch=1] The pitch of the sound.
     * @param {number} [pan=0] The pan of the sound.
     * @return BlockAction
     */
    public static BlockAction playSound2d(ResourceLocation sound, int volume, int pitch, int pan) {
        BlockAction action = new BlockAction("base:play_sound_2d");
        action.parameters.put("sound", sound);
        action.parameters.put("volume", volume);
        action.parameters.put("pitch", pitch);
        action.parameters.put("pan", pan);
        return action;
    }

    /**
     * The type of this block action in the game.
     */
    String type;

    /**
     * The parameters for this block action.
     */
    Map<String, Object> parameters;

    /**
     * Initializes a new BlockAction.
     */
    public BlockAction(String type) {
        this.type = type;
        this.parameters = new HashMap<>();
    }

    /**
     * Serializes the block action into a JSON object.
     * 
     * @return JsonObject The serialized block action as a JSON object.
     */
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.set("actionId",  this.type);
        object.set("parameters", JsonUtils.MapToJson(this.parameters));

        return object;
    }

    
    /**
     * Clones the block action into a new instance.
     *
     * @return BlockAction A new instance of the current block action.
     */
    @Override
    public BlockAction clone() {
        BlockAction action = new BlockAction(this.type);
        action.parameters = this.parameters;
        return action;
    }
}
