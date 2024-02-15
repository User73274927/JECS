package ecs;

import java.util.*;

public class EcsContainer {
    private Set<EcsObject> container;
    private HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> sortedComponents;
    private List<ContainerListener> containerListeners;
    private List<Filter<? extends EcsObject, ?>> filters;

    private EcsObjectAllocator allocator;

    public EcsContainer(Collection<EcsObject> container) {
        this.container = (Set<EcsObject>) container;
        containerListeners = new ArrayList<>();
        filters = new ArrayList<>();
        allocator = new EcsObjectAllocator();
        sortedComponents = allocator.getSortedItems();
    }

    public EcsContainer() {
        this(new HashSet<>());
    }

    public void addObjectsListener(ContainerListener listener) {
        if (listener == null) return;
        containerListeners.add(listener);
    }

    public void createFilter(Filter<? extends EcsObject, ?> filter) {
        if (filter == null) {
            filter = new EcsComponentFilter(container);
            containerListeners.add((EcsComponentFilter) filter);
        }
        filters.add(filter);
    }

    public void addObject(EcsObject object) {
        if (object == null) return;
        allocator.allocate(object);
        container.add(object);

        for (ContainerListener l : containerListeners) {
            l.onObjectAdded(object);
        }
    }

    public void removeObject(EcsObject object) {
        if (object == null) return;
        allocator.remove(object);
        container.remove(object);

        for (ContainerListener l : containerListeners) {
            l.onObjectRemoved(object);
        }
    }

    public Set<EcsComponent> getComponentsByClass(Class<? extends EcsComponent> clazz) {
        return sortedComponents.get(clazz);
    }

    public Set<EcsObject> getObjects() {
        return container;
    }

}
