package com.zyzsoft.homebrew.recipe;

import com.zyzsoft.homebrew.ui.EventDispatcher;
import com.zyzsoft.homebrew.ui.EventListener;
import com.zyzsoft.homebrew.ui.HomebrewEvent;

import java.util.Dictionary;

/**
 * Created by ZYZOF on 12/06/2015.
 *
 * Class for handling recipe changes as a result of ui update events
 */
public class RecipeManager implements EventListener {
    private static RecipeManager m_instance;

    public static RecipeManager getInstance() {
        if (m_instance == null) {
            m_instance = new RecipeManager();
        }
        return m_instance;
    }

    private RecipeManager() {
        EventDispatcher.getInstance().addListener((EventListener)this);
    }

    public void handleEvent(HomebrewEvent e, Dictionary<String, String> properties) {

    }
}
