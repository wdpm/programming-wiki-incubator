# Java version and new features

> origin: https://howtodoinjava.com/java-version-wise-features-history/ 

## Java 12

- Collectors.teeing() in Stream API
- String API Changes
- Files.mismatch(Path, Path)
- Compact Number Formatting
- Support for Unicode 11
- Switch Expressions (Preview)

## Java 11

- HTTP Client API
- Launch Single-File Programs Without Compilation
- String API Changes
- Collection.toArray(IntFunction)
- Files.readString() and Files.writeString()
- Optional.isEmpty()

## Java 10

- JEP 286: Local Variable Type Inference
- JEP 322: Time-Based Release Versioning
- JEP 304: Garbage-Collector Interface
- JEP 307: Parallel Full GC for G1
- JEP 316: Heap Allocation on Alternative Memory Devices
- JEP 296: Consolidate the JDK Forest into a Single Repository
- JEP 310: Application Class-Data Sharing
- JEP 314: Additional Unicode Language-Tag Extensions
- JEP 319: Root Certificates
- JEP 317: Experimental Java-Based JIT Compiler
- JEP 312: Thread-Local Handshakes
- JEP 313: Remove the Native-Header Generation Tool
- New Added APIs and Options
- Removed APIs and Options

## Java 9

- Java platform module system
- Interface Private Methods
- HTTP 2 Client
- JShell – REPL Tool
- Platform and JVM Logging
- Process API Updates
- Collection API Updates
- Improvements in Stream API
- Multi-Release JAR Files
- @Deprecated Tag Changes
- Stack Walking
- Java Docs Updates
- Miscellaneous Other Features

## Java 8

  - **Lambda expression** 
  - **Stream API**
  - **Functional interface and default methods**
  - Optionals
  - Nashorn – JavaScript runtime which allows developers to embed JavaScript code within applications
  - Annotation on Java Types
  - Unsigned Integer Arithmetic
  - Repeating annotations
  - **New Date and Time API**
  - Statically-linked JNI libraries
  - Launch JavaFX applications from jar files
  - **Remove the permanent generation from GC**

## Java SE 7 

- JVM support for dynamic languages

- Compressed 64-bit pointers

- **Strings in switch**

- **Automatic resource management in try-statement**

- **The diamond operator**

- Simplified varargs method declaration

- Binary integer literals

- **Underscores in numeric literals**

  ```java
  private final int size = 1_000_000;
  ```

- Improved exception handling

- ForkJoin Framework

- NIO 2.0 having support for multiple file systems, file metadata and symbolic links

- WatchService

- Timsort is used to sort collections and arrays of objects instead of merge sort

- APIs for the graphics features

- Support for new network protocols, including SCTP and Sockets Direct Protocol