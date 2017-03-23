/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.myself.library.controller.eventbus;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * the subscriber method hunter, find all of the subscriber's methods which
 * annotated with @Subcriber.
 * 
 * @author mrsimple
 */
public class SubsciberMethodHunter {

    /**
     * the event bus's subscriber's map
     */
    Map<EventType, CopyOnWriteArrayList<EventBusSubscription>> mSubcriberMap;

    /**
     * @param subscriberMap
     */
    public SubsciberMethodHunter(Map<EventType, CopyOnWriteArrayList<EventBusSubscription>> subscriberMap) {
        mSubcriberMap = subscriberMap;
    }

    /**
     * @param subscriber
     * @return
     */
    public void findSubcribeMethods(Object subscriber) {
        if (mSubcriberMap == null) {
            throw new NullPointerException("the mSubcriberMap is null. ");
        }
        Class<?> clazz = subscriber.getClass();
        // 查找类中符合要求的注册方法,直到Object类
        while (clazz != null && !isSystemCalss(clazz.getName())) {
            final Method[] allMethods = clazz.getDeclaredMethods();
            for (int i = 0; i < allMethods.length; i++) {
                Method method = allMethods[i];
                // 根据注解来解析函数
                Subcriber annotation = method.getAnnotation(Subcriber.class);
                if (annotation != null) {
                    // 获取方法参数
                    Class<?>[] paramsTypeClass = method.getParameterTypes();
                    // just only one param
                    if (paramsTypeClass != null && paramsTypeClass.length == 1) {
                        Class<?> paramType = convertType(paramsTypeClass[0]);
                        EventType eventType = new EventType(paramType, annotation.tag());
                        TargetMethod subscribeMethod = new TargetMethod(method, paramType,
                                annotation.mode());
                        subscibe(eventType, subscribeMethod, subscriber);
                    }
                }
            } // end for

            // 获取父类,以继续查找父类中复合要求的方法
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * @param event
     * @param method
     * @param subscriber
     */
    private void subscibe(EventType event, TargetMethod method, Object subscriber) {
        CopyOnWriteArrayList<EventBusSubscription> eventBusSubscriptionLists = mSubcriberMap
                .get(event);
        if (eventBusSubscriptionLists == null) {
            eventBusSubscriptionLists = new CopyOnWriteArrayList<EventBusSubscription>();
        }

        EventBusSubscription newEventBusSubscription = new EventBusSubscription(subscriber, method);
        if (eventBusSubscriptionLists.contains(newEventBusSubscription)) {
            return;
        }

        eventBusSubscriptionLists.add(newEventBusSubscription);
        // 将事件类型key和订阅者信息存储到map中
        mSubcriberMap.put(event, eventBusSubscriptionLists);
    }

    /**
     * remove subscriber methods from map
     * 
     * @param subscriber
     */
    public void removeMethodsFromMap(Object subscriber) {
        Iterator<CopyOnWriteArrayList<EventBusSubscription>> iterator = mSubcriberMap
                .values().iterator();
        while (iterator.hasNext()) {
            CopyOnWriteArrayList<EventBusSubscription> eventBusSubscriptions = iterator.next();
            if (eventBusSubscriptions != null) {
                List<EventBusSubscription> foundEventBusSubscriptions = new LinkedList<EventBusSubscription>();
                Iterator<EventBusSubscription> subIterator = eventBusSubscriptions.iterator();
                while (subIterator.hasNext()) {
                    EventBusSubscription eventBusSubscription = subIterator.next();
                    if (eventBusSubscription.subscriber.equals(subscriber)) {
                        Log.d("", "### 移除订阅 " + subscriber.getClass().getName());
                        foundEventBusSubscriptions.add(eventBusSubscription);
                    }
                }

                // 移除该subscriber的相关的Subscription
                eventBusSubscriptions.removeAll(foundEventBusSubscriptions);
            }

            // 如果针对某个Event的订阅者数量为空了,那么需要从map中清除
            if (eventBusSubscriptions == null || eventBusSubscriptions.size() == 0) {
                iterator.remove();
            }
        }
    }

    /**
     * if the subscriber method's type is primitive, convert it to corresponding
     * Object type. for example, int to Integer.
     * 
     * @param eventType origin Event Type
     * @return
     */
    private Class<?> convertType(Class<?> eventType) {
        Class<?> returnClass = eventType;
        if (eventType.equals(boolean.class)) {
            returnClass = Boolean.class;
        } else if (eventType.equals(int.class)) {
            returnClass = Integer.class;
        } else if (eventType.equals(float.class)) {
            returnClass = Float.class;
        } else if (eventType.equals(double.class)) {
            returnClass = Double.class;
        }

        return returnClass;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }

}
