import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends JPanel implements ActionListener {
    static final int TILE_WIDTH = 128;
    static final int TILE_HEIGHT = 128;
    static final BigInteger TILE_BLOCKED = new BigInteger("-1");
    static final BigInteger TILE_FREE = new BigInteger("0");
    static final BigInteger TILE_INIT = new BigInteger("1");
    static final Color COLOR_WHITE = new Color(255, 255, 255);
    static final Color COLOR_BLACK = new Color(0, 0, 0);
    static final Color COLOR_RED = new Color(255, 0, 0);

    int toolbarHeight;
    int offsetX, offsetY, miceX, miceY, currentX, currentY, startX, startY, viewportW, viewportH;
    int viewportX = 1;
    int viewportY = 1;

    boolean showGrid = true;
    boolean scrollBlock = false;

    BigInteger[][] map = new BigInteger[0][0];

    Image img = new ImageIcon("gfx/test.png").getImage();
    Image img2 = new ImageIcon("gfx/test2.png").getImage();
    
    Font font = new Font("TimesRoman", Font.PLAIN, 24);
    
    Timer timer = new Timer(20, this);
    JFrame frame;

    {
        MouseAdapter mAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 3) {
                    int mX = (e.getX() - offsetX) / TILE_WIDTH;
                    int mY = (e.getY() - offsetY) / TILE_HEIGHT;
                    map[mX + viewportX][mY + viewportY] = map[mX + viewportX][mY + viewportY] != TILE_BLOCKED
                            ? TILE_BLOCKED
                            : TILE_FREE;
                    if (startX != 0 || startY != 0) refreshMap(startX, startY);
                } else if (e.getButton() == 1)    
                    if (map[currentX][currentY] != TILE_BLOCKED) {
                        startX = currentX;
                        startY = currentY;
                        refreshMap(startX, startY);
                    }
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

    public Main(JFrame frame, int width, int height, boolean randomize, int toolbarHeight) {
        this.frame = frame;
        this.map = new BigInteger[width][height];
        this.miceX = frame.getWidth() / 2;
        this.miceY = frame.getHeight() / 2;
        
        if ((map.length-2) * TILE_WIDTH < frame.getWidth()) {
            this.viewportW = map.length - 3;
            scrollBlock = true;
        } else {
            this.viewportW = Math.round(frame.getWidth() / TILE_WIDTH);
        }
       
        if ((map[0].length-2) * TILE_HEIGHT < frame.getHeight()) {
            this.viewportH = map[0].length - 3;
            scrollBlock = true;
        } else {
            this.viewportH = Math.round(frame.getHeight() / TILE_HEIGHT);
        }

        
        
        this.toolbarHeight = toolbarHeight;
        initMap(randomize);
        this.timer.start();
    }

    private void zeroMap() {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[j][i] = TILE_FREE;
            }
        }
    }

    private void zeroMapPath() {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] != TILE_BLOCKED)
                    map[j][i] = TILE_FREE;
            }
        }
    }

    private void randMap() {
        for (int i = 1; i < map[0].length - 1; i++) {
            for (int j = 1; j < map.length - 1; j++) {
                map[j][i] = ThreadLocalRandom.current().nextInt(-1, 2) == -1 ? TILE_BLOCKED : TILE_FREE;
            }
        }
    }

    private void initMap(boolean randomize) {
        zeroMap();
        if (randomize) randMap();
    }

    private void refreshMap(int x, int y) {
        BigInteger tempX;
        BigInteger tempY;

        zeroMapPath();

        map[x][y] = TILE_INIT;
        for (int i = y; i < map[0].length - 1; i++) {
            for (int j = x; j < map.length - 1; j++) {
                if (i > y || j > x) {
                    if (map[j][i] != TILE_BLOCKED) {
                        tempX = map[j - 1][i] == TILE_BLOCKED ? TILE_FREE : map[j - 1][i];
                        tempY = map[j][i - 1] == TILE_BLOCKED ? TILE_FREE : map[j][i - 1];
                        map[j][i] = tempX.add(tempY);
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        if (!scrollBlock) {
            // X coord testing on offset (left-right side)
            if (miceX < 100) {
                if (viewportX > 1)
                    offsetX += 5;
            } else if (miceX > frame.getWidth() - 100) {
                if (viewportX + viewportW < map.length - 1)
                    offsetX -= 5;
            }
            // Y coord testing on offset (up-down side)
            if (miceY < 100) {
                if (viewportY > 1)
                    offsetY += 5;
            } else if (miceY > frame.getHeight() - 100) {
                if (viewportY + viewportH < map[0].length - 1)
                    offsetY -= 5;
            }
            // X coord testing on offset
            if (offsetX <= -TILE_WIDTH) {
                offsetX = 0;
                if (viewportX + viewportW < map.length - 1) {
                    viewportX++;
                }
            } else if (offsetX >= TILE_WIDTH) {
                offsetX = 0;
                if (viewportX > 1)
                    viewportX--;
            }
            // Y coord testing on offset
            if (offsetY <= -TILE_HEIGHT) {
                if (viewportY + viewportH < map.length - 1)
                    offsetY = 0;
                viewportY++;
            } else if (offsetY >= TILE_HEIGHT) {
                offsetY = 0;
                if (viewportY > 1)
                    viewportY--;
            }
        }

        g.setFont(font);
        g.setColor(COLOR_WHITE);

        for (int i = -1; i < viewportW + 1; i++) {
            for (int j = -1; j < viewportH + 1; j++) {
                if (map[i + viewportX][j + viewportY] == TILE_BLOCKED) {
                    g.drawImage(img2, i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                } else {
                    g.drawImage(img, i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT,
                            null);
                }
                if (showGrid) {
                    g.drawRect(i * TILE_WIDTH + offsetX, j * TILE_HEIGHT + offsetY, TILE_WIDTH, TILE_HEIGHT);
                    g.drawString(map[i + viewportX][j + viewportY].toString(), i * TILE_WIDTH + 20 + offsetX,
                            j * TILE_HEIGHT + 30 + offsetY);
                }
                if (i + viewportX == startX && j + viewportY == startY) {
                    g.setColor(COLOR_RED);
                    g.drawRect(i * TILE_WIDTH + offsetX + 1, j * TILE_HEIGHT + offsetY + 1, TILE_WIDTH - 2, TILE_HEIGHT - 2);
                    g.setColor(COLOR_WHITE);
                }
            }
        }
        
        g.fillRect(0, frame.getHeight() - toolbarHeight, frame.getWidth(), toolbarHeight);
        g.setColor(COLOR_BLACK);
        g.drawString("X:" + Integer.toString(currentX), 10, frame.getHeight() - toolbarHeight + 36);
        g.drawString("Y:" + Integer.toString(currentY), 80, frame.getHeight() - toolbarHeight + 36);
        g.drawString(map[currentX][currentY].toString(), frame.getWidth() / 2,
                frame.getHeight() - toolbarHeight + 36);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
