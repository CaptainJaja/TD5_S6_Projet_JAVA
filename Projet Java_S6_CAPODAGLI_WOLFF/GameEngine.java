import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener{
    private final DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }
    
    @Override
    public void update() {
        if (hero.getHitBox().getX() > 1200) {
            System.out.println("Le héros sort à droite, chargement du level 2...");
            LevelManager.generateLevel2();
            LevelManager.loadLevel("./data/level2.txt", hero, Main.renderEngine, Main.physicEngine);

            // Position exacte du héros sur la case vide à gauche
            hero.setX(10); // Ajuste légèrement selon taille sprite
            hero.setY(400); // Milieu vertical de la fenêtre
        }

        if (hero.getHitBox().getX() < 0) {
            System.out.println("Retour à level1...");
            LevelManager.loadLevel("./data/level1.txt", hero, Main.renderEngine, Main.physicEngine);

            // Position exacte du héros en revenant à droite dans level1
            hero.setX(1180);
            hero.setY(400);
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
