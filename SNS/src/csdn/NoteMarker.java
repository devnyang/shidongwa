package csdn;

import java.awt.*;
import twaver.network.*;

public class NoteMarker implements CanvasMarker {

    private Color backgroundColor = new Color(0, 200, 200, 50);
    private Font font = new Font("微软雅黑", Font.BOLD, 12);

    public void mark(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fill3DRect(50, 50, 330, 150, true);
        g.setFont(font);
        g.setColor(Color.white);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int space = 20;
        int x = 55;
        int y = 65;
        g.drawString("1、双击彩色节点【绿色或黄色】进行关系展开", x, y);
        y += space;
        g.drawString("2、灰色节点为已探索用户", x, y);
        y += space;
        g.drawString("3、绿色节点为Boy，黄色节点为Girl", x, y);
        y += space;
        g.drawString("4、鼠标停留可以tooltip此人的照片和BLOG网址", x, y);
        y += space;
        g.drawString("5、选择“环形布局”或“弹簧布局”进行布局算法切换", x, y);
        y += space;
        g.drawString("6、点击“随机展开几个”按钮随机对几个节点自动展开", x, y);
        y += space;
        g.drawString("7、文本框输入其他CSDN用户名并点击按钮“重新开始”", x, y);
    }
}
