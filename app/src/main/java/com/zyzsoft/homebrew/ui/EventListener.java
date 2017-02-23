package com.zyzsoft.homebrew.ui;

import java.util.Dictionary;

/**
 * Created by ZYZOF on 12/06/2015.
 */
public interface EventListener {
    void handleEvent(HomebrewEvent e, Dictionary<String, String> properties);
}
