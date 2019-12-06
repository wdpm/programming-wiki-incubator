# create a maven project in jenkins

In jenkins home page, click ``New Item``, then select building a maven project and enter item name.

It has many sections.

## Setup jenkins config

### General
Enter ``Description`` register.

### Source Code Management
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

- Additional Behaviours

Click ``Sparse Checkout paths``
```
Path: blog
```

### Build Triggers
[√] ``Build whenever a SNAPSHOT dependency is built``

### Build
```
Root POM: blog/pom.xml
Goals and options: clean package -X -pl register -am
```

### Post Steps
Tick ``Run only if build succeeds``.

Click ``Add post-build step``->``Send files or execute commands over SSH``:
```bash
Name: server0

Transfer Set
Source files: blog/register/target/*.jar
Remove prefix: blog/register/target/
Remote directory: blog
Exec command: /app/blog/register.sh start
```
> you should upload ``register.sh`` to ``/app/blog/`` in remote server and run ``chmod +x register.sh`` .

[register.sh](./register.sh register.sh)

## Test
first run this jenkins job:
```bash
SSH: Connecting from host [vmware0]
SSH: Connecting with configuration [server0] ...
SSH: EXEC: STDOUT/STDERR from command [/app/blog/register.sh start] ...
staring /app/blog/register.jar. 
(pid=68308) is ok.
SSH: EXEC: completed after 601 ms
SSH: Disconnecting configuration [server0] ...
SSH: Transferred 1 file(s)
Finished: SUCCESS
```

second run this jenkins job:
```bash
SSH: Connecting from host [vmware0]
SSH: Connecting with configuration [server0] ...
SSH: EXEC: STDOUT/STDERR from command [/app/blog/register.sh start] ...
========================
warning: /app/blog/register.jar is running.(pid=68308)
========================
Now restart...
Stop /app/blog/register.jar ok.
staring /app/blog/register.jar.
(pid=70937) is ok.
SSH: EXEC: completed after 1,002 ms
SSH: Disconnecting configuration [server0] ...
SSH: Transferred 1 file(s)
Finished: SUCCESS
```