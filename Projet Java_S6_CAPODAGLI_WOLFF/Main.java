import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Main {
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        /**
         * Definition de l'écran d'entrée*/
        JFrame frame = new JFrame("Mon Ecran");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        /** Centre la fenêtre */

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("C:/Users/laure/IdeaProjects/Projet.dongeon_crawler/Projet_dongeon_crawler/FISE_2024_2025_Dungeon_Crawler/img/EcranTitle.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };


        // Créer un bouton
        JButton bouton = new JButton("Start the game");
        bouton.setBounds(500, 700, 125, 25); // Position et taille du bouton

        // Ajouter le bouton à la fenêtre
        backgroundPanel.setLayout(null); // Désactive le layout pour positionner manuellement
        backgroundPanel.add(bouton);
        frame.setContentPane(backgroundPanel);
        //frame.add(bouton);

        // Rendre la fenêtre visible
        frame.setVisible(true);
        /**
         * Definition de la fenetre du jeu */
        displayZoneFrame = new JFrame("Dongeon Ecran");
        displayZoneFrame.setSize(1232, 736);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Chargement du héros
        DynamicSprite hero = new DynamicSprite(0, 192,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Initialisation des moteurs
        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        // Ajout des Timers pour rafraîchir les moteurs toutes les 50ms
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Ajout du moteur de rendu à la fenêtre
        displayZoneFrame.getContentPane().add(renderEngine);

        /** Ajouter un action listener au bouton */
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayZoneFrame.setVisible(true);
                frame.setVisible(false);
            }
        });

        // Chargement du niveau
        PlayGround level = new PlayGround("./data/level1.txt");

        // Ajout des éléments au moteur de rendu et de physique
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Ajout du gestionnaire de clavier
        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
