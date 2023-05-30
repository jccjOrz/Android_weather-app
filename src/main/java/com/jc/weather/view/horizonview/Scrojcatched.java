package com.jc.weather.view.horizonview;

/**
 * 定义滑动监听接口
 * @author jc
 */
public interface Scrojcatched {
    void addWatcher(Scrojcatcher watcher);
    void removeWatcher(Scrojcatcher watcher);
    void notifyWatcher(int x);
}
