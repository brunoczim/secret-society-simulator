package io.github.brunoczim.secsocsim.gui;

import io.github.brunoczim.secsocsim.ui.MainUi;
import io.github.brunoczim.secsocsim.ui.UiConfig;

public class GuiConfig extends UiConfig {
    @Override
    public MainUi finish() {
        return new MainGui(this);
    }
}
