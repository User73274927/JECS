package ecs.components;

import ecs.entities.GameObject;

public class Body extends GameComponent {
    private Transform transform;

    public Body(GameObject gameObject) {
        super(gameObject);
    }
}
