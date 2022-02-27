import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
    private final Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 25);
    private ArrayList<Ellipse2D.Double> listOfNodes = new ArrayList<>();
    private ArrayList<Point2D.Double> edgesRead = new ArrayList<>();
    private ArrayList<Line2D.Double> edges = new ArrayList<>();

    public DrawPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point2D.Double point = new Point2D.Double(e.getX(), e.getY());
                Point2D.Double circlePoint = isInsideCircle(point, listOfNodes);
                if (circlePoint == null) {
                    listOfNodes.add(new Ellipse2D.Double(point.getX() - 25, point.getY() - 25, 50, 50));
                } else {
                    edgesRead.add(circlePoint);
                }
                repaint();
            }
        });
    }

    private Point2D.Double isInsideCircle(Point2D.Double point, ArrayList<Ellipse2D.Double> listOfNodes) {
        for (Ellipse2D.Double circle : listOfNodes) {
            Point2D.Double center = new Point2D.Double(circle.getCenterX(), circle.getCenterY());
            double deltax = point.getX() - center.getX();
            double deltay = point.getY() - center.getY();
            if ((Math.sqrt(deltax * deltax + deltay * deltay)) < 25) {
                return center;
            }
        }
        return null;
    }

    private double getAngle(Point2D.Double from, Point2D.Double to) {
        double x = from.getX();
        double y = from.getY();
        double deltaX = to.getX() - x;
        double deltaY = to.getY() - y;
        return Math.atan2(deltaY, deltaX);
    }

    private Point2D.Double getPointOnCircle(Point2D.Double center, double radians, double radius) {
        double x = center.getX();
        double y = center.getY();
        double xPosy = x + Math.cos(radians) * radius;
        double yPosy = y + Math.sin(radians) * radius;
        return new Point2D.Double(xPosy, yPosy);
    }

    private void getEdges() {
        for (int i = 1; i < edgesRead.size(); i += 2) {
            Point2D.Double centerOfn1 = edgesRead.get(i - 1);

            Point2D.Double centerOfn2 = edgesRead.get(i);

            double angle1 = getAngle(centerOfn1, centerOfn2);
            double angle2 = getAngle(centerOfn2, centerOfn1);

            Point2D.Double pointOnNode1 = getPointOnCircle(centerOfn1, angle1, 25);
            Point2D.Double pointOnNode2 = getPointOnCircle(centerOfn2, angle2, 25);

            Line2D.Double line = new Line2D.Double(pointOnNode1, pointOnNode2);
            edges.add(line);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.black);
        for (int i = 0; i < listOfNodes.size(); ++i) {
            g2.draw(listOfNodes.get(i));
            g2.setFont(sanSerifFont);
            String number = Integer.toString(i + 1);
            g2.drawString(number, (int) listOfNodes.get(i).getCenterX() - 8,  (int) listOfNodes.get(i).getCenterY() + 10);
        }
        getEdges();
        for (Line2D.Double edge : edges) {
            g2.draw(edge);
        }
    }

}