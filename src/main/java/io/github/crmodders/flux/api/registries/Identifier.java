package io.github.crmodders.flux.api.registries;

public class Identifier {

    public String namespace, name;

    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    @Override
    public String toString() {
        return namespace + ":" + name;
    }

    public static Identifier fromString(String id) {
        return new Identifier(id.split(":")[0], id.split(":")[1]);
    }
}
