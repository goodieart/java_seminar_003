import java.util.Scanner;

import javax.swing.JFrame;
import java.util.Scanner;

public class PathCount {
    static final int TOOLBAR_HEIGHT = 32;
    public static void main(String[] args) {
        JFrame frame = new JFrame("PathCount");
        Scanner iScanner = new Scanner(System.in);
        int width = iScanner.nextInt();
        int height = iScanner.nextInt();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width > 8 ? 8 * 64 : width * 64, height > 8 ? 8 * 64 + TOOLBAR_HEIGHT : height * 64 + TOOLBAR_HEIGHT);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(new Main(frame, width, height, TOOLBAR_HEIGHT));
        frame.setVisible(true);
        iScanner.close();
    }

}
