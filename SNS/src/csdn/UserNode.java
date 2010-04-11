package csdn;

import java.awt.Color;
import java.awt.Font;
import twaver.ResizableNode;
import twaver.TWaverConst;
import twaver.TWaverUtil;

public class UserNode extends ResizableNode {

    private boolean male = !(TWaverUtil.getRandomInt(5) == 0);

    public UserNode() {
        init();
    }

    private void init() {
        this.putBorderVisible(false);

        this.putCustomDraw(true);
        this.putCustomDrawFill(true);
        this.putCustomDrawGradient(false);
        this.putCustomDrawGradient(true);

        this.putLabelColor(Color.white);
        this.putLabelFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
        this.putLabelYOffset(-5);
        this.putLabelHighlightable(false);

        this.putLabelUnderlineColor(Color.white);
        if (male) {
            this.setSize(10, 10);
            this.putCustomDrawGradient(false);
            this.putCustomDrawFill3D(true);
            this.putCustomDrawOutline(false);
            this.putCustomDrawShapeFactory(TWaverConst.SHAPE_RECTANGLE);
            this.putCustomDrawFillColor(Color.green.darker());
        } else {
            this.setSize(15, 15);
            this.putCustomDrawShapeFactory(TWaverConst.SHAPE_CIRCLE);
            this.putCustomDrawOutline(false);
            this.putCustomDrawGradientFactory(TWaverConst.GRADIENT_LINE_NE);
            this.putCustomDrawGradientColor(Color.yellow.brighter());
            this.putCustomDrawFillColor(Color.orange);
        }
    }

    public String getClickURL() {
        return (String) getClientProperty("clickURL");
    }

    public void setClickURL(String url) {
        putClientProperty("clickURL", url);
    }

    public void setExplored() {
        putClientProperty("explored", true);
        if (male) {
            this.putCustomDrawFillColor(Color.lightGray);
        } else {
            this.putCustomDrawFillColor(Color.black);
            this.putCustomDrawGradientColor(Color.white);
        }
    }

    public boolean isExplored() {
        Object value = this.getClientProperty("explored");
        return value != null && (Boolean) this.getClientProperty("explored");
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        this.putLabelUnderline(selected);
        if (selected) {
            this.putLabelFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
        } else {
            this.putLabelFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
        }
    }
}
