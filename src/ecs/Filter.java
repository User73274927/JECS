package ecs;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface Filter<T, I> {
    Filter<T, I> filter(Predicate<T> func);
    Filter<T, I> include(List<I> obj);
    Filter<T, I> exclude(List<I> obj);
    Set<T> toSet();
}
