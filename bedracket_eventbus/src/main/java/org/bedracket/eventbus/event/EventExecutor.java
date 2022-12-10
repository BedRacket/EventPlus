package org.bedracket.eventbus.event;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

/**
 * Interface which defines the class for event call backs to plugins
 */
public interface EventExecutor {
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException, InvocationTargetException, IllegalAccessException;
}
