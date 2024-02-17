package ecs;

public abstract class EcsComponent {
    private EcsObject parent;

    public EcsComponent(EcsObject parent) {
        this.parent = parent;
    }

    public <T extends EcsComponent>T getComponent(Class<T> clazz) {
        return parent.getComponent(clazz);
    }

    public void setParent(EcsObject owner) {
        this.parent = owner;
    }

    public EcsObject getParent() {
        return parent;
    }

}
