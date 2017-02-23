package com.zyzsoft.homebrew.ui;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by ZYZOF on 12/06/2015.
 */
public class EventDispatcher {
    private static EventDispatcher m_instance;

    private List<EventListener> m_listeners;

    public static EventDispatcher getInstance() {
        if (m_instance == null) {
            m_instance = new EventDispatcher();
        }
        return m_instance;
    }

    private EventDispatcher() {
        m_listeners = new ArrayList<EventListener>();
    }

    public void dispatchEvent(HomebrewEvent e, Dictionary<String, String> properties) {
        for (EventListener listener : m_listeners) {
            listener.handleEvent(e, properties);
        }
    }

    public void addListener(EventListener listener) {
        m_listeners.add(listener);
    }
}
