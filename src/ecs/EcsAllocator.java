package ecs;

import java.util.HashMap;
import java.util.Set;

public interface EcsAllocator<T, T1> {
    void allocate(T object);
    void remove(T object);
    HashMap<Class<? extends T1>, Set<T1>> getSortedItems();
}

