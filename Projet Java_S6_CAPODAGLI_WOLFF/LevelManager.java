import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LevelManager {
    private static final int WIDTH = 30; // Largeur (ex: 1200 pixels / 40 px par tile)
    private static final int HEIGHT = 20; // Hauteur (ex: 800 pixels / 40 px par tile)

    /**
     * Génère un fichier `level2.txt` aléatoire avec un chemin jouable.
     */
    public static void generateLevel2() {
        Random random = new Random();
        StringBuilder level = new StringBuilder();

        // Générer une position de départ aléatoire sur le bord gauche (excluant les
        // extrémités)
        int startY = random.nextInt(HEIGHT - 2) + 1;

        // FORCER l'écriture de `startY` en première ligne
        level.append(startY).append("\n");
        System.out.println("DEBUG: startY généré = " + startY);

        // Génération de la carte
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (y == 0 || y == HEIGHT - 1 || x == WIDTH - 1) {
                    level.append('T'); // Bordures en arbres
                } else if (x == 0) {
                    if (y == startY) {
                        level.append(' '); // Case d'entrée libre à gauche
                    } else {
                        level.append('T'); // Bordure gauche
                    }
                } else {
                    char tile = switch (random.nextInt(4)) {
                        case 0 -> ' '; // Herbe (zone libre)
                        case 1 -> 'R'; // Roche (obstacle)
                        case 2 -> 'T'; // Arbre (obstacle)
                        case 3 -> 'X'; // Piège
                        default -> ' ';
                    };
                    level.append(tile);
                }
            }
            level.append("\n"); // Passer à la ligne suivante
        }

        // Sauvegarde dans `level2.txt`
        try (FileWriter writer = new FileWriter("./data/level2.txt")) {
            writer.write(level.toString());
            System.out.println("✅ Fichier level2.txt généré avec startY: " + startY);
            System.out.println("Contenu généré :\n" + level.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayGround loadLevel(String filePath, DynamicSprite hero,
            RenderEngine renderEngine, PhysicEngine physicEngine) {

        int startY = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.matches("\\d+")) {
                // Cas d'un fichier comme level2.txt qui a un startY
                startY = Integer.parseInt(firstLine.trim());
            } else {
                // level1.txt : pas de repositionnement automatique
                System.out.println("INFO: Pas de startY dans " + filePath + ", on garde (0,192).");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayGround level = new PlayGround(filePath);
        renderEngine.clearRenderList();
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);

        physicEngine.clearMovingSprites();
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Si un startY a été trouvé, on place le héros
        if (startY != -1) {
            hero.setX(0);
            hero.setY(startY * 40); // ou un offset si tu veux 10 px, etc.
            System.out.println("DEBUG: Héros placé à (0, " + hero.getY() + ")");
        } else {
            // On suppose qu'on est dans le level1 => héros déjà à (0,192)
            System.out.println("DEBUG: Héros garde sa position initiale (0,192) pour level1");
        }

        return level;
    }

}
