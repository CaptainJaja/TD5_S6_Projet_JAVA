import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;

public class GameEngine implements Engine, KeyListener {
    private final DynamicSprite hero;
    private boolean gameOver = false;
    private Image gameOverImage;
    private String currentLevel = "./data/level1.txt";

    public GameEngine(DynamicSprite hero, RenderEngine renderEngine) {
        this.hero = hero;
        try {
            gameOverImage = ImageIO.read(new File("./img/gameover.png"));
            renderEngine.setGameOverImage(gameOverImage); // Transmettez l'image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (gameOver)
            return;

        // Gestion des changements de niveau en premier
        if (hero.getHitBox().getX() > 1200) {
            currentLevel = "./data/level2.txt";
            LevelManager.generateLevel2();
            LevelManager.loadLevel(currentLevel, hero, Main.renderEngine, Main.physicEngine);
            hero.setX(10);
        } else if (hero.getHitBox().getX() < 0) {
            currentLevel = "./data/level1.txt";
            LevelManager.loadLevel(currentLevel, hero, Main.renderEngine, Main.physicEngine);
            hero.setX(1180);
        }

        // Gestion des collisions avec les pièges après le changement de niveau
        Rectangle2D.Double heroHitbox = hero.getHitBox();
        System.out.println("Hero Hitbox: " + heroHitbox);

        for (Sprite sprite : Main.currentPlayGround.getSpriteList()) {
            if (sprite instanceof TrapSprite) {
                Rectangle2D.Double trapHitbox = ((TrapSprite) sprite).getHitBox();
                System.out.println("Trap Hitbox: " + trapHitbox);

                if (heroHitbox.intersects(trapHitbox)) {
                    gameOver = true;
                    System.out.println("GAME OVER !");
                    Main.renderEngine.repaint();
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ne pas réinitialiser la direction, juste arrêter le mouvement
        // hero.setSpeedX(0);
        // hero.setSpeedY(0);
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
                break;
            case KeyEvent.VK_S:// Touche F pour ralentir
                hero.setVitesse(Vitesse.SLOW);
                break;
            case KeyEvent.VK_F:// Touche F pour accelerer
                hero.setVitesse(Vitesse.FAST);
                break;
            case KeyEvent.VK_SPACE: // Touche ESPACE pour s'arrêter
                hero.setSpeedX(0);
                hero.setSpeedY(0);
                break;
            case KeyEvent.VK_R:
                if (gameOver)
                    resetGame();
                break;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void resetGame() {
        gameOver = false;

        // Réinitialiser le héros aux coordonnées de départ
        hero.setX(0); // Position X initiale
        hero.setY(192); // Position Y initiale
        hero.setSpeedX(0);
        hero.setSpeedY(0);

        // Recharger le niveau depuis le début
        LevelManager.loadLevel("./data/level1.txt", hero, Main.renderEngine, Main.physicEngine);

        // Forcer le rafraîchissement
        Main.renderEngine.repaint();
        Main.currentPlayGround = new PlayGround("./data/level1.txt"); // Recréer le niveau
    }

}
