package ecs.components;


import ecs.entities.GameObject;

public class Health extends GameComponent {
    public boolean isAlive;
    public int health;
    public int armor;

    public Health(GameObject attachedGameObject) {
        super(attachedGameObject);
    }

}
