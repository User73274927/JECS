package ecs;

import ecs.components.Transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EcsObject {
    //Components
    private final List<Class<? extends EcsComponent>> componentTypes = new ArrayList<>();
    private final List<EcsComponent> ecsComponents = new ArrayList<>();

    private EcsContainer parent;
    private boolean isActive;
    private int id;

    public EcsObject(EcsContainer parent) {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    public <T extends EcsComponent>T getComponent(Class<T> clazz) {
        for (EcsComponent ecsComponent : ecsComponents) {
            if (clazz == ecsComponent.getClass()) {
                return (T) ecsComponent;
            }
        }
        return null;
    }

    public void addComponent(EcsComponent newEcsComponent) {
        if (newEcsComponent == null) return;
        int i = 0;
        for (EcsComponent ecsComponent : ecsComponents) {
            if (newEcsComponent.getClass() == ecsComponent.getClass()) {
                ecsComponents.set(i, newEcsComponent);
                parent.getObjectAllocator().getComponentAllocator().allocate(newEcsComponent);
                return;
            }
            i++;
        }
        if (!componentTypes.contains(newEcsComponent.getClass())) {
            componentTypes.add(newEcsComponent.getClass());
        }
        parent.getObjectAllocator().getComponentAllocator().allocate(newEcsComponent);
        ecsComponents.add(newEcsComponent);
    }

    public void removeComponent(Class<? extends EcsComponent> clazz) {
        EcsComponent toRemove = null;
        for (EcsComponent ecsComponent : ecsComponents) {
            if (ecsComponent.getClass() == clazz) {
                toRemove = ecsComponent;
                break;
            }
        }
        parent.getObjectAllocator().getComponentAllocator().eliminate(toRemove);
        ecsComponents.remove(toRemove);
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public EcsComponent[] getComponents() {
        EcsComponent[] components = new EcsComponent[ecsComponents.size()];
        ecsComponents.toArray(components);
        return components;
    }

    public List<Class<? extends EcsComponent>> getComponentTypes() {
        return componentTypes;
    }

    public EcsContainer getParent() {
        return parent;
    }

}
