package ecs.components;

import ecs.EcsComponent;
import ecs.EcsObject;

public class Body extends EcsComponent {
    private Transform transform;

    public Body(EcsObject parent) {
        super(parent);
    }
}
