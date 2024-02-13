package ecs;

import ecs.entities.GameObject;
import ecs.components.GameComponent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GameObjectAllocator implements EcsAllocator<GameObject, GameComponent> {
    private HashMap<Class<? extends GameComponent>, Set<GameComponent>> sortedComponents;

    public GameObjectAllocator() {
        this.sortedComponents = new HashMap<>();
    }

    @Override
    public void allocate(GameObject object) {
        for (GameComponent gameComponent : object.getComponents()) {
            Class<? extends GameComponent> clazz = gameComponent.getClass();

            if (!sortedComponents.containsKey(clazz)) {
                sortedComponents.put(clazz, new HashSet<>());
            }
            sortedComponents.get(clazz).add(gameComponent);
        }
    }

    @Override
    public void remove(GameObject object) {
        for (GameComponent gameComponent : object.getComponents()) {
            if (!sortedComponents.containsKey(gameComponent.getClass())) continue;
            sortedComponents.get(gameComponent.getClass()).remove(gameComponent);
        }
    }

    @Override
    public HashMap<Class<? extends GameComponent>, Set<GameComponent>> getSortedItems() {
        return sortedComponents;
    }
}
