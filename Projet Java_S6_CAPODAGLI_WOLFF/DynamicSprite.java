import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite {
    private boolean isWalking=true;
    private double speed=5;
    private final int spriteSheetNumberOfColumn=10;
    private int timeBetweenFrame =200;
    private Direction direction= Direction.SOUTH;
    private Vitesse vitesse = Vitesse.SLOW;

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
    private void move(){
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
            default:
                break;
        }
    }

    private void run(){
        switch (direction){
            case EAST -> {
                this.x += 4*speed;
            }
            case WEST -> {
                this.x -= 4*speed;
            }
            case NORTH -> {
                this.y -= 4*speed;
            }
            case SOUTH -> {
                this.y += 4*speed;
            }
        }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case SOUTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:
                moved.setRect(super.getHitBox().getX() , super.getHitBox().getY()- speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case EAST:
                moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());          
                break;
        }

        for(Sprite spt:environment){
            if((spt instanceof SolidSprite)&&(spt!=this)){
                if(((SolidSprite) spt).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if(isMovingPossible(environment)){
            switch (vitesse) {
                case FAST -> {
                    run();
                }
                case SLOW -> {
                    move();
                }
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setVitesse(Vitesse vitesse) {
        this.vitesse = vitesse;

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
