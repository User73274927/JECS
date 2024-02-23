package ecs;

import java.util.*;

public class EcsContainer {
    private Set<EcsObject> container;
    private HashMap<Class<? extends EcsComponent>, Set<EcsComponent>> sortedComponents;
    private List<EcsContainerListener> ecsContainerListeners;
    private List<Filter<?, ? extends EcsObject>> filters;

    private ObjectAllocator allocator;
    private ActiveObjects activeObjectsContainer;

    public EcsContainer() {
        this.container = new HashSet<>();
        this.activeObjectsContainer = new ActiveObjects();

        ecsContainerListeners = new ArrayList<>();
        filters = new ArrayList<>();
        allocator = new ObjectAllocator();
        sortedComponents = allocator.getSortedItems();
    }

    public void addObjectsListener(EcsContainerListener listener) {
        if (listener == null) return;
        ecsContainerListeners.add(listener);
    }

    public void addFilter(Filter<?, ? extends EcsObject> filter) {
        if (filter == null) return;
        addObjectsListener((EcsObjectFilter) filter);
        filters.add(filter);
    }

    public EcsObjectFilter createFilter() {
        EcsObjectFilter filter = new EcsObjectFilter(container);
        addFilter(filter);
        return filter;
    }

    public void addObject(EcsObject object) {
        if (object == null) return;
        allocator.allocate(object);
        container.add(object);
        enableObject(object);

        for (EcsContainerListener l : ecsContainerListeners) {
            l.onObjectAdded(object);
        }
    }

    public void removeObject(EcsObject object) {
        if (object == null) return;
        allocator.eliminate(object);
        container.remove(object);
        disableObject(object);

        for (EcsContainerListener l : ecsContainerListeners) {
            l.onObjectRemoved(object);
        }
    }

    void enableObject(EcsObject object) {
        activeObjectsContainer.addObject(object);
    }

    void disableObject(EcsObject object) {
        activeObjectsContainer.removeObject(object);
    }

    @SuppressWarnings("unchecked")
    private <T extends EcsComponent>Set<T> getComponentsByClass(Class<T> type, ObjectAllocator allocator) {
        var sortedItems = allocator.getSortedItems();
        if (!sortedItems.containsKey(type)) {
            sortedItems.put(type, new HashSet<>());
        }
        return (Set<T>) sortedItems.get(type);
    }

    public <T extends EcsComponent>Set<T> getComponentsByClass(Class<T> type) {
        return getComponentsByClass(type, this.allocator);
    }

    public <T extends EcsComponent>Set<T> getActiveComponentsByClass(Class<T> type) {
        return activeObjectsContainer.getComponentsByClass(type);
    }

    @SuppressWarnings("unchecked")
    public <T extends EcsObject>T createObject() {
        T instance = (T) new EcsObject(this);
        return instance;
    }

    public ObjectAllocator getObjectAllocator() {
        return allocator;
    }

    public Set<EcsObject> getActiveObjectsContainer() {
        return activeObjectsContainer.getObjects();
    }

    public Set<EcsObject> getObjects() {
        return container;
    }

    public ActiveObjects getActiveObjectContainer() {
        return activeObjectsContainer;
    }

    public class ActiveObjects {
        private EcsObjectFilter filter;
        private ObjectAllocator allocator;

        ActiveObjects() {
            filter = new EcsObjectFilter(container).active(true).apply();
            allocator = new ObjectAllocator();
        }

        void addObject(EcsObject object) {
            if (object.isActive()) {
                allocator.allocate(object);
                filter.onObjectAdded(object);
            }
        }

        void removeObject(EcsObject object) {
            allocator.eliminate(object);
            filter.onObjectRemoved(object);
        }

        public Set<EcsObject> getObjects() {
            return filter.toSet();
        }

        public <T extends EcsComponent>Set<T> getComponentsByClass(Class<T> type) {
            return EcsContainer.this.getComponentsByClass(type, this.allocator);
        }

    }

}
