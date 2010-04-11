package csdn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Point2D;
import twaver.Link;

public class UserLink extends Link {

    public UserLink(UserNode from, UserNode to) {
        super(from, to);
        init();
    }

    private void init() {
        this.putLinkWidth(1);
        this.putLinkOutlineWidth(0);
        this.putLinkAntialias(true);
        this.putLinkColor(Color.lightGray);
        this.putBorderVisible(false);
        this.setToolTipText("∫√”—");
        this.putLinkLabelRotatable(true);
        this.putLabelYOffset(-10);
        this.putLabelFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 12));
        this.putLabelColor(Color.white);
        this.putBorderVisible(false);
        this.putLabelHighlightable(false);
    }

    @Override
    public String getUIClassID() {
        return UserLinkUI.class.getName();
    }

    public double getOffset() {
        Point p1 = this.getFromAgent().getCenterLocation();
        Point p2 = this.getToAgent().getCenterLocation();
        double distance = Point2D.distance(p1.x, p1.y, p2.x, p2.y);
        return distance / 10d;
    }
}
