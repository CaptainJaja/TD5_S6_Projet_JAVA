import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class SolidSprite extends Sprite{

    public SolidSprite(double x, double y, Image image, double width, double height){
        super(x, y, image, width, height);
    }



    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean intersect(Rectangle2D hitBox) {
        return this.getHitBox().intersects(hitBox);
    }
}
