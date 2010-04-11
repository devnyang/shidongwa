package csdn;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import twaver.TWaverUtil;
import twaver.network.TNetwork;

public class PageExplorer implements Runnable {

    private TNetwork network = null;
    private String urlString = null;
    private UserNode parent = null;
    private boolean circleLayout = true;

    public PageExplorer(TNetwork network, UserNode parent, String urlString, boolean circleLayout) {
        this.network = network;
        this.urlString = urlString;
        this.parent = parent;
        this.circleLayout = circleLayout;
    }

    public void run() {
        try {
            if (parent == null) {
                UserNode centerNode = createCenterNode();
                addChildrenNode(centerNode);
                centerNode.setExplored();
            } else {
                addChildrenNode(parent);
                parent.setExplored();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(network, "无法获得该用户数据。");
        }
    }

    private UserNode createCenterNode() {
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("<div id=\"space_avatar\">")) {
                    line = reader.readLine().trim();
                    int index = line.indexOf("http");
                    line = line.substring(index);
                    index = line.indexOf("\"");
                    String imageURL = line.substring(0, index);
                    index = line.indexOf("alt=");
                    line = line.substring(index + 5);
                    if (line.contains("\"")) {
                        line = line.substring(0, line.indexOf("\""));
                    }
                    String tooltip = line;

                    UserNode centerNode = addNode(null, tooltip, imageURL, null);
                    return centerNode;
                }

                line = reader.readLine();
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return null;
    }

    private void addChildrenNode(final UserNode parent) {
        final Frame window = (Frame) TWaverUtil.getWindowForComponent(network);
        final String oldWindowTitle = window.getTitle();
        final String oldName = parent.getName();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                parent.setName("正在下载数据....");
                if (!oldWindowTitle.endsWith("....")) {
                    window.setTitle(oldWindowTitle + " - 正在下载数据....");
                }
            }
        });
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (line.contains("<div class=\"avatar48\">")
                        && line.contains("<a href=\"")
                        && line.contains("alt=\"")
                        && line.contains("<img src=\"")) {
                    int index = line.indexOf("href=\"");
                    String clickURLString = line.substring(index + 6);
                    final String clickURL = clickURLString.substring(0, clickURLString.indexOf("\""));

                    index = line.indexOf("src=\"");
                    line = line.substring(index + 5);
                    final String imageURL = line.substring(0, line.indexOf("\""));

                    index = line.indexOf("alt=");
                    line = line.substring(index + 5);
                    if (line.contains("class")) {
                        line = line.substring(0, line.indexOf("class")).trim();
                    } else if (line.contains("\"")) {
                        line = line.substring(0, line.indexOf("\"")).trim();
                    } else {
                        line = line.substring(0, line.indexOf("/>")).trim();
                    }

                    if (line.endsWith("\"")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    final String tooltip = line;
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            addNode(parent, tooltip, imageURL, clickURL);
                        }
                    });
                }

                line = reader.readLine();
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    parent.setName(oldName);
                }
            });
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (!oldWindowTitle.endsWith("....")) {
                    window.setTitle(oldWindowTitle);
                }

                //do layout.
                if (circleLayout) {
                    Util.layoutNetwork(network);
                } else {
                    double layoutFactor = 80d / network.getDataBox().size();
                    network.getSpringLayouter().setNodeRepulsionFactor(layoutFactor);
                    network.getSpringLayouter().start();
                }
            }
        });
    }

    private UserNode addNode(UserNode parent, String name, final String image, String clickURL) {
        UserNode node = null;
        if (network.getDataBox().getElementByName(name) != null) {
            return (UserNode) network.getDataBox().getElementByName(name);
        } else {
            node = new UserNode();
            if (parent != null) {
                node.setLocation(parent.getLocation());
            } else {
                node.setLocation(network.getCanvas().getWidth() / 2, network.getCanvas().getHeight() / 2);
            }
            String description = "<html><center><img src=\"" + image + "\"></img>";
            description += "<br>" + name;
            if (clickURL != null) {
                description += "<br><a href=\"" + clickURL + "\">" + Util.CSDN_URL_PREFIX + clickURL + "</a>";
            }
            description += "</center></html>";
            node.setToolTipText(description);
            node.setName(name);
            node.setClickURL(clickURL);
            network.getDataBox().addElement(node);
        }
        if (parent != null) {
            UserLink link = new UserLink(parent, node);
            network.getDataBox().addElement(link);
        }
        return node;
    }
}
