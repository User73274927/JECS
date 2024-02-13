package ecs;



import ecs.components.GameComponent;
import ecs.entities.GameObject;

import java.util.*;

public class EcsWorld {
    private Set<GameObject> allItems;
    private HashMap<Class<? extends GameComponent>, Set<GameComponent>> sortedComponents;
    private List<Filter<GameObject, ?>> filters;
    private List<ObjectsListener> objectsListeners;

    private GameObjectAllocator allocator;

    public EcsWorld() {
        allItems = new HashSet<>();
        objectsListeners = new ArrayList<>();
        filters = new ArrayList<>();
        this.allocator = new GameObjectAllocator();
        this.sortedComponents = allocator.getSortedItems();
    }

    public EcsFilter createFilter() {
        EcsFilter instance = new EcsFilter(allItems);
        objectsListeners.add(instance);
        filters.add(instance);
        return instance;
    }

    public void addItem(GameObject object) {
        if (object == null) return;
        for (ObjectsListener l : objectsListeners) {
            l.onObjectAdded(object);
        }
        allocator.allocate(object);
        allItems.add(object);
    }

    public void removeItem(GameObject object) {
        if (object == null) return;
        for (ObjectsListener l : objectsListeners) {
            l.onObjectRemoved(object);
        }
        allocator.remove(object);
        allItems.remove(object);
    }

    public Set<GameComponent> getComponentsByClass(Class<? extends GameComponent> clazz) {
        return sortedComponents.get(clazz);
    }

    public Set<GameObject> getItems() {
        return allItems;
    }

    public GameObjectAllocator getAllocator() {
        return allocator;
    }

}
