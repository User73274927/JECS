package ecs;

import java.util.HashMap;
import java.util.Set;

public interface HashTableAllocator<T, R, C> {
    void allocate(T object);
    void eliminate(T object);
    HashMap<C, Set<R>> getSortedItems();

    interface Of1<T> extends HashTableAllocator<T, T, Class<? extends T>> {}

    interface Of2<T, R> extends HashTableAllocator<T, R, Class<? extends R>> {}
}

