package dev.crmodders.flux.api.resource.generation.block.trigger;

import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.api.resource.generation.Writeable;
import dev.crmodders.flux.api.resource.generation.block.BlockAction;
import dev.crmodders.flux.tags.Identifier;
import dev.crmodders.flux.util.JsonUtils;
import org.hjson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Defines a collection of triggers and actions that can be executed by a block.
 *
 * @class TriggerSheet
 * @extends {Writeable}
 */
public class TriggerSheet extends Writeable {
    static TriggerSheet BASE_TRIGGER_SHEET;

    /**
     * The parent TriggerSheet for this trigger sheet.
     */
    TriggerSheet parent;

    /**
     * The unique id for this trigger sheet.
     */
    Identifier id;

    /**
     * The triggers and actions for this trigger sheet.
     */
    Map<String, ArrayList<BlockAction>> triggers;

    /**
     * Initializes a new trigger sheet.
     *
     * @param {string} id The unique id for this trigger sheet.
     */
    public TriggerSheet(Identifier id) {
        super();
        this.id = id;
        this.triggers = new HashMap<>();
    }

    /**
     * Initializes a new trigger sheet.
     *
     * @param {string} id The unique id for this trigger sheet.
     * @param {parent} [parent=null] The parent TriggerSheet for this trigger sheet.
     */
    public TriggerSheet(Identifier id, TriggerSheet parent) {
        super();
        this.parent = parent;
        this.id = id;
        this.triggers = new HashMap<>();

        if (parent.triggers != null) this.append(this.parent);
    }

    /**
     * Adds a new action to the trigger sheet.
     *
     * If the trigger does not exist, it will be created.
     *
     * @param {string} triggers A trigger or a list of triggers to add the action to.
     * @param {BlockAction} action The action to add.
     * @param {number?} index The index to insert the action at.
     */
    public void addAction(ArrayList<String> triggers, BlockAction action, int index) {
        for(String trigger : triggers) {
            if (!this.triggers.containsKey(trigger))
                this.triggers.put(trigger, new ArrayList<>());

            if(index < 0) index += this.triggers.get(trigger).size();
            this.triggers.get(trigger).add(index, action);
        }
    }

    public void addAction(String trigger, BlockAction action, int index) {
        addAction(new ArrayList<>(List.of(new String[]{trigger})), action, index);
    }

    public void addAction(String trigger, BlockAction action) {
        addAction(new ArrayList<>(List.of(new String[]{trigger})), action, 0);
    }

    /**
     * Adds multiple actions to multiple triggers within the trigger sheet.
     *
     * For each trigger on triggers, the corresponding actions in actions will be added.
     *
     * @param {Array<string>} triggers A trigger or a list of triggers to add the actions to.
     * @param {BlockAction} actions The actions to add.
     * @param {number?} startingIndex The index to sequentially insert the actions at.
     */
    public void addActions(ArrayList<String> triggers, List<BlockAction> actions, int startingIndex) {
        if(startingIndex < 0) startingIndex += this.triggers.get(triggers.get(0)).size();
        int i = 0;
        for(BlockAction action : actions) this.addAction(triggers, action, startingIndex + i++);
    }

    public void addActions(String trigger, List<BlockAction> actions, int startingIndex) {
        addActions(new ArrayList<>(List.of(new String[]{trigger})), actions, startingIndex);
    }

    public void addActions(String trigger, List<BlockAction> actions) {
        addActions(new ArrayList<>(List.of(new String[]{trigger})), actions, 0);
    }

    /**
     * Removes a trigger from the trigger sheet.
     *
     * @param {string} trigger
     */
    public void removeTrigger(String trigger) {
        this.triggers.remove(trigger);
    }

    /**
     * Removes an action from all triggers within the trigger sheet.
     *
     * @param {BlockActionComparator} comparator The function to use to select actions.
     */
    public void removeActionByComparison(Function<BlockAction, Boolean> comparator) {
        for(String triggerId : this.triggers.keySet()) {
            this.removeTriggerActionByComparison(triggerId, comparator);
        }
    }

    /**
     * Removes an action from a specific trigger within the trigger sheet.
     *
     * @param {string} trigger
     * @param {BlockActionComparator} comparator The function to use to select actions.
     */
    public void removeTriggerActionByComparison(String trigger, Function<BlockAction, Boolean> comparator) {
        ArrayList<BlockAction> actions = this.triggers.get(trigger);
        List<BlockAction> deletions = new ArrayList();

        for(BlockAction action : actions) {
            if(comparator.apply(action)) deletions.add(action);
        }
        for(BlockAction deletion : deletions) {
            actions.remove(actions.indexOf(deletion));
        }

        this.triggers.put(trigger, actions);
    }

    /**
     * Serializes the trigger sheet into a JSON object.
     *
     * @return {Object} The serialized trigger sheet as a JSON object.
     */
    public JsonObject serialize() {
        TriggerSheet output = new TriggerSheet(this.id, BASE_TRIGGER_SHEET);
        output.append(this);

        JsonObject object = new JsonObject();
        if (this.parent == null) object.set("parent", "base:block_events_default");
        else object.set("parent",  output.serialize());
        object.set("stringId",  output.id.toString());
        object.set("triggers", JsonUtils.ActionsMapToJson(this.triggers));

        return object;
    }

    /**
     * Clones the trigger sheet into a new instance.
     *
     * @param {string} [id=this.id]
     * @return {TriggerSheet} A new instance of the current trigger sheet.
     */
    public TriggerSheet clone(Identifier id) {
        TriggerSheet sheet = new TriggerSheet(id, this.parent);
        for(String triggerId : this.triggers.keySet()) {
            ArrayList<BlockAction> triggers = this.triggers.get(triggerId);
            for(BlockAction trigger : triggers) {
                sheet.addAction(triggerId, trigger.clone());
            }
        }
        return sheet;
    }

    /**
     * Appends another trigger sheet to this trigger sheet.
     *
     * @param {TriggerSheet} other The trigger sheet to append.
     * @return {this} The current trigger sheet.
     */
    public TriggerSheet append(TriggerSheet other) {
        for(String triggerId : other.triggers.keySet()) {
            for(BlockAction trigger : other.triggers.get(triggerId)) {
                this.addAction(triggerId, trigger.clone());
            }
        }
        return this;
    }

    static {
        TriggerSheet sheet = new TriggerSheet(new Identifier("base","block_events_default"));
        sheet.addAction("onPlace", BlockAction.replaceBlock(0, 0, 0, "self"));
        sheet.addAction("onPlace", BlockAction.playSound2d(new ResourceLocation("base", "block-place.ogg"), 1, 1, 0));
        sheet.addAction("onBreak", BlockAction.replaceBlock(0, 0, 0, new Identifier("base", "air[default]")));
        sheet.addAction("onBreak", BlockAction.playSound2d(new ResourceLocation("base", "block-break.ogg"), 1, 1, 0));
        sheet.written = true;
        BASE_TRIGGER_SHEET = sheet;
    }
}
