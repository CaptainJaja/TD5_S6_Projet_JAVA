import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener{
    private final DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }
    
    @Override
    public void update() {
        if (hero.getHitBox().getX() > 1200) { // Sortie à droite vers Level 2
            System.out.println("Le héros sort à droite, chargement du level 2...");
            LevelManager.generateLevel2(); // Génère level2.txt
            LevelManager.loadLevel("./data/level2.txt", hero, Main.renderEngine, Main.physicEngine);
            hero.setX(10); // Entrée du niveau 2
            hero.setY(400);
        }

        if (hero.getHitBox().getX() < 0) { // Retour à Level 1
            System.out.println("Retour à level1...");
            LevelManager.loadLevel("./data/level1.txt", hero, Main.renderEngine, Main.physicEngine);
            hero.setX(1180); // Sortie du level 2 -> entrée dans level 1
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // Implémentation vide pour l'instant (nécessaire pour respecter KeyListener)
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implémentation vide pour l'instant
    }



    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(DynamicSprite.Direction.NORTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(DynamicSprite.Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(DynamicSprite.Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(DynamicSprite.Direction.SOUTH);
            default:
                break;
        }
    }

    

}
