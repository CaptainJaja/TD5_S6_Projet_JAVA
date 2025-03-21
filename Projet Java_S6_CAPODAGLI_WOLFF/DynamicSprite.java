import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite {
    private boolean isWalking=true;
    private int speed = 5;
    private final int spriteSheetNumberOfColumn=10;
    private int timeBetweenFrame =200;
    private Direction direction= Direction.EAST;
    private Vitesse vitesse = Vitesse.SLOW;
    private int heroVitesse;

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

    private void move() {
        heroVitesse = speed;
        switch (direction) {
            case EAST -> {
                this.x += speed;
            }
            case WEST -> {
                this.x -= speed;
            }
            case NORTH -> {
                this.y -= speed;
            }
            case SOUTH -> {
                this.y += speed;
            }
        }
    }

    private void run(ArrayList<Sprite> environment) { // Ajouter le paramètre
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

    // private boolean isMovingPossible(ArrayList<Sprite> environment){
    //     Rectangle2D.Double moved = new Rectangle2D.Double();
    //     switch (direction) {
    //         case SOUTH:
    //             moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
    //                     super.getHitBox().getWidth(), super.getHitBox().getHeight());
    //             break;
    //         case NORTH:
    //             moved.setRect(super.getHitBox().getX() , super.getHitBox().getY()- speed,
    //                     super.getHitBox().getWidth(), super.getHitBox().getHeight());
    //             break;
    //         case WEST:
    //             moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
    //                     super.getHitBox().getWidth(), super.getHitBox().getHeight());
    //             break;
    //         case EAST:
    //             moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
    //                                 super.getHitBox().getWidth(), super.getHitBox().getHeight());          
    //             break;
    //     }

    //     for(Sprite spt:environment){
    //         if((spt instanceof SolidSprite)&&(spt!=this)){
    //             if(((SolidSprite) spt).intersect(moved)){
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        double nextX = this.x;
        double nextY = this.y;

        // Calculer la prochaine position
        switch (direction) {
            case SOUTH -> nextY += speed;
            case NORTH -> nextY -= speed;
            case WEST -> nextX -= speed;
            case EAST -> nextX += speed;
        }

        // Vérifier les bordures
        if (nextY < 0 || nextY > 10 * 64+20) {
            return false;
        }

        // Vérifier les collisions
        Rectangle2D.Double moved = new Rectangle2D.Double(
                nextX,
                nextY,
                this.width,
                this.height);

        for (Sprite spt : environment) {
            if (spt instanceof SolidSprite && spt != this) {
                if (((SolidSprite) spt).intersect(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

        // Empêcher de sortir en haut ou en bas
        // if (nextY < 64 || nextY > 11 * 64) { // Ajuste selon la hauteur de la map
        //     return false;
        // }

        // moved.setRect(nextX, nextY, super.getHitBox().getWidth(), super.getHitBox().getHeight());

        // for (Sprite spt : environment) {
        //     if ((spt instanceof SolidSprite) && (spt != this)) {
        //         if (((SolidSprite) spt).intersect(moved)) {
        //             return false;
        //         }
        //     }
        // }
        // return true;
    //}

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            switch (vitesse) {
                case FAST -> run(environment); // Passer le paramètre
                case SLOW -> move();
            }
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setVitesse(Vitesse vitesse) {
        this.vitesse = vitesse;
    }

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }


    @Override
    public void draw(Graphics g) {
        long index = (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);
        int attitude=direction.getFrameLineNumber();
        
        g.drawImage(image, (int)x,(int) y,(int) (width+x),(int) (height+y), 
        (int) (index*this.width),(int) (attitude*height),(int) ((index+1)*this.width), (int) ((attitude+1)*this.height), null);

    }
}
