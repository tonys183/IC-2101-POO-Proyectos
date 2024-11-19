package Events;

import Entidades.Actor;
import Objects.SuperObject;

public class DamageEvent {
    private Actor actorTarget;
    private SuperObject objectTarget;

    public DamageEvent(Actor actor) {
        this.actorTarget = actor;
        this.objectTarget = null;
    }

    public DamageEvent(SuperObject object) {
        this.actorTarget = null;
        this.objectTarget = object;
    }

    public void apply() {
        actorTarget.setAlive(false);
        actorTarget.setDeathTime(System.currentTimeMillis());
    }

    public Actor getActorTarget() {
        return actorTarget;
    }

    public SuperObject getObjectTarget() {
        return objectTarget;
    }
}

