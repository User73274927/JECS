package ecs;

public interface EcsSystem {
    void onCreate(EcsContainer context);
    void update(float dt);
    void onDestroy();
}
