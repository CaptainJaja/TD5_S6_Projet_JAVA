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

    public Main() throws Exception {
        createStartScreen();
    }

    private void createStartScreen() {
        JFrame frame = new JFrame("Mon Ecran");
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
        gameEngine = new GameEngine(hero);

        // Initialisation des timers
        Timer renderTimer = new Timer(50, e -> renderEngine.update());
        Timer gameTimer = new Timer(50, e -> gameEngine.update());
        Timer physicTimer = new Timer(50, e -> physicEngine.update());

        // Démarrage des timers
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        LevelManager.loadLevel("./data/level1.txt", hero, renderEngine, physicEngine);
        displayZoneFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}