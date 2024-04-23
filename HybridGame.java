import javax.swing.*;
import java.awt.*;

enum GameState {
    MAIN_MENU,
    SNAKE_GAME,
    SHOOTER_GAME,
    GAME_OVER
}

public class HybridGame extends JFrame {
    private CardLayout cardLayout;
    private GameState currentState;
    private JPanel cards;

    public HybridGame() {
        super("Hybrid Game:Snake and Shooter");
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        initializeGames();
        add(cards);
        setupMainFrame();
    }

    private void setupMainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setVisible(true);
    }

    private void initializeGames() {
        SnakeGamePanel snakePanel = new SnakeGamePanel(this);
        ShooterGamePanel shooterPanel = new ShooterGamePanel();
        cards.add(snakePanel, GameState.SNAKE_GAME.name());
        cards.add(shooterPanel, GameState.SHOOTER_GAME.name());
        setState(GameState.SNAKE_GAME);
    }


    public void setState(GameState nextState) {
        this.currentState = nextState;
        cardLayout.show(cards, nextState.name());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HybridGame::new);
    }

}