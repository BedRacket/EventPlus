package org.bedracket.registry_events.util;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegistryObject<T> {

    private final Identifier id;
    private final T entry;

    public RegistryObject(Identifier id, T entry) {
        this.id = id;
        this.entry = entry;
    }

    public Identifier getId() {
        return this.id;
    }

    public T get() {
        return this.entry;
    }

    public void register(Registry<? super T> registry) {
        Registry.register(registry, this.id, this.entry);
    }

    public static <T> RegistryObject<T> of(Identifier id, T entry) {
        return new RegistryObject<>(id, entry);
    }

}
