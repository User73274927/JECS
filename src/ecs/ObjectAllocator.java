package ecs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ObjectAllocator implements HashTableAllocator.Of2<EcsObject, EcsComponent> {
    private final
    HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> sortedComponents;
    private final
    ComponentHashTableAllocator componentAllocator;

    public ObjectAllocator() {
        this.sortedComponents = new HashMap<>();
        this.componentAllocator = new ComponentHashTableAllocator();
    }

    @Override
    public void allocate(EcsObject object) {
        for (EcsComponent ecsComponent : object.getComponents()) {
            componentAllocator.allocate(ecsComponent);
        }
    }

    @Override
    public void eliminate(EcsObject object) {
        for (EcsComponent ecsComponent : object.getComponents()) {
            componentAllocator.eliminate(ecsComponent);
        }
    }

    public ComponentHashTableAllocator getComponentAllocator() {
        return componentAllocator;
    }

    @Override
    public HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> getSortedItems() {
        return sortedComponents;
    }

    public class ComponentHashTableAllocator implements HashTableAllocator.Of1<EcsComponent> {
        @Override
        public void allocate(EcsComponent ecsComponent) {
            if (ecsComponent == null) return;
            Class<? extends EcsComponent> clazz = ecsComponent.getClass();

            if (!sortedComponents.containsKey(clazz)) {
                sortedComponents.put(clazz, new HashSet<>());
            }
            sortedComponents.get(clazz).add(ecsComponent);
        }

        @Override
        public void eliminate(EcsComponent ecsComponent) {
            if (ecsComponent == null || !sortedComponents.containsKey(ecsComponent.getClass())) return;
            sortedComponents.get(ecsComponent.getClass()).remove(ecsComponent);
        }

        @Override
        public HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> getSortedItems() {
            return sortedComponents;
        }

    }

}
