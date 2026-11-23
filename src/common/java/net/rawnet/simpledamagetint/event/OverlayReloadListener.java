package net.rawnet.simpledamagetint.event;

import java.util.ArrayList;
import java.util.List;

public interface OverlayReloadListener {
    List<OverlayReloadListener> LISTENERS = new ArrayList<>();

    void simpleDamageTint$onOverlayReload();

    public static void register(OverlayReloadListener listener) {
        LISTENERS.add(listener);
    }

    static void unregister(OverlayReloadListener listener) {
        LISTENERS.remove(listener);
    }

    static void callEvent() {
        for (OverlayReloadListener listener : LISTENERS) {
            listener.simpleDamageTint$onOverlayReload();
        }
    }
}

