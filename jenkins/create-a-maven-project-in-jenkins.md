# create a maven project in jenkins

In jenkins home page, click ``New Item``, then select building a maven project and enter item name.

It has many sections.

## General
Enter ``Description`` is enough.

## Source Code Management
Tick ``Git``.
- Repositories
```
Repository URL ... #step 1, enter repo url
Credentials ADD #step 2,click ADD -> jenkins
```
enter ``username``  and ``password``.

- Branches to build
Branch Specifier
```
(blank for 'any') */master
```

## Build Triggers
[√] ``Build whenever a SNAPSHOT dependency is built``

## Build
```
Root POM: pom.xml
Goals and options: clean package
```

## Post Steps
Tick ``Run only if build succeeds or is unstable ``.

Click ``Add post-build step``->``Send files or execute commands over SSH``:
```bash
Name: server0

Transfer Set
Source files: **/*.jar
Remove prefix: 
Remote directory: /root/blog
Exec command: /root/blog/register-start.sh
```

[register.sh](./register.sh register.sh)
