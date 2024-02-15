package ecs;

import java.util.HashMap;
import java.util.Set;

public interface EcsAllocator<T, R> {
    void allocate(T object);
    void remove(T object);
    HashMap<Class<? extends R>, Set<R>> getSortedItems();
}

