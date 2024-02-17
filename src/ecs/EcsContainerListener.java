package ecs;

public interface EcsContainerListener {
    void onObjectAdded(EcsObject object);
    void onObjectRemoved(EcsObject object);
}
