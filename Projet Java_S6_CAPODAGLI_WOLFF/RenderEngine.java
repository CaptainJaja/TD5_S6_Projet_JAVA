import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JPanel;


public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;

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
    public void paint(Graphics g){
        super.paint(g);
        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }
    }
    
    @Override
    public void update() {
        this.repaint();
    }
}
