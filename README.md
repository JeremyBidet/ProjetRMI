# ProjetRMI

## Settings
There are some path settings to set before starting the services.
Make sure the two path correlate with your install path:
```java
in Client.java: line 12: the "java.security.policy" match your "all.policy" file path location
in Authentication.java: line 10: the "usersDBPath" path match your "users.db" file path location
```

## Compiling
Clone the repository, then in a terminal, go to the src folder at the root of the project repo.
Type following commands:
```shell
javac *.java
rmic Authentication Park User Comment Vehicle
```
## RMI registry
Make sure that the rmiregistry is reachable from the terminal (just type rmire then autocomplete). If the binary is not found, you can start the service from the java isntall foler (/usr/lib/jvm/jdk.../jre/bin/rmiregistry). You also can make a symlink to it:
```shell
sudo ln -s /usr/lib/jvm/jdk.../jre/bin/rmiregistry /usr/bin/rmiregistry
```
It should be now reachable from anywhere in the terminal.
Start him with the following command:
```shell
rmiregistry &
```
Note the PID to easily kill it when needed.

## Running
Still in the src folder, enter following commands (order matters):
```shell
java Server
```
and in another terminal (same folder):
```shell
java Client
```

