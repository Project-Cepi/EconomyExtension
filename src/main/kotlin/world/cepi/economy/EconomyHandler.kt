package world.cepi.economy

import it.unimi.dsi.fastutil.objects.Object2LongMap
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import net.minestom.server.entity.Player
import java.util.*

object EconomyHandler {

    private val economy: Object2LongMap<UUID> = Object2LongOpenHashMap()

    operator fun set(player: Player, amount: Long) {
        economy[player.uuid] = amount
    }

    operator fun get(player: Player): Long {
        return economy.getLong(player.uuid)
    }

}