package csdn;

import java.awt.Point;
import java.awt.geom.GeneralPath;

import twaver.Link;
import twaver.network.TNetwork;
import twaver.network.ui.LinkUI;

public class UserLinkUI extends LinkUI {

    public UserLinkUI(TNetwork network, Link link) {
        super(network, link);
    }

    @Override
    public GeneralPath getPath() {
        GeneralPath customPath = new GeneralPath();
        Point p1 = this.getFromPoint();
        Point p2 = this.getToPoint();
        customPath.moveTo(p1.x, p1.y);
        double offset = ((UserLink) this.getLink()).getOffset();
        customPath.quadTo((p1.x + p2.x) / 2, (p1.y + p2.y) / 2 + offset, p2.x, p2.y);
        return customPath;
    }
}
