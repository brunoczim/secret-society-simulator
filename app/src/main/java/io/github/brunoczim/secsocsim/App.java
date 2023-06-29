package io.github.brunoczim.secsocsim;

import io.github.brunoczim.secsocsim.p2p.PeerFactory;
import io.github.brunoczim.secsocsim.ui.UiConfig;

public class App implements Runnable {
    private UiConfig uiConfig;
    private PeerFactory peerFactory;

    public App(UiConfig uiConfig, PeerFactory peerFactory) {
        this.uiConfig = uiConfig;
        this.peerFactory = peerFactory;
    }

    @Override
    public void run() {

    }
}
