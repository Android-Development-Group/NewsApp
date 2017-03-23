/*
 * Copyright (C) 2015 Mr.Simple <bboyfeiyu@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myself.library.controller.eventbus;

import com.myself.library.controller.eventbus.handler.AsyncEventHandler;
import com.myself.library.controller.eventbus.handler.DefaultEventHandler;
import com.myself.library.controller.eventbus.handler.EventHandler;
import com.myself.library.controller.eventbus.handler.UIThreadEventHandler;
import com.myself.library.controller.eventbus.matchpolicy.DefaultMatchPolicy;
import com.myself.library.controller.eventbus.matchpolicy.MatchPolicy;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * EventBus是AndroidEventBus框架的核心类,也是用户的入口类.它存储了用户注册的订阅者信息和方法,
 * 事件类型和该事件对应的tag标识一个种类的事件{@see EventType},每一种事件对应有一个或者多个订阅者{@see EventBusSubscription}
 * ,订阅者中的订阅函数通过{@see Subcriber}注解来标识tag和线程模型,这样使得用户体检较为友好,代码也更加整洁.
 * <p>
 * 用户需要在发布事件前通过@{@see #register(Object)}方法将订阅者注册到EventBus中,EventBus会解析该订阅者中使用了
 * {@see Subcriber}标识的函数,并且将它们以{@see EventType}为key,以{@see EventBusSubscription}
 * 列表为value存储在map中. 当用户post一个事件时通过事件到map中找到对应的订阅者,然后按照订阅函数的线程模型将函数执行在对应的线程中.
 * <p>
 * 最后在不在需要订阅事件时,应该调用{@see #unregister(Object)}函数注销该对象,避免内存泄露!
 * 例如在Activity或者Fragment的onDestory函数中注销对Activity或者Fragment的订阅.
 * <p>
 * 注意 : 如果发布的事件的参数类型是订阅的事件参数的子类,订阅函数默认也会被执行。例如你在订阅函数中订阅的是List<String>类型的事件,
 * 但是在发布时发布的是ArrayList<String>的事件,
 * 因此List<String>是一个泛型抽象,而ArrayList<String>才是具体的实现
 * ,因此这种情况下订阅函数也会被执行。如果你需要订阅函数能够接收到的事件类型必须严格匹配 ,你可以构造一个EventBusConfig对象,
 * 然后设置MatchPolicy然后在使用事件总线之前使用该EventBusConfig来初始化事件总线. <code>
 *      EventBusConfig config = new EventBusConfig();
        config.setMatchPolicy(new StrictMatchPolicy());
        EventBus.getDefault().initWithConfig(config);
 * </code>
 * 
 * @author mrsimple
 */
final class EventBus {

    /**
     * default descriptor
     */
    private static final String DESCRIPTOR = EventBus.class.getSimpleName();

    /**
     * 事件总线描述符描述符
     */
    private String mDesc = DESCRIPTOR;

    /**
     * EventType-Subcriptions map
     */
    private final Map<EventType, CopyOnWriteArrayList<EventBusSubscription>> mSubcriberMap = new ConcurrentHashMap<EventType, CopyOnWriteArrayList<EventBusSubscription>>();

    /**
     * the thread local event queue, every single thread has it's own queue.
     */
    ThreadLocal<Queue<EventType>> mLocalEvents = new ThreadLocal<Queue<EventType>>() {
        protected Queue<EventType> initialValue() {
            return new ConcurrentLinkedQueue<EventType>();
        };
    };

    /**
     * the event dispatcher
     */
    EventDispatcher mDispatcher = new EventDispatcher();

    /**
     * the subscriber method hunter, find all of the subscriber's methods
     * annotated with @Subcriber
     */
    SubsciberMethodHunter mMethodHunter = new SubsciberMethodHunter(mSubcriberMap);

    /**
     * The Default EventBus instance
     */
    private static EventBus sDefaultBus;

    /**
     * private Constructor
     */
    private EventBus() {
        this(DESCRIPTOR);
    }

    /**
     * constructor with desc
     * 
     * @param desc the descriptor of eventbus
     */
    public EventBus(String desc) {
        mDesc = desc;
    }

    /**
     * @return
     */
    public static EventBus getDefault() {
        if (sDefaultBus == null) {
            synchronized (EventBus.class) {
                if (sDefaultBus == null) {
                    sDefaultBus = new EventBus();
                }
            }
        }
        return sDefaultBus;
    }

    /**
     * register a subscriber into the mSubcriberMap, the key is subscriber's
     * method's name and tag which annotated with {@see Subcriber}, the value is
     * a list of EventBusSubscription.
     * 
     * @param subscriber the target subscriber
     */
    public void register(Object subscriber) {
        if (subscriber == null) {
            return;
        }

        synchronized (this) {
            mMethodHunter.findSubcribeMethods(subscriber);
        }
    }

    /**
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        if (subscriber == null) {
            return;
        }

        synchronized (this) {
            mMethodHunter.removeMethodsFromMap(subscriber);
        }
    }

    /**
     * post a event
     * 
     * @param event
     */
    public void post(Object event) {
        post(event, EventType.DEFAULT_TAG);
    }

    /**
     * post event with tag
     * 
     * @param event 要发布的事件
     * @param tag 事件的tag, 类似于BroadcastReceiver的action
     */
    public void post(Object event, String tag) {
        mLocalEvents.get().offer(new EventType(event.getClass(), tag));
        mDispatcher.dispatchEvents(event);
    }

    /**
     * 设置订阅函数匹配策略
     * 
     * @param policy 匹配策略
     */
    public void setMatchPolicy(MatchPolicy policy) {
        mDispatcher.mMatchPolicy = policy;
    }

    /**
     * 设置执行在UI线程的事件处理器
     * 
     * @param handler
     */
    public void setUIThreadEventHandler(EventHandler handler) {
        mDispatcher.mUIThreadEventHandler = handler;
    }

    /**
     * 设置执行在post线程的事件处理器
     * 
     * @param handler
     */
    public void setPostThreadHandler(EventHandler handler) {
        mDispatcher.mPostThreadHandler = handler;
    }

    /**
     * 设置执行在异步线程的事件处理器
     * 
     * @param handler
     */
    public void setAsyncEventHandler(EventHandler handler) {
        mDispatcher.mAsyncEventHandler = handler;
    }

    /**
     * 返回订阅map
     * 
     * @return
     */
    public Map<EventType, CopyOnWriteArrayList<EventBusSubscription>> getSubscriberMap() {
        return mSubcriberMap;
    }

    /**
     * 获取等待处理的事件队列
     * 
     * @return
     */
    public Queue<EventType> getEventQueue() {
        return mLocalEvents.get();
    }

    /**
     * clear the events and subcribers map
     */
    public synchronized void clear() {
        mLocalEvents.get().clear();
        mSubcriberMap.clear();
    }

    /**
     * get the descriptor of EventBus
     * 
     * @return the descriptor of EventBus
     */
    public String getDescriptor() {
        return mDesc;
    }

    /**
     * 事件分发器
     * 
     * @author mrsimple
     */
    private class EventDispatcher {

        /**
         * 将接收方法执行在UI线程
         */
        EventHandler mUIThreadEventHandler = new UIThreadEventHandler();

        /**
         * 哪个线程执行的post,接收方法就执行在哪个线程
         */
        EventHandler mPostThreadHandler = new DefaultEventHandler();

        /**
         * 异步线程中执行订阅方法
         */
        EventHandler mAsyncEventHandler = new AsyncEventHandler();

        /**
         * 缓存一个事件类型对应的可EventType列表
         */
        private Map<EventType, List<EventType>> mCacheEventTypes = new ConcurrentHashMap<EventType, List<EventType>>();
        /**
         * 事件匹配策略,根据策略来查找对应的EventType集合
         */
        MatchPolicy mMatchPolicy = new DefaultMatchPolicy();

        /**
         * @param aEvent
         */
        void dispatchEvents(Object aEvent) {
            Queue<EventType> eventsQueue = mLocalEvents.get();
            while (eventsQueue.size() > 0) {
                deliveryEvent(eventsQueue.poll(), aEvent);
            }
        }

        /**
         * 根据aEvent查找到所有匹配的集合,然后处理事件
         * 
         * @param type
         * @param aEvent
         */
        private void deliveryEvent(EventType type, Object aEvent) {
            Class<?> eventClass = aEvent.getClass();
            List<EventType> eventTypes = null;
            // 如果有缓存则直接从缓存中取
            if (mCacheEventTypes.containsKey(eventClass)) {
                eventTypes = mCacheEventTypes.get(type);
            } else {
                eventTypes = mMatchPolicy.findMatchEventTypes(type, aEvent);
                mCacheEventTypes.put(type, eventTypes);
            }

            for (EventType eventType : eventTypes) {
                handleEvent(eventType, aEvent);
            }
        }

        /**
         * 处理单个事件
         * 
         * @param eventType
         * @param aEvent
         */
        private void handleEvent(EventType eventType, Object aEvent) {
            List<EventBusSubscription> eventBusSubscriptions = mSubcriberMap.get(eventType);
            if (eventBusSubscriptions == null) {
                return;
            }

            for (EventBusSubscription eventBusSubscription : eventBusSubscriptions) {
                final ThreadMode mode = eventBusSubscription.threadMode;
                EventHandler eventHandler = getEventHandler(mode);
                // 处理事件
                eventHandler.handleEvent(eventBusSubscription, aEvent);
            }
        }

        private EventHandler getEventHandler(ThreadMode mode) {
            if (mode == ThreadMode.ASYNC) {
                return mAsyncEventHandler;
            }

            if (mode == ThreadMode.POST) {
                return mPostThreadHandler;
            }

            return mUIThreadEventHandler;
        }
    } // end of EventDispatcher

}
