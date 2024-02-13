package ecs.systems;



import ecs.EcsFilter;
import ecs.EcsWorld;
import ecs.components.Health;
import ecs.components.Sprite2D;
import ecs.components.Transform;
import ecs.entities.GameObject;

import java.util.List;

public class DamageSystem implements GameSystem {
    private List<GameObject> objects;
    private EcsFilter filter;

    @Override
    public void onCreate(EcsWorld context) {
        filter = context.createFilter();
        filter.exclude(List.of( Sprite2D.class ));
    }

    @Override
    public void update(float dt) {
        System.out.println(filter.toSet());
    }

    @Override
    public void onDestroy() {

    }

}
