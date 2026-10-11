package Events;

import Entidades.Actor;
import Entidades.Player;
import Entidades.Villanos.Villanos;
import Objects.Explosion;
import main.Bomberman;
import main.FloatingText;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EventManager {
    private Bomberman bomberman;
    private ArrayList<Actor> actors;
    private ArrayList<DamageEvent> events;
    private ArrayList<Actor> eventsDamageM=new ArrayList<>();
    private Set<Explosion> explosions = new HashSet<>();
    private ArrayList<FloatingText> floatingTexts = new ArrayList<>();
    private Player player;
    private long lastExpID;
    private boolean firstPlay = true;
    private boolean playMusic = true;

    public EventManager(Player player, Bomberman bomberman) {
        this.actors = new ArrayList<>();
        this.events = new ArrayList<>();
        this.player = player;
        this.bomberman = bomberman;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void flipPlayMusic(){ playMusic^=true; }

    private void checkPlayerExplosion(Explosion explosion) {
        player.getSolidArea().x = (int) (player.getX() + player.getSolidArea().x);
        player.getSolidArea().y = (int) (player.getY() + player.getSolidArea().y);
        explosion.getSolidArea().x = explosion.getX() + explosion.getSolidArea().x;
        explosion.getSolidArea().y = explosion.getY() + explosion.getSolidArea().y;

        if (player.getSolidArea().intersects(explosion.getSolidArea()) && player.getAlive()) {
            if (!explosion.isPlayerDamaged()) {
                if (player.getVidas() == 0) {
                    player.Kill();
                } else {
                    player.removeLife();
                    events.add(new DamageEvent(player));
                }
                explosion.setPlayerDamaged(true);
            }
        }
        player.getSolidArea().x = player.getSolidAreaDefX();
        player.getSolidArea().y = player.getSolidAreaDefY();
        explosion.getSolidArea().x = explosion.getSolidAreaDefX();
        explosion.getSolidArea().y = explosion.getSolidAreaDefY();
    }

    public void checkExplosionCollisions(Explosion explosion) {
        lastExpID = explosion.getID();
        for (Actor actor : actors) {
            actor.getSolidArea().x = (int) (actor.getX() + actor.getSolidArea().x);
            actor.getSolidArea().y = (int) (actor.getY() + actor.getSolidArea().y);
            explosion.getSolidArea().x = explosion.getX() + explosion.getSolidArea().x;
            explosion.getSolidArea().y = explosion.getY() + explosion.getSolidArea().y;
            if (actor.getSolidArea().intersects(explosion.getSolidArea())) {
                if (actor.getAlive() && (lastExpID == explosion.getID())) {
                    Villanos villano = (Villanos) actor;
                    int score = villano.getPuntaje();
                    floatingTexts.add(new FloatingText("+" + score, (float) actor.getX(), (float) (actor.getY() - 20), 500));
                    eventsDamageM.add(actor);
                } else if (actor.getAlive()) {
                    events.add(new DamageEvent(actor));
                }
            }
            actor.getSolidArea().x = actor.getSolidAreaDefX();
            actor.getSolidArea().y = actor.getSolidAreaDefY();
            explosion.getSolidArea().x = explosion.getSolidAreaDefX();
            explosion.getSolidArea().y = explosion.getSolidAreaDefY();
        }
        if (!player.isHELL() && !player.isInmortal()) {
            checkPlayerExplosion(explosion);
        }
    }

    public void checkForSameID() {
        int power = 1;
        for (Actor actor : eventsDamageM) {
            if (actor instanceof Villanos) {
                Villanos villano = (Villanos) actor;
                int nuevoPuntaje = villano.getPuntaje() * power;
                villano.setPuntaje(nuevoPuntaje);
                if(villano.getAlive()) events.add(new DamageEvent(actor));
                power++;
            }
        }
        eventsDamageM.clear();
    }

    public void update() {
        for (Explosion explosion : explosions) {
            explosion.setPlayerDamaged(false);
        }

        for(DamageEvent event : events) {
            event.apply();
            bomberman.getSound().stopX(13);
            bomberman.getSound().playX(13);
        }
        this.events.clear();
        if (player.getVidas() == 0) player.Kill();
    }

    public void updateText(){
        floatingTexts.removeIf(FloatingText::isExpired);
    }

    public void render(Graphics2D g2) {
        for (FloatingText text : floatingTexts) {
            text.render(g2);
        }
    }

    public boolean checkStatusEnemies(){
        boolean allDead = true;
        for (Actor actor : actors) {
            if (actor.getAlive()) {
                allDead = false;
                break;
            }
        }
        if(allDead && firstPlay && playMusic){
            bomberman.getSound().pauseX(1);
            bomberman.getSound().pauseX(4);
            bomberman.getSound().playX(12);
            firstPlay=false;
            bomberman.setResetSoundLevel(System.currentTimeMillis());
        }
        return allDead;
    }

    public void reset(){
        actors.clear();
        events.clear();
        explosions.clear();
        floatingTexts.clear();
        firstPlay=true;
    }

    public ArrayList<Actor> getActors(){
        return this.actors;
    }

    public boolean isFirstPlay() { return firstPlay; }
}
