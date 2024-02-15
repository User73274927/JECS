package ecs;

public interface ContainerListener {
    void onObjectAdded(EcsObject object);
    void onObjectRemoved(EcsObject object);
}
