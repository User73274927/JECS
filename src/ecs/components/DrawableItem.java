package ecs.components;


import ecs.entities.GameObject;

public class DrawableItem extends GameComponent {
    public boolean isVisible;
    public Transform bounds;

    public DrawableItem(GameObject attachedGameObject) {
        super(attachedGameObject);
    }
}
