import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ShooterGamePanel extends JPanel implements ActionListener, KeyListener {
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 1000;
    private final Player player;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Enemy> enemies;
    private final Timer timer;

    public ShooterGamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        player = new Player(PANEL_WIDTH / 2, PANEL_HEIGHT - 60);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        initializeEnemies();
    }

    private void initializeEnemies() {
        for (int i = 0; i < 5; i++) {
            enemies.add(new Enemy(60 + i * 100, 30));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
            Bullet bullet = it.next();
            bullet.update();
            if (bullet.y < 0) {
                it.remove();
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDx(-1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(1);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.x + 20, player.y));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    static class Player {
        int x, y;
        int dx = 0;

        public Player(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, 40, 20);
        }

        public void update() {
            x += dx;
        }

        public void setDx(int dx) {
            this.dx = dx;
        }
    }

    static class Bullet {
        int x, y;
        int dy = -2;

        public Bullet(int x, int y) {
            this.x = y;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 5, 10);
        }

        public void update() {
            y += dy;

        }
    }

    static class Enemy {
        int x, y;

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 40, 20);
        }
    }

}