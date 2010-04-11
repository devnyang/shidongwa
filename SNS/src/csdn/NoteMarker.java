package csdn;

import java.awt.*;
import twaver.network.*;

public class NoteMarker implements CanvasMarker {

    private Color backgroundColor = new Color(0, 200, 200, 50);
    private Font font = new Font("΢���ź�", Font.BOLD, 12);

    public void mark(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fill3DRect(50, 50, 330, 150, true);
        g.setFont(font);
        g.setColor(Color.white);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int space = 20;
        int x = 55;
        int y = 65;
        g.drawString("1��˫����ɫ�ڵ㡾��ɫ���ɫ�����й�ϵչ��", x, y);
        y += space;
        g.drawString("2����ɫ�ڵ�Ϊ��̽���û�", x, y);
        y += space;
        g.drawString("3����ɫ�ڵ�ΪBoy����ɫ�ڵ�ΪGirl", x, y);
        y += space;
        g.drawString("4�����ͣ������tooltip���˵���Ƭ��BLOG��ַ", x, y);
        y += space;
        g.drawString("5��ѡ�񡰻��β��֡��򡰵��ɲ��֡����в����㷨�л�", x, y);
        y += space;
        g.drawString("6����������չ����������ť����Լ����ڵ��Զ�չ��", x, y);
        y += space;
        g.drawString("7���ı�����������CSDN�û����������ť�����¿�ʼ��", x, y);
    }
}
