import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    private static JFrame displayZoneFrame;
    public static RenderEngine renderEngine;
    public static PhysicEngine physicEngine;
    public static GameEngine gameEngine;
    private static DynamicSprite hero;
    public static PlayGround currentPlayGround;

    public Main() throws Exception {
        createStartScreen();
    }

    private void createStartScreen() {
        JFrame frame = new JFrame("Jungle RUN");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("./img/EcranTitle.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JButton bouton = new JButton("Start the game");
        bouton.setBounds(500, 700, 200, 40);
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.dispose(); // Fermer l'écran de démarrage
                    initializeGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        backgroundPanel.setLayout(null);
        backgroundPanel.add(bouton);
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    private void initializeGame() throws Exception {
        // Initialisation du jeu après le clic
        displayZoneFrame = new JFrame("Jungle RUN");
        displayZoneFrame.setSize(1232, 736);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hero = new DynamicSprite(0, 192,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, renderEngine);

        // Initialisation du timer
        Timer mainTimer = new Timer(50, e -> {
            physicEngine.update(); // 1. Met à jour les positions (physique)
            gameEngine.update(); // 2. Détecte les collisions (game logic)
            renderEngine.update(); // 3. Rafraîchit l'affichage
        });
        mainTimer.start(); // Démarre le timer

        Main.currentPlayGround = LevelManager.loadLevel(
                "./data/level1.txt",
                hero,
                renderEngine,
                physicEngine);

        displayZoneFrame.add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}