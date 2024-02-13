package ecs.renderer;

import ecs.components.GameComponent;
import ecs.entities.GameObject;

public class Renderer extends GameComponent {
    public Renderer(GameObject attachedGameObject) {
        super(attachedGameObject);
    }
}
