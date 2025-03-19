import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LevelManager {
    private static final int WIDTH = 30; // 30 caractères (≈ 1200 pixels)
    private static final int HEIGHT = 20; // 20 caractères (≈ 800 pixels)

    /**
     * Génère un fichier `level2.txt` aléatoire avec un chemin jouable.
     */
    public static void generateLevel2() {
        Random random = new Random();
        StringBuilder level = new StringBuilder();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (y == 0 || y == HEIGHT - 1 || x == WIDTH - 1) {
                    level.append('T'); // Bordures d'arbres
                } else if (x == 0 && y != HEIGHT / 2) {
                    level.append('T'); // Entrée à gauche
                } else {
                    char tile = switch (random.nextInt(4)) {
                        case 0 -> ' '; // Herbe
                        case 1 -> 'R'; // Roche
                        case 2 -> 'T'; // Arbre
                        case 3 -> 'X'; // Piège
                        default -> ' ';
                    };
                    level.append(tile);
                }
            }
            level.append("\n");
        }

        // Forcer un chemin jouable au centre
        for (int y = 1; y < HEIGHT - 1; y++) {
            level.setCharAt(y * (WIDTH + 1) + WIDTH / 2, ' ');
        }

        try (FileWriter writer = new FileWriter("./data/level2.txt")) {
            writer.write(level.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge un niveau spécifique depuis un fichier.
     * 
     * @param filePath Chemin du fichier du niveau
     * @param hero     Héros du jeu
     */
    public static PlayGround loadLevel(String filePath, DynamicSprite hero, RenderEngine renderEngine, PhysicEngine physicEngine) {
    PlayGround level = new PlayGround(filePath);

    renderEngine.clearRenderList();
    renderEngine.addToRenderList(level.getSpriteList());
    renderEngine.addToRenderList(hero);

    physicEngine.clearMovingSprites();
    physicEngine.addToMovingSpriteList(hero);
    physicEngine.setEnvironment(level.getSolidSpriteList());

    return level;
    }
}
