import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite {
    private int speed = 5;
    private int heroVitesse;
    private double speedX = 0;
    private double speedY = 0;
    private Vitesse vitesse = Vitesse.SLOW;

    private final int spriteSheetNumberOfColumn=10;
    private int timeBetweenFrame =200;
    private Direction direction= Direction.EAST;


    public Rectangle2D.Double getHitBox() {
        // Réduire légèrement la largeur/hauteur pour faciliter les passages étroits
        double hitboxWidth = width * 0.8; // 80% de la largeur originale
        double hitboxHeight = height * 0.9; // 90% de la hauteur originale

        return new Rectangle2D.Double(
                x + (width - hitboxWidth) / 2, // Centrer horizontalement
                y + (height - hitboxHeight), // Aligner vers le bas
                hitboxWidth,
                hitboxHeight);
    }
    
    public enum Direction {
        NORTH(2), SOUTH(0), EAST(3), WEST(1);

        private int frameLineNumber;

        Direction(int frameLineNumber) {
            this.frameLineNumber = frameLineNumber;
        }

        public int getFrameLineNumber() {
            return frameLineNumber;
        }
        
    }
    
    public double getSpeedX() {
        return speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    private void move() {
        switch (direction) {
            case EAST:
                this.x += speed;
                break;
            case WEST:
                this.x -= speed;
                break;
            case NORTH:
                this.y -= speed;
                break;
            case SOUTH:
                this.y += speed;
                break;
        }
    }

    private void run(){
        heroVitesse =  speed;
        switch (direction) {
            case NORTH:
                this.y -=4*speed;
                break;
            case SOUTH:
                this.y += 4*speed;
                break;
            case WEST:
                this.x -= 4*speed;
                break;
            case EAST:
                this.x += 4*speed;
                break;
        }
    }

    private void run(ArrayList<Sprite> environment) { 
        heroVitesse = 4 * speed;
        int steps = 4;

        for (int i = 0; i < steps; i++) {
            if (isMovingPossible(environment)) { 
                switch (direction) {
                    case EAST -> this.x += speed;
                    case WEST -> this.x -= speed;
                    case NORTH -> this.y -= speed;
                    case SOUTH -> this.y += speed;
                }
            } else {
                break;
            }
        }
    }


    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        double nextX = this.x + speedX;
        double nextY = this.y + speedY;

        // Vérifier les bordures du niveau
        //  nextX < -1 ||
        if ( (nextX < -1 && nextY<200) || nextY < -10 || nextY + height > 720) {
            return false; // Bloque le mouvement hors écran
        }

        // Vérifier les collisions avec les obstacles
        Rectangle2D.Double movedHitbox = new Rectangle2D.Double(nextX, nextY, width, height);
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && sprite != this) {
                if (((SolidSprite) sprite).intersect(movedHitbox)) {
                    return false;
                }
            }
        }
        return true;
    }


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        // Déplacement en X
        double nextX = x + speedX;
        Rectangle2D.Double tempHitboxX = new Rectangle2D.Double(nextX, y, width, height);
        if (isMovingPossible(environment)==false){return;}
        boolean canMoveX = true;

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && ((SolidSprite) sprite).intersect(tempHitboxX)) {
                canMoveX = false;
                break;
            }
        }

        if (canMoveX) {
            x = nextX; // Déplacement complet en X
        } else {
            // Tentative de "glissement" partiel (ex: 1 pixel à la fois)
            double stepX = speedX > 0 ? 1 : -1;
            for (int i = 0; i < Math.abs(speedX); i++) {
                x += stepX;
                if (!isMovingPossible(environment)) {
                    x -= stepX; // Annuler le dernier pas
                    break;
                }
            }
        }

        // Déplacement en Y (même logique)
        double nextY = y + speedY;
        Rectangle2D.Double tempHitboxY = new Rectangle2D.Double(x, nextY, width, height);
        boolean canMoveY = true;

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && ((SolidSprite) sprite).intersect(tempHitboxY)) {
                canMoveY = false;
                break;
            }
        }

        if (canMoveY) {
            y = nextY; // Déplacement complet en Y
        } else {
            double stepY = speedY > 0 ? 1 : -1;
            for (int i = 0; i < Math.abs(speedY); i++) {
                y += stepY;
                if (!isMovingPossible(environment)) {
                    y -= stepY; // Annuler le dernier pas
                    break;
                }
            }
        }
    }
    public void setDirection(Direction direction) {
        this.direction = direction;

        // Réinitialiser les vitesses
         speedX = 0;
         speedY = 0;

        if (direction != null) {
            switch (direction) {
                case EAST:
                    speedX = vitesse == Vitesse.FAST ? 4 * speed : speed;
                    break;
                case WEST:
                    speedX = vitesse == Vitesse.FAST ? -4 * speed : -speed;
                    break;
                case NORTH:
                    speedY = vitesse == Vitesse.FAST ? -4 * speed : -speed;
                    break;
                case SOUTH:
                    speedY = vitesse == Vitesse.FAST ? 4 * speed : speed;
                    break;
            }
        }
    }

    public void setVitesse(Vitesse vitesse) {
        this.vitesse = vitesse;
    }

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }


    @Override
    public void draw(Graphics g) {
        if (direction == null) {
            direction = Direction.EAST; // Garantir une direction valide
        }
        long index = (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);
        int attitude = (direction != null)
                ? direction.getFrameLineNumber()
                : Direction.EAST.getFrameLineNumber(); // Valeur par défaut
        g.drawImage(image, (int)x,(int) y,(int) (width+x),(int) (height+y), 
        (int) (index*this.width),(int) (attitude*height),(int) ((index+1)*this.width), (int) ((attitude+1)*this.height), null);

    }
}

