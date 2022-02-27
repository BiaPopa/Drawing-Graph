import javax.swing.SwingUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("./src/input.in");
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            ArrayList<Integer> edgesRead = new ArrayList<>(2 * m);
            for (int i = 0; i < m; ++i) {
                edgesRead.add(scanner.nextInt());
                edgesRead.add((scanner.nextInt()));
            }
            System.out.println(edgesRead);
            DrawCircles drawCircles = new DrawCircles(n, edgesRead);
            SwingUtilities.invokeLater(() -> drawCircles.DrawGraph());
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}