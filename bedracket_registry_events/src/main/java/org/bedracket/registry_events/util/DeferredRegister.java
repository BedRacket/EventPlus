package org.bedracket.registry_events.util;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegister<T> {

    private Registry<? super T> registry;
    private RegistryKey<Registry<? super T>> registryKey;
    private final String modId;
    private final List<RegistryObject<T>> entries;


    public DeferredRegister(String modId) {
        this.modId = modId;
        this.entries = new ArrayList<>();
        this.registryKey = null;
        this.registry = null;
    }

    public DeferredRegister<T> registry(Registry<? super T> registry) {
        this.registry = registry;
        return this;
    }

    public DeferredRegister<T> registryKey(RegistryKey<Registry<? super T>> registryKey) {
        this.registryKey = registryKey;
        return this;
    }

    public static <T> DeferredRegister<T> create(Registry<? super T> registry, String modId) {
        return new DeferredRegister<T>(modId).registry(registry);
    }

    public static <T> DeferredRegister<T> create(RegistryKey<Registry<? super T>> registryKey, String modId) {
        return new DeferredRegister<T>(modId).registryKey(registryKey);
    }

    public RegistryObject<T> register(String name, T entry) {
        if (this.getKey(entry) != null) throw new IllegalArgumentException("Entry already exists: " + entry.toString());
        RegistryObject<T> e = new RegistryObject<>(new Identifier(this.modId, name), entry);
        this.entries.add(e);
        return e;
    }

    public RegistryObject<T> register(String name, Supplier<T> entry) {
        return this.register(name, entry.get());
    }

    public void register() {
        for (RegistryObject<T> entry : entries) {
            if (this.registry != null) {
                entry.register(this.registry);
            }
        }
    }

    public Collection<RegistryObject<T>> getObjects() {
        return this.entries;
    }

    public Collection<T> getEntries() {
        List<T> entriesL = new ArrayList<>();
        for (RegistryObject<T> entry : this.entries) {
            entriesL.add(entry.get());
        }
        return entriesL;
    }

    @Nullable
    public Identifier getKey(T entry) {
        for (RegistryObject<T> object : this.entries) {
            if (object.get().equals(entry)) return object.getId();
        }
        return null;
    }

    @Nullable
    public T get(Identifier id) {
        for (RegistryObject<T> object : this.entries) {
            if (object.getId().equals(id)) return object.get();
        }
        return null;
    }

    @Nullable
    public Identifier get(T entry) {
        return this.getKey(entry);
    }
}
