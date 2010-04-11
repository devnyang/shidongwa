package csdn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import twaver.Element;
import twaver.Generator;
import twaver.TDataBox;
import twaver.TWaverUtil;
import twaver.network.TNetwork;
import twaver.network.background.TextureBackground;

public class MainUI extends JFrame {

    private TDataBox box = new TDataBox();
    private TNetwork network = new TNetwork(box);
    private JRadioButton btnCircleLayout = null;
    private JRadioButton btnSpringLayout = null;

    public MainUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(network);
        this.setSize(900, 600);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("CSDN用户关系探险记");
        TWaverUtil.centerWindow(this);

        initToolbar();
        initTWaver();
        initGraphLayout();
    }

    private void initToolbar() {
        btnCircleLayout = new JRadioButton("环形布局", false);
        btnSpringLayout = new JRadioButton("弹簧布局", true);
        btnCircleLayout.setOpaque(false);
        btnSpringLayout.setOpaque(false);

        network.getToolbar().removeAll();

        //CSDN user name input text field.
        network.getToolbar().add(new JLabel("CSDN用户名：    "));
        final JTextField txtName = new JTextField("solo");
        network.getToolbar().add(txtName);

        //explore button. press to explore
        final JButton btnExplore = new JButton("重新开始");
        btnExplore.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                box.clear();
                explore(null, Util.CSDN_URL_PREFIX + txtName.getText());
            }
        });
        network.getToolbar().add(btnExplore);

        //add this listener so press "enter" in text field will trigger explore button.
        txtName.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                btnExplore.doClick();
            }
        });

        //when this window just shows up, explore immediately
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                btnExplore.doClick();
            }
        });

        //explore randomly button.
        JButton btnExploreAll = new JButton("随机展开几个");
        btnExploreAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                List allElements = box.getAllElements();
                for (int i = 0; i < TWaverUtil.getRandomInt(30); i++) {
                    Object o = allElements.get(TWaverUtil.getRandomInt(allElements.size()));
                    if (o instanceof UserNode) {
                        UserNode node = (UserNode) o;
                        explore(node);
                    }
                }
            }
        });
        network.getToolbar().add(btnExploreAll);

        //circle layout button.
        btnCircleLayout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Util.layoutNetwork(network);
            }
        });
        network.getToolbar().add(btnCircleLayout);

        //spring layout button.
        btnSpringLayout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                network.getSpringLayouter().start();
            }
        });
        network.getToolbar().add(btnSpringLayout);
        ButtonGroup layoutButtonGroup = new ButtonGroup();
        layoutButtonGroup.add(btnCircleLayout);
        layoutButtonGroup.add(btnSpringLayout);

        //check box, hide or show node name label.
        final JCheckBox cbShowLabel = new JCheckBox("显示名字", true);
        cbShowLabel.setOpaque(false);
        network.setElementLabelGenerator(new Generator() {

            public Object generate(Object o) {
                if (cbShowLabel.isSelected()) {
                    return ((Element) o).getName();
                } else {
                    return null;
                }
            }
        });
        cbShowLabel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                network.getCanvas().repaint();
            }
        });
        network.getToolbar().add(cbShowLabel);

        //copyright information.
        String tip = "源代码及Copyright 归大伙所有 随便复制 绝不追究";
        network.getToolbar().add(new JLabel(tip));
    }

    private void initTWaver() {
        //display notes on network canvas with a marker.
        network.addCanvasMarker(new NoteMarker());

        //add element listener, drill down data on double click
        network.addElementDoubleClickedActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source instanceof UserNode) {
                    UserNode node = (UserNode) source;
                    explore(node);
                }
            }
        });

        //display texture background on network canvas.
        network.setNetworkBackground(new TextureBackground("/csdn/texture.jpg"));

        //move mouse, highlight link.
        network.getCanvas().addMouseMotionListener(new MouseMotionAdapter() {

            private Color oldColor = null;
            private UserLink lastLink = null;

            @Override
            public void mouseMoved(MouseEvent e) {
                //clear old
                if (lastLink != null) {
                    lastLink.putLinkColor(oldColor);
                    lastLink.setName(null);
                }

                //set new.
                Point p = e.getPoint();
                Element element = network.getElementPhysicalAt(p);
                if (element instanceof UserLink) {
                    UserLink link = (UserLink) element;
                    oldColor = link.getLinkColor();
                    link.putLinkColor(Color.white);
                    link.setName(link.getToolTipText());
                    lastLink = link;
                }
            }
        });

        //add listener when mouse released, clear selection.
        network.getCanvas().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                network.getSelectionModel().clearSelection();
            }
        });
    }

    private void initGraphLayout() {
        //setup TWaver auto-layout algorithm parameters.
        network.getSpringLayouter().setForceSize(3);
        network.getSpringLayouter().setStepSize(40);
        network.getSpringLayouter().setNodeRepulsionFactor(1);
        network.getSpringLayouter().setLinkRepulsionFactor(30);
        network.getSpringLayouter().start();
        network.getCanvasScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        network.getCanvasScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //when window resized, reset the spring layout limit bounds to canvas view port size.
        network.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                network.getSpringLayouter().setLimitBounds(network.getCanvasScrollPane().getBounds());
            }
        });
    }

    private void explore(UserNode parent) {
        //explore this user's friends from CSDN website.
        String clickURL = parent.getClickURL();
        if (clickURL != null) {
            clickURL = Util.CSDN_URL_PREFIX + clickURL;
            explore(parent, clickURL);
        }
    }

    private void explore(UserNode parent, String clickURL) {
        if (clickURL != null) {
            Object flag = null;
            if (parent != null) {
                flag = parent.isExplored();
            }
            if (flag == null || !(Boolean) flag) {
                boolean circleLayout = btnCircleLayout.isSelected();
                PageExplorer explorer = new PageExplorer(network, parent, clickURL, circleLayout);
                Thread thread = new Thread(explorer);
                thread.start();
                box.getSelectionModel().clearSelection();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //setup swing fonts before start program.
                Font font = new Font("微软雅黑", Font.PLAIN, 12);
                UIManager.put("Label.font", font);
                UIManager.put("Button.font", font);
                UIManager.put("RadioButton.font", font);
                UIManager.put("CheckBox.font", font);
                UIManager.put("TextField.font", font);

                //show main ui.
                MainUI ui = new MainUI();
                ui.setVisible(true);
            }
        });
    }
}
