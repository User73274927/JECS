package ecs.components;

import ecs.entities.GameObject;

public class Transform extends GameComponent {
    public float x, y;
    public float width, height;
    public float rotation;

    public Transform(GameObject gameObject) {
        super(gameObject);
    }
}
