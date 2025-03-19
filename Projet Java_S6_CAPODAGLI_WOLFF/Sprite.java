import java.awt.*;

public class Sprite implements Displayable{
    protected final Image image;
    protected double x;
    protected double y;
    protected final double width;
    protected final double height;
    

    public Sprite(double x, double y,Image image, double width, double height){
        this.image=image;
        this.x = x;
        this.y=y;
        this.width=width;
        this.height=height;     
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}
