# jenkins basic usage
- maven
- ssh publish
- git

in jenkins home page,click ``Manage Jenkins``
```
- Configure System
Configure global settings and paths.

- Global Tool Configuration
Configure tools, their locations and automatic installers.

- Manage Plugins
Add, remove, disable or enable plugins that can extend the functionality of Jenkins.
```

## Manage Plugins
click: ``Manage Jenkins``->``Manage Plugins``->``Available``

- Maven Integration

search ``Maven Integration`` and tick it. Install without restart.

- Publish Over SSH

search ``Publish Over SSH`` and tick it. Install without restart.

## Configure System
After ``Maven Integration`` and ``Publish Over SSH`` installed , 
go to ``Manage Jenkins``->``Configure System``

in ``Publish over SSH`` section:
```
#SSH Server

Name server0
Hostname 192.168.31.12
Username root
Remote Directory /root

[√] Use password authentication, or use a different key
Passphrase / Password ******
```
click ``Test configuration`` to test if Success.

## Global Tool Configuration

