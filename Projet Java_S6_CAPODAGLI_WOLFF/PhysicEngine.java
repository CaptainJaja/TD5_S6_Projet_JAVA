import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList<Sprite> environment;
    

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
    } 

    public void addToEnvironmentList(Sprite sprite) {
        if (!environment.contains(sprite)) {
            environment.add(sprite);
        }
    }
    
    public ArrayList<Sprite> getEnvironment() {
        return environment;
    }

    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite) {
        if (!movingSpriteList.contains(sprite)) {
            movingSpriteList.add(sprite);
        }
    }

    public void clearMovingSprites() {
        movingSpriteList.clear();
    }


    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.moveIfPossible(environment);
            // Alignement sur la grille 64x64
            // dynamicSprite.setX(Math.round(dynamicSprite.getX() / 64) * 64);
            // dynamicSprite.setY(Math.round(dynamicSprite.getY() / 64) * 64);
        }
    }
}
