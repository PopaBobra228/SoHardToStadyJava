import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ShooterGamePanel extends JPanel implements ActionListener, KeyListener {
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 800;
    private final Player player;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Enemy> enemies;
    private final Timer timer;
    private final int fireRate = 300; // Задержка стрельбы в миллисекундах
    private long lastFireTime; // Время последнего выстрела
    private HybridGame hybridGame;

    public ShooterGamePanel(HybridGame hybridGame) {
        this.hybridGame = hybridGame;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        player = new Player(PANEL_WIDTH / 2, PANEL_HEIGHT - 60);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
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

    private void checkCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Rectangle bulletRect = new Rectangle(bullet.x, bullet.y, 5, 10);

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                Rectangle enemyRect = new Rectangle(enemy.x, enemy.y, 40, 20);
                if (bulletRect.intersects(enemyRect)) {
                    enemy.takeDamage(bullet.damage); // Наносим урон врагу
                    bulletIterator.remove(); // Удаляем пулю после попадания
                    break; // Прерываем цикл, так как пуля уже нанесла урон
                }
            }
        }

        // Удаляем мертвых врагов
        enemies.removeIf(e -> !e.isAlive);
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
        checkCollisions();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDx(-1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDx(1);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFireTime >= fireRate) {
                bullets.add(new Bullet(player.x + 20, player.y - 10));
                lastFireTime = currentTime;
            }
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
        int damage = 20;

        public Bullet(int x, int y) {
            this.x = x;
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
        int health = 100;
        boolean isAlive = true;

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(isAlive ? Color.RED : Color.GRAY); // Если враг мертв, он становится серым
            g.fillRect(x, y, 40, 20);
        }

        public void takeDamage(int damage) {
            health -= damage;
            if (health <= 0) {
                isAlive = false; // Враг умирает, если здоровье <= 0
            }
        }
    }

}