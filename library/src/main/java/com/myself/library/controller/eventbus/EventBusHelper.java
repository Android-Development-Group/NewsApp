package com.myself.library.controller.eventbus;

/**
 * 事件总线帮助
 * Created by guchenkai on 2015/11/16.
 */
public final class EventBusHelper {
    private static EventBus eventBus = EventBus.getDefault();

    public static void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public static void post(Object target, String tag) {
        eventBus.post(target, tag);
    }

}
