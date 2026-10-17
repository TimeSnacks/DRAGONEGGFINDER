# DragonEggFinder

Paper 26.2 plugin targeting Java 25. The GitHub Actions workflow builds a JAR automatically.

## Commands
- `/eggsearch start` — scans generated chunk positions inside each world's world border, checking block containers for a Dragon Egg.
- `/eggsearch stop` — stops the scan.

Only operators have permission by default. It checks at most one generated chunk per tick, so scanning a very large border may take a very long time. It skips chunks that have not been generated; it does not generate new terrain. Back up the world before installing any plugin. This project has not been tested on your exact server.
