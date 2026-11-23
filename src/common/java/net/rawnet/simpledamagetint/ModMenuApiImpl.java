package net.rawnet.simpledamagetint;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.rawnet.simpledamagetint.screen.ConfigScreen;

public class ModMenuApiImpl implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }
}

