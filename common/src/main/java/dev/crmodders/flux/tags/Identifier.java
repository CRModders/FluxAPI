package dev.crmodders.flux.tags;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name);
    }

    public static Identifier fromString(String id) {
        if (!id.contains(":")) id = "base:"+id;
        String[] splitId = id.split(":");
        return new Identifier(splitId[0], splitId[1]);
    }

}
