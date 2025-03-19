/*import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener{
    //private final
    DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }
    
    @Override
    public void update() {
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
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
            case KeyEvent.VK_S:
                hero.setVitesse(Vitesse.SLOW);
                break;
            case KeyEvent.VK_F:
                hero.setVitesse(Vitesse.FAST);
                break;
        }
    }
}
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameEngine implements Engine, KeyListener{
    DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * La fonction keyPressed permet de savoir à quelle touche associer quelle action . A utiliser avec
     * des énumerés c'est assez utile
     */

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
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
