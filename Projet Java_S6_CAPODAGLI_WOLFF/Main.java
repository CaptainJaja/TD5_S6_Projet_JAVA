import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;



public class Main {
    JFrame displayZoneFrame;
    static RenderEngine renderEngine; 
    static PhysicEngine physicEngine; // Ajoute 'static' ici
    static GameEngine gameEngine; 

    public Main() throws Exception {
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1232, 736);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(0, 192,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        LevelManager.loadLevel("./data/level1.txt", hero, renderEngine, physicEngine);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
