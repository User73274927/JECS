package ecs;

import java.util.ArrayList;
import java.util.List;

public class EcsObject {
    //Components
    private final List<Class<? extends EcsComponent>> componentTypes = new ArrayList<>();
    private final List<EcsComponent> ecsComponents = new ArrayList<>();

    private EcsContainer world;
    private boolean isActive;
    private int id;

    public EcsObject() {

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
        int i = 0;
        for (EcsComponent ecsComponent : ecsComponents) {
            if (newEcsComponent.getClass() == ecsComponent.getClass()) {
                ecsComponents.set(i, newEcsComponent);
                return;
            }
            i++;
        }
        if (!componentTypes.contains(newEcsComponent.getClass())) {
            componentTypes.add(newEcsComponent.getClass());
        }
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
        ecsComponents.remove(toRemove);
    }

    public List<EcsComponent> getComponents() {
        return ecsComponents;
    }
    public List<Class<? extends EcsComponent>> getComponentTypes() {
        return componentTypes;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
