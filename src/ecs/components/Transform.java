package ecs.components;

import ecs.EcsComponent;
import ecs.EcsObject;

public class Transform extends EcsComponent {
    public float x, y;
    public float width, height;
    public float rotation;

    public Transform(EcsObject ecsObject) {
        super(ecsObject);
    }
}
