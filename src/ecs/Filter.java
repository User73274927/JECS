package ecs;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface Filter<T extends Filter<T, R>, R> {
    T filter(Predicate<R> func);
    T apply();
    List<R> toList();
}
