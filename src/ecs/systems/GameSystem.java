package ecs.systems;

import ecs.EcsWorld;

public interface GameSystem {
    void onCreate(EcsWorld context);
    void update(float dt);
    void onDestroy();
}
