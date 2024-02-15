package ecs;


public abstract class EcsComponent {
    private EcsObject owner;

    public EcsComponent(EcsObject attachedEcsObject) {
        this.owner = attachedEcsObject;
    }

    public <T extends EcsComponent>T getComponent(Class<T> clazz) {
        return owner.getComponent(clazz);
    }

    public void setOwner(EcsObject owner) {
        this.owner = owner;
    }

    public EcsObject getOwner() {
        return owner;
    }

}
