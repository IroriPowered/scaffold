# scaffold
A barebones Discord chat bridge plugin for Hytale servers. Join/quit messages and chat are automatically forwarded to the specified Discord channel, and user messages sent in the channel are forwarded to the in-game.

## what scaffold is
- A simple plugin to serve the gap between the launch of Hytale, and the creation of a sophisticated Discord chat bridge plugin

## what scaffold isn't
- A customization-heavy plugin with a lot of features such as responding to certain game events and a system for more complicated server setups
  - (I hope that a more complete Discord bridge plugin will be written by developers better than me)

# building
Java 25 is recommended. Use the following command to compile the scaffold plugin:
```sh
./gradlew build
```
