import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;


public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private Image gameOverImage;

    public RenderEngine (){
        renderList = new ArrayList<>();
    }
    
    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }
    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }

    public void clearRenderList() {
        renderList.clear();
    }
    
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Dessiner les sprites
        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }
        // Dessiner l'image de Game Over par-dessus
        if (Main.gameEngine != null && Main.gameEngine.isGameOver()) {
            g.drawImage(gameOverImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
    
    public void setGameOverImage(Image image) {
        this.gameOverImage = image;
    }
    
    @Override
    public void update() {
        this.repaint();
    }
}
