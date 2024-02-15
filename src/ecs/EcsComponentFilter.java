package ecs;


import java.util.*;
import java.util.function.Predicate;

public class EcsComponentFilter implements Filter<EcsObject, Class<? extends EcsComponent>>, ContainerListener {
    private final Collection<EcsObject> objectData;
    private final Collection<EcsObject> acceptableObjects;

    private List<Class<? extends EcsComponent>> includedClasses;
    private List<Class<? extends EcsComponent>> excludedClasses;

    private Predicate<EcsObject> customFilter;
    private boolean filterChanged;

    public EcsComponentFilter(Collection<EcsObject> objectData) {
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
    public Filter<EcsObject, Class<? extends EcsComponent>> filter(Predicate<EcsObject> func) {
        customFilter = func;
        filterChanged = (func != null);
        return this;
    }

    @Override
    public Filter<EcsObject, Class<? extends EcsComponent>> include(List<Class<? extends EcsComponent>> obj) {
        includedClasses = obj;
        filterChanged = true;
        return this;
    }

    @Override
    public Filter<EcsObject, Class<? extends EcsComponent>> exclude(List<Class<? extends EcsComponent>> obj) {
        excludedClasses = obj;
        filterChanged = true;
        return this;
    }

    @Override
    public Filter<EcsObject, Class<? extends EcsComponent>> apply() {
        recalculate();
        return this;
    }

    public void recalculate() {
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

    public void clear() {
        includedClasses.clear();
        excludedClasses.clear();
        customFilter = null;
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
