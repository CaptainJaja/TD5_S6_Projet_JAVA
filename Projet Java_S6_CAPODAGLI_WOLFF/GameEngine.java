import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener{
    private final DynamicSprite hero;
    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }
    
    @Override
    public void update() {
        if (hero.getHitBox().getX() > 1200) { // Sortie du level1 vers level2
            System.out.println("Le héros sort à droite, passage au level2...");
            LevelManager.generateLevel2();
            LevelManager.loadLevel("./data/level2.txt", hero, Main.renderEngine, Main.physicEngine);

            // 🔹 Positionner le héros à l'entrée du level2 (même Y que la sortie du level1)
            hero.setX(10);
            System.out.println("Héros placé dans level2 à (10, " + hero.getY() + ")");
        }

        if (hero.getHitBox().getX() < 0) { // Sortie de level2 vers level1
            System.out.println("Retour à level1...");
            LevelManager.loadLevel("./data/level1.txt", hero, Main.renderEngine, Main.physicEngine);

            // 🔹 Retour à droite de level1 avec la même Y que sortie du level2
            hero.setX(1180);
            System.out.println("Héros replacé dans level1 à (1180, " + hero.getY() + ")");
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
            case KeyEvent.VK_S:
                hero.setVitesse(Vitesse.SLOW);
                break;
            case KeyEvent.VK_F:
                hero.setVitesse(Vitesse.FAST);
                break;
        }
    }

    

}
