import java.util.Scanner;

import javax.swing.JFrame;
import java.util.Scanner;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class PathCount {
    static final int TOOLBAR_HEIGHT = 64;
    public static void main(String[] args) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        JFrame frame = new JFrame("PathCount");
        Scanner iScanner = new Scanner(System.in);
        int width = iScanner.nextInt();
        int height = iScanner.nextInt();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(width > 8 ? 8 * 64 : width * 64, height > 8 ? 8 * 64 + TOOLBAR_HEIGHT : height * 64 + TOOLBAR_HEIGHT);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //HD resolution
        //frame.setSize(1280, 720);
        
        //FHD resolution
        frame.setSize(1920, 1080);

        //Fullscreen mode
        // int scrWidth = gd.getDisplayMode().getWidth();
        // int scrHeight = gd.getDisplayMode().getHeight();
        // frame.setAlwaysOnTop(true);
        // frame.setLocation(0, 0);
        // frame.setSize(scrWidth, scrHeight);

        //frame.setLocation(, height);
        frame.setUndecorated(true);
        //frame.pack();
        frame.add(new Main(frame, width + 2, height + 2, TOOLBAR_HEIGHT));
        frame.setVisible(true);
        

        iScanner.close();
    }

}
