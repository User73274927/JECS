package ecs.components;

import ecs.EcsObject;

public interface ObjectChangedListener {
    void objectChanged(EcsObject object);
}
