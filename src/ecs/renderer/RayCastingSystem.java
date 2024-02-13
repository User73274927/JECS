package ecs.renderer;

import ecs.Filter;
import ecs.entities.GameObject;
import ecs.components.GameComponent;
import ecs.EcsWorld;
import ecs.systems.GameSystem;

public class RayCastingSystem implements GameSystem {
    Filter<GameObject, GameComponent> filter;
    EcsWorld context;

    public RayCastingSystem(EcsWorld context) {
        this.context = context;
    }

    @Override
    public void onCreate(EcsWorld context) {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void onDestroy() {

    }

}
