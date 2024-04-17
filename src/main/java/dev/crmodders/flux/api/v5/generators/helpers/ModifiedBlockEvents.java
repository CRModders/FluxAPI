package dev.crmodders.flux.api.v5.generators.helpers;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import finalforeach.cosmicreach.blockevents.BlockEventTrigger;
import finalforeach.cosmicreach.blockevents.BlockEvents;

public class ModifiedBlockEvents extends BlockEvents {
        public String parent;
        public String stringId;
        public OrderedMap<String, BlockEventTrigger[]> triggers = new OrderedMap<>();
        private transient boolean initTriggers;

        public BlockEvents getParent() {
            return this.parent == null ? null : getInstance(this.parent);
        }

        @Override
        public OrderedMap<String, BlockEventTrigger[]> getTriggerMap() {
            if (!this.initTriggers) {
                BlockEvents parentEvent = this.getParent();
                if (parentEvent != null) {
                    OrderedMap<String, BlockEventTrigger[]> parentTriggers = parentEvent.getTriggerMap();
                    if (parentTriggers != null) {
                        ObjectMap.Entries var3 = parentTriggers.entries().iterator();
                        while(var3.hasNext()) {
                            ObjectMap.Entry<String, BlockEventTrigger[]> t = (ObjectMap.Entry)var3.next();
                            if (!this.triggers.containsKey(t.key)) {
                                this.triggers.put(t.key, t.value);
                            }
                        }
                    }
                }

                this.initTriggers = true;
            }

            return this.triggers;
        }

        @Override
        public BlockEventTrigger[] getTriggers(String triggerId) {
            OrderedMap<String, BlockEventTrigger[]> triggers = this.getTriggerMap();
            return triggers.get(triggerId);
        }

    }