import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JPanel implements ActionListener {
    static final int TILE_WIDTH = 64;
    static final int TILE_HEIGHT = 64;

    int toolbarHeight;

    int offsetX = 0;
    int offsetY = 0;

    int miceX = 0;
    int miceY = 0;

    int currentX;
    int currentY;

    int viewportX = 1;
    int viewportY = 1;
    int viewportW = 0;
    int viewportH = 0;

    boolean showGrid = true;
    
    long[][] map = new long[0][0];

    Image img = new ImageIcon("gfx/test.png").getImage();
    Image img2 = new ImageIcon("gfx/test2.png").getImage();
    Timer timer = new Timer(1, this);
    JFrame frame;

    {
        MouseAdapter mAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int mX = (e.getX() - offsetX) / TILE_WIDTH;
                int mY = (e.getY() - offsetY) / TILE_HEIGHT;
                map[mX + viewportX][mY + viewportY] = map[mX + viewportX][mY + viewportY] != -1 ? -1 : 0;
                // map[mX][mY] = map[mX][mY] != -1 ? -1 : 0;
            }

            public void mouseMoved(MouseEvent e) {
                miceX = e.getX();
                miceY = e.getY();
                currentX = (e.getX() - offsetX) / TILE_WIDTH + viewportX;
                currentY = (e.getY() - offsetY) / TILE_HEIGHT + viewportY;
            }
        };
        addMouseListener(mAdapter);
        addMouseMotionListener(mAdapter);
    };

    public Main(JFrame frame, int width, int height, int toolbarHeight) {
        this.frame = frame;
        this.map = new long[width][height];
        this.miceX = frame.getWidth() / 2;
        this.miceY = frame.getHeight() / 2;
        // TODO: DEBUG
        this.viewportW = 8;
        this.viewportH = 8;
        this.toolbarHeight = toolbarHeight;
        initMap();
        this.timer.start();
    }

    private void zeroMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[j][i] = 1;
            }
        }
    }

    private void initMap() {
        zeroMap();
        map[1][1] = 1;
        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map[0].length; j++) {
                if (i != 1 && j != 1) map[j][i] = map[j-1][i] + map[j][i-1];
            }
        }
    }

    public void paint(Graphics g) {
        if (miceX < 100) {
            if (viewportX > 1) offsetX+=5;
        } else if (miceX > frame.getWidth() - 100) {
            offsetX-=5;
        }
        if (miceY < 100) {
            if (viewportY > 1) offsetY+=5;
        } else if (miceY > frame.getHeight() - 100) {
            offsetY-=5;
        }
        if (offsetX <= -TILE_WIDTH) {
            offsetX = 0;
            if (viewportX < map.length) viewportX++;
        } else if (offsetX >= TILE_WIDTH) {
            offsetX = 0;
            if (viewportX > 1) viewportX--;
        }
        if (offsetY <= -TILE_HEIGHT) {
            offsetY = 0;
            viewportY++;
        } else if (offsetY >= TILE_HEIGHT) {
            offsetY = 0;
            viewportY--;
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        g.setColor(new Color(255, 255, 255));
        // for (int i = 0; i < map.length; i++) {
        //     for (int j = 0; j < map[0].length; j++) {
        //         if (map[i][j] == -1) {
        //             g.drawImage(img2, j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
        //                     null);
        //         } else {
        //             g.drawImage(img, j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
        //                     null);
        //         }
        //         g.drawRect(j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT);
        //         g.drawString(Integer.toString(map[i][j]), j * TILE_WIDTH + 20 + offsetX,
        //                 i * TILE_HEIGHT + 30 + offsetY);
        //     }
        // }
        for (int i = -1; i < viewportW + 1; i++) {
            for (int j = -1; j < viewportH + 1; j++) {
                if (map[i + viewportX][j + viewportY] == -1) {
                    g.drawImage(img2, i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                } else {
                    g.drawImage(img, i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                }
                if (showGrid) {
                g.drawRect(i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT);
                g.drawString(Long.toString(map[i + viewportX][j + viewportY]), i * TILE_WIDTH + 20 + offsetX,
                        j * TILE_HEIGHT + 30 + offsetY);
                }
                g.fillRect(0, frame.getHeight() - toolbarHeight, frame.getWidth(), toolbarHeight);
                g.setColor(new Color(0,0,0));
                g.drawString(Integer.toString(currentX), 10, frame.getHeight() - toolbarHeight + 12);
                g.drawString(Long.toString(map[currentX][currentY]), frame.getWidth() / 2, frame.getHeight() - toolbarHeight + 12);
                g.setColor(new Color(255,255,255));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
