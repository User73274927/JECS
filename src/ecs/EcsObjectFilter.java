package ecs;


import java.util.*;
import java.util.function.Predicate;

public class EcsObjectFilter implements Filter<EcsObjectFilter, EcsObject>, EcsContainerListener {
    private final Collection<EcsObject> objectData;
    private final Collection<EcsObject> acceptableObjects;

    private List<Class<? extends EcsComponent>> includedClasses;
    private List<Class<? extends EcsComponent>> excludedClasses;

    private Predicate<EcsObject> customFilter;
    private boolean onlyActiveObjects;
    private boolean filterChanged;

    public EcsObjectFilter(Collection<EcsObject> objectData) {
        this.acceptableObjects = new HashSet<>();
        this.includedClasses = new ArrayList<>();
        this.excludedClasses = new ArrayList<>();
        this.objectData = objectData;
    }

    @Override
    public void onObjectAdded(EcsObject object) {
        if (isAccept(object)) {
            acceptableObjects.add(object);
        }
    }

    @Override
    public void onObjectRemoved(EcsObject object) {
        acceptableObjects.remove(object);
    }

    private boolean isAccept(EcsObject obj) {
        return obj != null
                && (!onlyActiveObjects || obj.isActive())
                && (containsIncludes(obj) && !containsExcludes(obj))
                && (customFilter == null || customFilter.test(obj));
    }

    private boolean containsIncludes(EcsObject obj) {
        return new HashSet<>(obj.getComponentTypes()).containsAll(includedClasses);
    }

    private boolean containsExcludes(EcsObject obj) {
        return obj.getComponentTypes().stream().anyMatch(e -> excludedClasses.contains(e));
    }

    @Override
    public EcsObjectFilter filter(Predicate<EcsObject> func) {
        customFilter = func;
        filterChanged = (func != null);
        return this;
    }

    public EcsObjectFilter include(List<Class<? extends EcsComponent>> obj) {
        includedClasses.addAll(obj);
        filterChanged = true;
        return this;
    }

    public EcsObjectFilter exclude(List<Class<? extends EcsComponent>> obj) {
        excludedClasses.addAll(obj);
        filterChanged = true;
        return this;
    }

    public EcsObjectFilter active(boolean active) {
        filterChanged = (active != onlyActiveObjects);
        onlyActiveObjects = active;
        return this;
    }

    @Override
    public EcsObjectFilter apply() {
        recalculate();
        return this;
    }

    private void recalculate() {
        if (filterChanged) {
            acceptableObjects.clear();

            for (EcsObject obj : objectData) {
                if (isAccept(obj)) {
                    acceptableObjects.add(obj);
                }
            }
        }
        filterChanged = false;
    }

    public EcsObjectFilter clear() {
        clearIncludes();
        clearExcludes();
        customFilter = null;
        return this;
    }

    public EcsObjectFilter clearIncludes() {
        includedClasses.clear();
        return this;
    }

    public EcsObjectFilter clearExcludes() {
        excludedClasses.clear();
        return this;
    }

    @Override
    public List<EcsObject> toList() {
        recalculate();
        return (List<EcsObject>) acceptableObjects;
    }

    public Set<EcsObject> toSet() {
        recalculate();
        return (Set<EcsObject>) acceptableObjects;
    }

}
