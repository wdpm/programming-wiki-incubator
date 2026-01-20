# JPA Projection by Interface

Aim to create projections based on certain attributes of those types.

```java
class Person {

  @Id UUID id;
  String firstname, lastname;
  Address address;

  static class Address {
    String zipCode, city, street;
  }
}

interface PersonRepository extends Repository<Person, UUID> {

  Collection<Person> findByLastname(String lastname);
}
```

 Now imagine that we want to retrieve the person’s name attributes only.

## Interface-based Projections

```java
interface NamesOnly {
  String getFirstname();
  String getLastname();
}
```

```java
interface PersonRepository extends Repository<Person, UUID> {
  Collection<NamesOnly> findByLastname(String lastname);
}
```

The query execution engine creates proxy instances of that interface at runtime for each element returned and forwards calls to the exposed methods to the target object. 

```
NamesOnly.getFirstname() => Person.getFirstname()
```

### Recursive Projections

```java
interface PersonSummary {

  String getFirstname();
  String getLastname();
  AddressSummary getAddress();

  interface AddressSummary {
    String getCity();
  }
}
```

###  **Closed Projections** 

A projection interface whose accessor methods all match properties of the target aggregate is considered to be a closed projection.

```java
interface NamesOnly {
  String getFirstname();
  String getLastname();
}
```

###  **Open Projections** 

Accessor methods in projection interfaces can also be used to compute new values by using the `@Value` annotation.

```java
interface NamesOnly {

  @Value("#{target.firstname + ' ' + target.lastname}")
  String getFullName();
  ...
}
```

More flexible.

```java
@Component
class MyBean {
  String getFullName(Person person) {
    …
  }
}

interface NamesOnly {
  @Value("#{@myBean.getFullName(target)}")
  String getFullName();
  …
}
```

Methods backed by SpEL expression evaluation can also use method parameters.

```java
interface NamesOnly {

  @Value("#{args[0] + ' ' + target.firstname + '!'}")
  String getSalutation(String prefix);
}
```

## Class-based Projections (DTOs)

```java
class NamesOnly {

  private final String firstname, lastname;

  NamesOnly(String firstname, String lastname) {

    this.firstname = firstname;
    this.lastname = lastname;
  }

  String getFirstname() {
    return this.firstname;
  }

  String getLastname() {
    return this.lastname;
  }

  // equals(…) and hashCode() implementations
}
```

> You can dramatically simplify the code for a DTO by using [Project Lombok](https://projectlombok.org/), which provides an `@Value` annotation 

## Dynamic Projections

```java
interface PersonRepository extends Repository<Person, UUID> {
  <T> Collection<T> findByLastname(String lastname, Class<T> type);
}
```

Usage

```java
void someMethod(PersonRepository people) {

  Collection<Person> aggregates =
    people.findByLastname("Matthews", Person.class);

  Collection<NamesOnly> aggregates =
    people.findByLastname("Matthews", NamesOnly.class);
}
```

