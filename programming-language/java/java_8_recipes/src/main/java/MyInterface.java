@FunctionalInterface
public interface MyInterface {
    int myMethod();
    // int myOtherMethod();

    default String sayHello() {
        return "Hello, World";
    }

    static void myStaticMethod() {
        System.out.println("I'm a static method in an interface");
    }
}
