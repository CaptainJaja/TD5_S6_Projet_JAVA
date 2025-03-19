import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {
        // Création de la fenêtre
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1232, 736);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Chargement du héros
        DynamicSprite hero = new DynamicSprite(200, 300,
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
        displayZoneFrame.setVisible(true);

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
