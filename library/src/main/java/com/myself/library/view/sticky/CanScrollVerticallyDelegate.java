package com.myself.library.view.sticky;

/**
 * Created by Dimitry Ivanov (mail@dimitryivanov.ru) on 28.03.2015.
 */
public interface CanScrollVerticallyDelegate {

    /**
     * @see android.view.View#canScrollVertically(int)
     */
    boolean canScrollVertically(int direction);
}
