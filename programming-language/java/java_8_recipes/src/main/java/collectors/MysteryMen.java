package collectors;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MysteryMen {
    public static void main(String[] args) {
        Movie mysteryMen = new Movie();
        mysteryMen.addActor(new Actor("Wes Studi", "The Sphinx"));
        mysteryMen.addActor(new Actor("Ben Stiller", "Mr. Furious"));
        mysteryMen.addActor(new Actor("Hank Azaria", "The Blue Raja"));
        mysteryMen.addActor(new Actor("William H. Macy", "The Shoveler"));
        mysteryMen.addActor(new Actor("Janeane Garofalo", "The Bowler"));
        mysteryMen.addActor(new Actor("Kel Mitchell", "Invisible Boy"));
        mysteryMen.addActor(new Actor("Paul Reubens", "The Spleen"));
        mysteryMen.addActor(new Actor("Geoffrey Rush", "Casanova Frankenstein"));
        mysteryMen.addActor(new Actor("Greg Kinnear", "Captain Amazing"));

        Set<Actor> actors = mysteryMen.getActors();

        Map<String, String> actorMap = actors.stream()
                .collect(Collectors.toMap(Actor::getName, Actor::getRole));

        actorMap.forEach((key,value) -> System.out.printf("%s played %s%n", key, value));
    }
}
