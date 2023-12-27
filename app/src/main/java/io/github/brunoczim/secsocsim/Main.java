package io.github.brunoczim.secsocsim;

import io.github.brunoczim.secsocsim.gui.GuiConfig;
import io.github.brunoczim.secsocsim.ui.MainUi;

public class Main {
    public static void main(String[] args) {
        MainUi ui = new GuiConfig()
            .setConnectionOpeningHandler((address, societyUi) -> {})
            .finish();
        ui.start();
    }
}
