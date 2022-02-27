import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.Font;

public class DrawCircles extends JFrame {
    private final Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 25);
    private int n;
    private ArrayList<Integer> edgesRead;

    public DrawCircles(int n, ArrayList<Integer> edgesRead) {
        this.n = n;
        this.edgesRead = edgesRead;
    }

    public void DrawGraph() {
        setSize(new Dimension(1000, 1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                int xcenter = 500;
                int ycenter = 500;
                int radius = 400;
                ArrayList<Ellipse2D.Double> listOfNodes = new ArrayList<>();
                for (int i = 0; i < n; ++i) {
                    int x = (int) (xcenter + radius * Math.cos((2 * Math.PI * i) / n));
                    int y = (int) (ycenter + radius * Math.sin((2 * Math.PI * i) / n));
                    Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 50, 50);
                    listOfNodes.add(circle);
                    g2.draw(circle);
                    g2.setFont(sanSerifFont);
                    String number = Integer.toString(i + 1);
                    g2.drawString(number, x + 20, y + 35);
                }
                ArrayList<Line2D> edges = drawEdges(edgesRead, listOfNodes);
                for (Line2D edge : edges) {
                    g2.draw(edge);
                }
            }
        };
        setTitle("My Graph");
        getContentPane().add(panel);
    }

    public double getAngle(Point2D.Double from, Point2D.Double to) {
        double x = from.getX();
        double y = from.getY();
        double deltaX = to.getX() - x;
        double deltaY = to.getY() - y;
        return Math.atan2(deltaY, deltaX);
    }

    public Point2D.Double getPointOnCircle(Point2D.Double center, double radians, double radius) {
        double x = center.getX();
        double y = center.getY();
        double xPosy = x + Math.cos(radians) * radius;
        double yPosy = y + Math.sin(radians) * radius;
        return new Point2D.Double(xPosy, yPosy);
    }

    public ArrayList<Line2D> drawEdges(ArrayList<Integer> edgesRead, ArrayList<Ellipse2D.Double> listOfNodes) {
        ArrayList<Line2D> edges = new ArrayList<>();
        for (int i = 0; i < edgesRead.size(); i += 2) {
            int n1 = edgesRead.get(i) - 1;
            int n2 = edgesRead.get(i + 1) - 1;

            double x1 = listOfNodes.get(n1).getCenterX();
            double y1 = listOfNodes.get(n1).getCenterY();
            Point2D.Double centerOfn1 = new Point2D.Double(x1, y1);

            double x2 = listOfNodes.get(n2).getCenterX();
            double y2 = listOfNodes.get(n2).getCenterY();
            Point2D.Double centerOfn2 = new Point2D.Double(x2, y2);

            double angle1 = getAngle(centerOfn1, centerOfn2);
            double angle2 = getAngle(centerOfn2, centerOfn1);

            Point2D.Double pointOnNode1 = getPointOnCircle(centerOfn1, angle1, 25);
            Point2D.Double pointOnNode2 = getPointOnCircle(centerOfn2, angle2, 25);

            Line2D.Double line = new Line2D.Double(pointOnNode1, pointOnNode2);
            edges.add(line);
        }
        return edges;
    }
}