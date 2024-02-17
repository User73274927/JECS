package ecs.components;


import ecs.EcsComponent;
import ecs.EcsObject;

public class DrawableItem extends EcsComponent {
    public boolean isVisible;
    public Transform bounds;

    public DrawableItem(EcsObject parent) {
        super(parent);
    }
}
