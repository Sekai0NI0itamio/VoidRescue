# Void Rescue

**Never die in the void.**

The instant a player falls below the bottom of the world, Void Rescue
teleports them back to safety:

1. their **respawn point** — bed or respawn anchor, in its own dimension, or
2. the **world spawn** if they have no respawn point set.

Creative and spectator players are left alone.

## Variants

| Minecraft | Loader | Directory |
|---|---|---|
| 1.12.2 | Forge | `1.12.2-forge/` |
| 1.20.1 | Fabric | `1.20.1-fabric/` |
| 1.20.1 | Forge | `1.20.1-forge/` |
| 1.21.1 | Fabric | `1.21.1-fabric/` |
| 1.21.1 | NeoForge | `1.21.1-neoforge/` |
| 1.21.11 | Fabric | `1.21.11-fabric/` |
| 1.21.11 | NeoForge | `1.21.11-neoforge/` |
| 26.3 | Fabric | `26.3-fabric/` |
| 26.3 | NeoForge | `26.3-neoforge/` |

Every variant is an independent Gradle project; `.github/workflows/build.yml`
builds all of them in a matrix and uploads one artifact per variant.

## Building

```bash
gradle build        # inside any variant directory
# or the whole matrix:
gh workflow run build.yml
```

## License

MIT — see [LICENSE](LICENSE).
