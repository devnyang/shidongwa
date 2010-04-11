package csdn;

import javax.swing.SwingUtilities;
import twaver.TWaverConst;
import twaver.network.TNetwork;

public class Util {

    public static final String CSDN_URL_PREFIX = "http://hi.csdn.net/";

    public static void layoutNetwork(final TNetwork network) {
        if (SwingUtilities.isEventDispatchThread()) {
            doNetworkLayout(network);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    doNetworkLayout(network);
                }
            });
        }
    }

    private static void doNetworkLayout(final TNetwork network) {
        network.getSelectionModel().clearSelection();
        network.doLayout(TWaverConst.LAYOUT_CIRCULAR, true, new Runnable() {

            public void run() {
                network.layoutToOverview();
            }
        });
    }
}
