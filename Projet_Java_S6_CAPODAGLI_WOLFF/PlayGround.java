import java.awt.Image; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PlayGround {
    private ArrayList<Sprite> environment = new ArrayList<>();
    public PlayGround (String pathName){
    try{
        final Image imageTree = ImageIO.read(new File("./img/tree.png"));
        final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
        final Image imageRock = ImageIO.read(new File("./img/rock.png"));
        final Image imageTrap = ImageIO.read(new File("./img/trap.png"));
        final int imageTreeWidth = imageTree.getWidth(null);
        System.out.printf("tree width : %d \n", imageTree.getWidth(null));
        System.out.printf("tree height : %d \n", imageTree.getHeight(null));
        final int imageTreeHeight = imageTree.getHeight(null);
        final int imageGrassWidth = imageGrass.getWidth(null);
        final int imageGrassHeight = imageGrass.getHeight(null);
        final int imageRockWidth = imageRock.getWidth(null);
        final int imageRockHeight = imageRock.getHeight(null);
        final int imageTrapWidth = imageTrap.getWidth(null);
        final int imageTrapHeight = imageTrap.getHeight(null);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
        
        //bufferedReader.readLine();
        
        String line=bufferedReader.readLine();
        int lineNumber = 0;
        int columnNumber = 0;

    while (line!= null){
        for (byte element : line.getBytes(StandardCharsets.UTF_8)){
            switch (element){
            case 'T' : environment.add(new SolidSprite(columnNumber*imageTreeWidth,
            lineNumber*imageTreeHeight,imageTree, imageTreeWidth, imageTreeHeight));
            break;
            case ' ' : 
                environment.add(new BasicSprite( // Utilisez BasicSprite au lieu de Sprite
                        columnNumber * imageGrassWidth,
                        lineNumber * imageGrassHeight,
                        imageGrass,
                        imageGrassWidth,
                        imageGrassHeight));
                break;
            case 'R' : environment.add(new SolidSprite(columnNumber* imageTrapWidth,
            lineNumber* imageTrapHeight, imageRock, imageTrapWidth, imageTrapHeight));
            break;
            case 'X':
                double trapX = columnNumber * imageTrapWidth;
                double trapY = lineNumber * imageTrapHeight;
                environment.add(new TrapSprite(trapX, trapY, imageTrap, imageTrapWidth, imageTrapHeight));
                System.out.println("[DEBUG] Piège chargé : X=" + trapX + ", Y=" + trapY);
            break;
            }
            columnNumber++;
        }
        columnNumber =0;
        lineNumber++;
        line=bufferedReader.readLine();
    }
    }
    catch (Exception e){
    e.printStackTrace();
    }
    }
    
    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSprites = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && !(sprite instanceof TrapSprite)) {
                solidSprites.add(sprite);
            }
        }
        return solidSprites;
    }

    // Dans PlayGround.java
    public ArrayList<Sprite> getSpriteList() { // Changer le type de retour
        ArrayList<Sprite> spriteList = new ArrayList<>();
        for (Sprite sprite : environment) { // environment contient déjà des Sprite
            spriteList.add(sprite);
        }
        return spriteList;
    }
}
