import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener{
    private final DynamicSprite hero;

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
