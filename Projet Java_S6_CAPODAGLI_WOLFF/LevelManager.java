import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

///////// GENERATION ALEATOIRE DES NIVEAUX SUPERIEURES

public class LevelManager {
    private static final int WIDTH = 19; // Largeur de la carte
    private static final int HEIGHT = 11; // Hauteur de la carte
    private static final char EMPTY = ' ';
    private static final char TREE = 'T';
    private static final char ROCK = 'R';
    private static final char TRAP = 'X';

    // Génère un fichier `level2.txt` avec un chemin jouable.
     public static void generateLevel2() {
        Random random = new Random();
        char[][] grid = new char[HEIGHT][WIDTH];
        
        // Initialisation des bordures
        for (int y = 0; y < HEIGHT; y++) {
            Arrays.fill(grid[y], TREE);
        }
        // Génération des points d'entrée/sortie
        int startY = 6; // Position fixe pour l'exemple
        int endY = 6;
        grid[startY][0] = EMPTY;
        grid[endY][WIDTH - 1] = EMPTY;

        // Algorithme de génération de labyrinthe
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startY, 1}); // Commence à côté de l'entrée
        
        while (!stack.isEmpty()) {
            int[] current = stack.peek(); //On regarde l'élément au dessus de la stack sans l'enlever
            int y = current[0];
            int x = current[1];
            
            List<int[]> neighbors = new ArrayList<>();
            
            // Détection des voisins non visités
            if (y > 1 && grid[y - 2][x] == TREE) neighbors.add(new int[]{y - 2, x});
            if (y < HEIGHT - 2 && grid[y + 2][x] == TREE) neighbors.add(new int[]{y + 2, x});
            if (x > 1 && grid[y][x - 2] == TREE) neighbors.add(new int[]{y, x - 2});
            if (x < WIDTH - 2 && grid[y][x + 2] == TREE) neighbors.add(new int[]{y, x + 2});

            if (!neighbors.isEmpty()) {
                int[] next = neighbors.get(random.nextInt(neighbors.size()));
                int ny = next[0];
                int nx = next[1];
                
                // Creuser le chemin
                grid[ny][nx] = EMPTY;
                grid[(y + ny)/2][(x + nx)/2] = EMPTY; // Mur entre
                stack.push(next);
            } else {
                stack.pop();
            }
        }

        // Connexion garantie à la sortie
        for (int y = endY - 1; y <= endY + 1; y++) {
            if (y >= 0 && y < HEIGHT) {
                grid[y][WIDTH - 2] = EMPTY;
            }
        }

        // Ajout des obstacles aléatoires
        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                if (grid[y][x] == TREE && random.nextFloat() < 0.3) {
                    grid[y][x] = switch(random.nextInt(3)) {
                        case 0 -> ROCK;
                        case 1 -> TRAP;
                        default -> TREE;
                    };
                }
            }
        }

        // Écriture du fichier
        try (FileWriter writer = new FileWriter("./data/level2.txt")) {
            for (char[] row : grid) {
                writer.write(new String(row) + "\n");
            }
            System.out.println("Level2 généré avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    
    //Charge un niveau spécifique depuis un fichier.
    
    public static PlayGround loadLevel(String filePath, DynamicSprite hero,
            RenderEngine renderEngine, PhysicEngine physicEngine) {
        System.out.println("Chargement du niveau: " + filePath);

        // Réinitialiser les moteurs
        physicEngine.clearMovingSprites(); // Vide la liste des sprites mobiles
        physicEngine.setEnvironment(new ArrayList<>()); // Réinitialise l'environnement
        renderEngine.clearRenderList(); // Vide la liste d'affichage

        // Charger le nouveau niveau
        PlayGround level = new PlayGround(filePath);
        Main.currentPlayGround = level;
        // Ajouter les nouveaux sprites
        for (Sprite sprite : level.getSpriteList()) {
            renderEngine.addToRenderList((Displayable) sprite);
        }
        renderEngine.addToRenderList(hero);

        // Mettre à jour l'environnement physique
        physicEngine.setEnvironment(level.getSolidSpriteList());
        physicEngine.addToMovingSpriteList(hero);

        return level;
    }
}
