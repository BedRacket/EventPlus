package org.bedracket.eventbus.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BedRacket {

    public static HashMap<Class<?>, List<RegisteredListener>> eventHandlers = new HashMap<>();

    public static BedRacket EVENT_BUS = new BedRacket();

    public static boolean register(Class<?> c, Method e, Object lis) {
        EventHandler hand = e.getAnnotation(EventHandler.class);

        List<RegisteredListener> list = eventHandlers.getOrDefault(c, new ArrayList<>());
        RegisteredListener l = new RegisteredListener(lis, hand, e);
        eventHandlers.put(c, list);

        return list.add(l);
    }

    public int registerAll(Object lis) {
        int registered = 0;
        for (Method m : lis.getClass().getMethods()) {

            Class<?>[] pt = m.getParameterTypes();
            if (pt.length <= 0) continue;

            Class<?> clazz = pt[0];
            Class<?> clazzo = pt[0];

            while (null != clazz.getSuperclass()) {
                if (clazz.equals(Event.class)) break;

                clazz = clazz.getSuperclass();
            }

            if (clazz.equals(Event.class)) { // first argument of method is subclass of Event
                if (register(clazzo, m, lis)) registered++;
            }
        }
        return registered;
    }

    public Event post(Class<? extends Event> type, Event ev) {
        post(eventHandlers.getOrDefault(type, new ArrayList<>()), ev);
        return ev;
    }

    public void post(List<RegisteredListener> ls, Event ev) {
        if (ev.isAsynchronous()) Multithreading.runAsync(() -> post0(ls,ev)); else post0(ls,ev);
    }

    public void post0(List<RegisteredListener> ls, Event ev) {
        for (RegisteredListener listener : ls) {
            Method m = listener.method;
            try {
                if (ev instanceof Blockable && ((Blockable) ev).isBlocked()) return;
                if (ev instanceof Cancellable)
                    if (((Cancellable)ev).isCancelled() && !listener.ignoreCancelled) return;

                m.invoke(listener.getListener(), ev);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void unPostEvent(Event event) {
        eventHandlers.remove(event.getClass());
    }
}
