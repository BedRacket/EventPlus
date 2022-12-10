/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.capabilities;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.annotation.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

/**
 * A high-speed implementation of a capability delegator.
 * This is used to wrap the results of the AttachCapabilitiesEvent.
 * It is HIGHLY recommended that you DO NOT use this approach unless
 * you MUST delegate to multiple providers instead just implement y
 * our handlers using normal if statements.
 *
 * Internally the handlers are baked into arrays for fast iteration.
 * The ResourceLocations will be used for the NBT Key when serializing.
 */
@MethodsReturnNonnullByDefault
public final class CapabilityDispatcher implements INBTSerializable<NbtCompound>, ICapabilityProvider {
    private ICapabilityProvider[] caps;
    private INBTSerializable<NbtElement>[] writers;
    private String[] names;
    private final List<Runnable> listeners;

    public CapabilityDispatcher(Map<Identifier, ICapabilityProvider> list, List<Runnable> listeners) {
        this(list, listeners, null);
    }

    @SuppressWarnings("unchecked")
    public CapabilityDispatcher(Map<Identifier, ICapabilityProvider> list, List<Runnable> listeners, @Nullable ICapabilityProvider parent) {
        List<ICapabilityProvider> lstCaps = Lists.newArrayList();
        List<INBTSerializable<NbtElement>> lstWriters = Lists.newArrayList();
        List<String> lstNames = Lists.newArrayList();
        this.listeners = listeners;

        // Parents go first
        if (parent != null) {
            lstCaps.add(parent);

            if (parent instanceof INBTSerializable) {
                lstWriters.add((INBTSerializable<NbtElement>) parent);
                lstNames.add("Parent");
            }
        }

        for (Map.Entry<Identifier, ICapabilityProvider> entry : list.entrySet()) {
            ICapabilityProvider prov = entry.getValue();
            lstCaps.add(prov);

            if (prov instanceof INBTSerializable) {
                lstWriters.add((INBTSerializable<NbtElement>) prov);
                lstNames.add(entry.getKey().toString());
            }
        }

        caps = lstCaps.toArray(new ICapabilityProvider[0]);
        writers = lstWriters.toArray(new INBTSerializable[0]);
        names = lstNames.toArray(new String[0]);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        for (ICapabilityProvider c : caps)
        {
            LazyOptional<T> ret = c.getCapability(cap, side);
            //noinspection ConstantConditions
            if (ret == null)
            {
                throw new RuntimeException(
                        String.format(
                                Locale.ENGLISH,
                                "Provider %s.getCapability() returned null; return LazyOptional.empty() instead!",
                                c.getClass().getTypeName()
                        )
                );
            }
            if (ret.isPresent())
            {
                return ret;
            }
        }
        return LazyOptional.empty();
    }

    @Override
    public NbtCompound serializeNBT()
    {
        NbtCompound nbt = new NbtCompound();
        for (int x = 0; x < writers.length; x++)
        {
            nbt.put(names[x], writers[x].serializeNBT());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NbtCompound nbt)
    {
        for (int x = 0; x < writers.length; x++)
        {
            if (nbt.contains(names[x]))
            {
                writers[x].deserializeNBT(nbt.get(names[x]));
            }
        }
    }

    public boolean areCompatible(@Nullable CapabilityDispatcher other) //Called from ItemStack to compare equality.
    {                                                        // Only compares serializeable caps.
        if (other == null) return this.writers.length == 0;  // Done this way so we can do some pre-checks before doing the costly NBT serialization and compare
        if (this.writers.length == 0) return other.writers.length == 0;
        return this.serializeNBT().equals(other.serializeNBT());
    }

    public void invalidate()
    {
        this.listeners.forEach(Runnable::run);
    }
}
