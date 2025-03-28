import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class TrapSprite extends Sprite { // Hérite de Sprite, pas de SolidSprite
    public TrapSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    @Override
    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}