package ecs.components;


import ecs.EcsComponent;
import ecs.EcsObject;

public class Health extends EcsComponent {
    public boolean isAlive;
    public int health;
    public int armor;

    public Health(EcsObject parent) {
        super(parent);
    }

}
