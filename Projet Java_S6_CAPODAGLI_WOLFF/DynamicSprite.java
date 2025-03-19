import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite {
    private boolean isWalking=true;
    private int speed=5;
    private final int spriteSheetNumberOfColumn=10;
    private int timeBetweenFrame =200;
    private Direction direction= Direction.EAST;
    private Vitesse vitesse = Vitesse.SLOW;
    private int heroVitesse;


    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+heroVitesse,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-heroVitesse,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-heroVitesse,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+heroVitesse,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setVitesse(Vitesse vitesse){
            this.vitesse = vitesse;
    }

    private void move(){
        heroVitesse =  speed;
        switch (direction) {
            case NORTH:
                this.y -=speed;
                break;
            case SOUTH:
                this.y += speed;
                break;
            case WEST:
                this.x -= speed;
                break;
            case EAST:
                this.x += speed;
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

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            switch (vitesse) {
                case FAST -> {
                    run();
                }
                case SLOW -> {
                    move();
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        long index = (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);
        int attitude=direction.getFrameLineNumber();
        
        g.drawImage(image, (int)x,(int) y,(int) (width+x),(int) (height+y), 
        (int) (index*this.width),(int) (attitude*height),(int) ((index+1)*this.width), (int) ((attitude+1)*this.height), null);

    }
}

