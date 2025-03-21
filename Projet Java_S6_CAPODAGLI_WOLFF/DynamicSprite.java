import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class DynamicSprite extends SolidSprite {
    private boolean isWalking = true;
    private int speed = 5;
    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrames = 200;
    private Direction direction = Direction.EAST;
    private Vitesse vitesse = Vitesse.SLOW;
    private int heroVitesse;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y,image, width, height);
    }
    /**
     * Cette fonction est a modifier pour éviter les bugs lorsqu'on court. Idéalement il faudrait
     * une variable heroVitesse sachant si on court ou pas
     * MAIS sans fonctionne aussi très bien
     * Modification faite mais cela n'a pas regleé le problème
     */
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

    public void setVitesse(Vitesse vitesse) {
        this.vitesse = vitesse;
    }

    private void move(){
        heroVitesse = speed;
        switch (direction){
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

    private void run(){
        heroVitesse = 4*speed;
        /** Pour quand il est rapide, le hero peut passer partout */
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

    /** Idde de code pour la barre de vie: si le hero rencontre un arbre -1 si un rocher -5 et un trap il meurt illico
     * donc pour ce la le hero doit avoir une barre de vie, afficher GAME OVER si il est mort */

    @Override
    public void draw(Graphics g){
        int index = (int) (System.currentTimeMillis()/timeBetweenFrames%spriteSheetNumberOfColumn);
        int attitude = direction.getFrameLineNumber();
        g.drawImage(image, (int) x, (int) y, (int)(width+x), (int)(height+y),
                (int)(index*this.width),(int)(attitude*height),
                (int)((index+1)*this.width),(int)((attitude+1)*this.height),null);

    }
}


