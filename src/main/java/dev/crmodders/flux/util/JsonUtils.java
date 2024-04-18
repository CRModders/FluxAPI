package dev.crmodders.flux.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import dev.crmodders.flux.api.v6.resource.generation.block.BlockAction;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    public static JsonObject MapToJson(Map<String, ?> map) {
        Type typeObject = new TypeToken<HashMap>() {}.getType();
        return JsonValue.readJSON(new Gson().toJson(map, typeObject)).asObject();
    }

    public static JsonObject ActionsMapToJson(Map<String, ArrayList<BlockAction>> map) {
        Map<String, ArrayList<String>> oMap = new HashMap<>();
        for (String key : map.keySet()) {
            ArrayList<String> strings = new ArrayList<>();
            for (BlockAction action : map.get(key)) {
                strings.add(action.serialize().toString());
            }
            oMap.put(key, strings);
        }

        return MapToJson(oMap);
    }

}
