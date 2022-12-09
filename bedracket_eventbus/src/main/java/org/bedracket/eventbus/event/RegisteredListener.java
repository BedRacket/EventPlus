package org.bedracket.eventbus.event;

import java.lang.reflect.Method;

public class RegisteredListener {
    private final Object listener;
    public final EventPriority p;
    public final boolean ignoreCancelled;
    public final Method method;

    public RegisteredListener(Object listener, EventHandler h, Method m) {
        this.listener = listener;
        this.p = h.priority();
        this.ignoreCancelled = h.ignoreCancelled();
        this.method = m;
    }

    public Object getListener() {
        return listener;
    }
}
