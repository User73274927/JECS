package ecs.components;


import ecs.entities.GameObject;

public abstract class GameComponent {
    private GameObject attachedGameObject;

    public GameComponent(GameObject attachedGameObject) {
        this.attachedGameObject = attachedGameObject;
    }

    public <T extends GameComponent>T getComponent(Class<T> clazz) {
        return attachedGameObject.getComponent(clazz);
    }

    public GameObject getAttachedGameObject() {
        return attachedGameObject;
    }

}
