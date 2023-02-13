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
    static final int TILE_WIDTH = 128;
    static final int TILE_HEIGHT = 128;

    int offsetX = 0;
    int offsetY = 0;

    int miceX = 0;
    int miceY = 0;

    int[][] map = new int[0][0];

    Image img = new ImageIcon("gfx/test.png").getImage();
    Image img2 = new ImageIcon("gfx/test2.png").getImage();
    Timer timer = new Timer(20, this);
    JFrame frame;

    {
        MouseAdapter mAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int mY = (e.getX() - offsetX) / TILE_WIDTH;
                int mX = (e.getY() - offsetY) / TILE_HEIGHT;
                map[mX][mY] = map[mX][mY] != -1 ? -1 : 0;
            }

            public void mouseMoved(MouseEvent e) {
                miceX = e.getX();
                miceY = e.getY();
            }
        };
        addMouseListener(mAdapter);
        addMouseMotionListener(mAdapter);
    };

    public Main(JFrame frame, int width, int height) {
        this.frame = frame;
        this.map = new int[width][height];
        this.miceX = frame.getWidth() / 2;
        this.miceY = frame.getHeight() / 2;
        this.timer.start();
    }

    public void paint(Graphics g) {
        if (miceX < 100) {
            offsetX+=5;
        } else if (miceX > frame.getWidth() - 100) {
            offsetX-=5;
        }
        if (miceY < 100) {
            offsetY+=5;
        } else if (miceY > frame.getHeight() - 100) {
            offsetY-=5;
        }
        if (offsetX <= -TILE_WIDTH) {
            offsetX = 0;
        } else if (offsetX >= TILE_WIDTH) {
            offsetX = 0;
        }
        if (offsetY <= -TILE_HEIGHT) {
            offsetY = 0;
        } else if (offsetY >= TILE_HEIGHT) {
            offsetY = 0;
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.setColor(new Color(255, 255, 255));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == -1) {
                    g.drawImage(img2, j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                } else {
                    g.drawImage(img, j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                }
                g.drawRect(j * TILE_WIDTH + offsetX, i * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT);
                g.drawString(Integer.toString(map[i][j]), j * TILE_WIDTH + 20 + offsetX,
                        i * TILE_HEIGHT + 30 + offsetY);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
