package ecs;


import ecs.components.GameComponent;
import ecs.entities.GameObject;

import java.util.*;
import java.util.function.Predicate;

public class EcsFilter implements Filter<GameObject, Class<? extends GameComponent>>, ObjectsListener {
    private final Collection<GameObject> objectData;
    private Set<GameObject> acceptableObjects;

    private List<Class<? extends GameComponent>> includedClasses;
    private List<Class<? extends GameComponent>> excludedClasses;

    private Predicate<GameObject> customFilter;
    private boolean filterChanged;

    public EcsFilter(Collection<GameObject> objectData) {
        this.acceptableObjects = new HashSet<>();
        this.includedClasses = new ArrayList<>();
        this.excludedClasses = new ArrayList<>();
        this.objectData = objectData;
    }

    @Override
    public void onObjectAdded(GameObject object) {
        if (isAccept(object)) {
            acceptableObjects.add(object);
        }
    }

    @Override
    public void onObjectRemoved(GameObject object) {
        acceptableObjects.remove(object);
    }

    private boolean isAccept(GameObject obj) {
        return obj != null
                && (containsIncludes(obj) && !containsExcludes(obj))
                && (customFilter == null || customFilter.test(obj));
    }

    private boolean containsIncludes(GameObject obj) {
        return new HashSet<>(obj.getComponentTypes()).containsAll(includedClasses);
    }

    private boolean containsExcludes(GameObject obj) {
        return obj.getComponentTypes().stream().anyMatch(e -> excludedClasses.contains(e));
    }

    @Override
    public Filter<GameObject, Class<? extends GameComponent>> filter(Predicate<GameObject> func) {
        customFilter = func;
        filterChanged = (func != null);
        return this;
    }

    @Override
    public Filter<GameObject, Class<? extends GameComponent>> include(List<Class<? extends GameComponent>> obj) {
        includedClasses = obj;
        filterChanged = true;
        return this;
    }

    @Override
    public Filter<GameObject, Class<? extends GameComponent>> exclude(List<Class<? extends GameComponent>> obj) {
        excludedClasses = obj;
        filterChanged = true;
        return this;
    }

    public Filter<GameObject, Class<? extends GameComponent>> recalculate() {
        if (filterChanged) {
            acceptableObjects.clear();

            for (GameObject obj : objectData) {
                if (isAccept(obj)) {
                    acceptableObjects.add(obj);
                }
            }
        }
        filterChanged = false;
        return this;
    }

    public void clear() {
        includedClasses.clear();
        excludedClasses.clear();
        customFilter = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<GameObject> toSet() {
        recalculate();
        return acceptableObjects;
    }

}
