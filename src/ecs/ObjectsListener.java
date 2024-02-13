package ecs;

import ecs.entities.GameObject;

public interface ObjectsListener {
    void onObjectAdded(GameObject object);
    void onObjectRemoved(GameObject object);
}
