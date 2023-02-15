import java.util.Scanner;
import javax.swing.JFrame;

public class PathCount {
    static final int TOOLBAR_HEIGHT = 64;

    public static void main(String[] args) {
        JFrame frame = new JFrame("PathCount");
        Scanner iScanner = new Scanner(System.in);

        int width = 0;
        int height = 0;
        String sRand = "";

        try {
            System.out.print("Введите ширину карты в клетках: ");
            width = iScanner.nextInt();
            System.out.print("Введите высоту карты в клетках: ");
            height = iScanner.nextInt();
        } catch (Exception e) {
            System.out.println("Неверные входные данные!");
            System.exit(1);
        }

        System.out.print("Заполнить карту случайным образом?(y/n): ");
        while (!(sRand.equals("y") || sRand.equals("n"))) {
            sRand = iScanner.nextLine();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // HD resolution
        // frame.setSize(1280, 720);

        // FHD resolution
        frame.setSize(1920, 1080);
        
        frame.setUndecorated(true);
        boolean rand = sRand.equals("y");
        frame.add(new Main(frame, width + 2, height + 2, rand, TOOLBAR_HEIGHT));
        frame.setVisible(true);

        iScanner.close();
    }

}
