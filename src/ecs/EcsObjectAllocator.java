package ecs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EcsObjectAllocator implements EcsAllocator<EcsObject, EcsComponent> {
    private HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> sortedComponents;

    public EcsObjectAllocator() {
        this.sortedComponents = new HashMap<>();
    }

    @Override
    public void allocate(EcsObject object) {
        for (EcsComponent ecsComponent : object.getComponents()) {
            Class<? extends EcsComponent> clazz = ecsComponent.getClass();

            if (!sortedComponents.containsKey(clazz)) {
                sortedComponents.put(clazz, new HashSet<>());
            }
            sortedComponents.get(clazz).add(ecsComponent);
        }
    }

    @Override
    public void remove(EcsObject object) {
        for (EcsComponent ecsComponent : object.getComponents()) {
            if (!sortedComponents.containsKey(ecsComponent.getClass())) continue;
            sortedComponents.get(ecsComponent.getClass()).remove(ecsComponent);
        }
    }

    @Override
    public HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> getSortedItems() {
        return sortedComponents;
    }
}
