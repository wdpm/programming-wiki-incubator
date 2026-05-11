package collectors;

import java.util.HashSet;
import java.util.Set;

public class Movie {
    private Set<Actor> actors = new HashSet<>();

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Actor> getActors() {
        return actors;
    }
}
