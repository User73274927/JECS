package ecs.entities;

import ecs.components.GameComponent;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    //Components
    private final List<Class<? extends GameComponent>> componentTypes = new ArrayList<>();
    private final List<GameComponent> components = new ArrayList<>();
    private boolean isActive;
    private int id;

    public GameObject() {

    }

    @SuppressWarnings("unchecked")
    public <T extends GameComponent>T getComponent(Class<T> clazz) {
        for (GameComponent component : components) {
            if (clazz.isAssignableFrom(component.getClass())) {
                return (T) component;
            }
        }
        return null;
    }

    public void addComponent(GameComponent new_component) {
        int i = 0;
        for (GameComponent component : components) {
            if (new_component.getClass() == component.getClass()) {
                components.set(i, new_component);
                return;
            }
            i++;
        }
        if (!componentTypes.contains(new_component.getClass())) {
            componentTypes.add(new_component.getClass());
        }
        components.add(new_component);
    }

    public void removeComponent(Class<? extends GameComponent> clazz) {
        GameComponent toRemove = null;
        for (GameComponent component : components) {
            if (component.getClass() == clazz) {
                toRemove = component;
                break;
            }
        }
        components.remove(toRemove);
    }

    public List<GameComponent> getComponents() {
        return components;
    }
    public List<Class<? extends GameComponent>> getComponentTypes() {
        return componentTypes;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void onEnable() {}
    public void onDisable() {}

}
