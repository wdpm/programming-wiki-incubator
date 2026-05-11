package optionals;

import java.util.Optional;

public class Department {
    private String name;
    private Manager boss;

    public Optional<Manager> getBoss() {
        return Optional.ofNullable(boss);
    }

    public void setBoss(Manager boss) {
        this.boss = boss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "boss=" + boss +
                '}';
    }
}
