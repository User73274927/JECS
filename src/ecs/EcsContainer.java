package ecs;

import java.util.*;

public class EcsContainer {
    private Set<EcsObject> container;
    private HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> sortedComponents;
    private List<EcsContainerListener> ecsContainerListeners;
    private List<Filter<?, ? extends EcsObject>> filters;

    private ObjectAllocator allocator;

    public EcsContainer(Collection<EcsObject> container) {
        this.container = (Set<EcsObject>) container;
        ecsContainerListeners = new ArrayList<>();
        filters = new ArrayList<>();
        allocator = new ObjectAllocator();
        sortedComponents = allocator.getSortedItems();
    }

    public EcsContainer() {
        this(new HashSet<>());
    }

    public void addObjectsListener(EcsContainerListener listener) {
        if (listener == null) return;
        ecsContainerListeners.add(listener);
    }

    public void createFilter(Filter<?, ? extends EcsObject> filter) {
        if (filter == null) {
            filter = new EcsObjectFilter(container);
            addObjectsListener((EcsObjectFilter) filter);
        }
        filters.add(filter);
    }

    public void addObject(EcsObject object) {
        if (object == null) return;
        allocator.allocate(object);
        container.add(object);

        for (EcsContainerListener l : ecsContainerListeners) {
            l.onObjectAdded(object);
        }
    }

    public void removeObject(EcsObject object) {
        if (object == null) return;
        allocator.eliminate(object);
        container.remove(object);

        for (EcsContainerListener l : ecsContainerListeners) {
            l.onObjectRemoved(object);
        }
    }

    public ObjectAllocator getObjectAllocator() {
        return allocator;
    }

    public Set<EcsComponent> getComponentsByClass(Class<? extends EcsComponent> clazz) {
        return sortedComponents.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T extends EcsObject>T createObject() {
        T instance = (T) new EcsObject(this);
        return instance;
    }

    public Set<EcsObject> getObjects() {
        return container;
    }

}
