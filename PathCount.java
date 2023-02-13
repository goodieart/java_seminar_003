import java.util.Scanner;

import javax.swing.JFrame;
import java.util.Scanner;

public class PathCount {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PathCount");
        Scanner iScanner = new Scanner(System.in);
        int width = iScanner.nextInt();
        int height = iScanner.nextInt();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width > 8 ? 8 * 128 : width * 128, height > 8 ? 8 * 128 : height * 128);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(new Main(frame, width, height));
        frame.setVisible(true);
        iScanner.close();
    }

}
