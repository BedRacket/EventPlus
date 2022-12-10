package org.bedracket.eventbus.event;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class RegisteredListener {

    private final Listener listener;
    private final EventPriority priority;
    private final EventExecutor executor;
    private final boolean ignoreCancelled;
    private final Method method;

    public RegisteredListener(Listener listener, EventHandler handler, EventExecutor executor, Method method) {
        this.listener = listener;
        this.priority = handler.priority();
        this.executor = executor;
        this.ignoreCancelled = handler.ignoreCancelled();
        this.method = method;
    }


    /**
     * Gets the listener for this registration
     *
     * @return Registered Listener
     */
    @NotNull
    public Listener getListener() {
        return listener;
    }

    /**
     * Gets the priority for this registration
     *
     * @return Registered Priority
     */
    @NotNull
    public EventPriority getPriority() {
        return priority;
    }

    public Method getMethod() {
        return method;
    }

    public EventExecutor getExecutor() {
        return executor;
    }


    /**
     * Whether this listener accepts cancelled events
     *
     * @return True when ignoring cancelled events
     */
    public boolean isIgnoringCancelled() {
        return ignoreCancelled;
    }
}
