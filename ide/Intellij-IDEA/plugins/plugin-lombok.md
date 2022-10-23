# lombok plugin

## setup

https://projectlombok.org/setup/intellij

IDEA -> ``Settings`` -> ``Annotation Processors``, enable it.

## basic usage
```java
public class User {
    public Long id;
    private String username;
    private String password;
}
```

- @Getter/@Setter
- @Accessors(chain = true)
```java
@Getter
@Setter
@Accessors(chain = true)
public class User {
    public Long id;

    private String username;
    private String password;

    public static void main(String[] args) {
        final User user2 = new User();
        user2.setId().setUsername().setPassword();
    }

}
```
- @Builder
```java
@Builder
public class User {
    public Long id;
    private String username;
    private String password;
}
```
compiled User class
```java
public class User {
    public Long id;
    private String username;
    private String password;

    User(final Long id, final String username, final String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;

        UserBuilder() {
        }

        public User.UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public User.UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this.id, this.username, this.password);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password + ")";
        }
    }
}
```
visit User builder from outside.
```java
final User user = User.builder()
        .id(1L)
        .username("user1")
        .password("pwd1")
        .build();
```
- @EqualsAndHashCode
- @ToString
 
It will generate equals, toString, hashCode method.
- @NoArgsConstructor
- @AllArgsConstructor(access = AccessLevel.PRIVATE)
```java
public User() {
}

private User(final Long id, final String username, final String password) {
    this.id = id;
    this.username = username;
    this.password = password;
}
```

## go further
https://projectlombok.org/