package world.cepi.economy.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kstom.addSyntax
import world.cepi.kstom.arguments.asSubcommand

class EcoCommand : Command("eco") {

    companion object {
        val map: MutableMap<Player, Int> = mutableMapOf()
    }

    fun getP(name: String): Player? {
        return MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.username.equals(name, true) }
    }

    init {

        val info = "info".asSubcommand()
        val set = "set".asSubcommand()
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()

        val amount = ArgumentType.Integer("amount")
        val playerArgument = ArgumentType.DynamicWord("player").fromRestrictions { name -> MinecraftServer.getConnectionManager().onlinePlayers.any { it.username.equals(name, true) } }

        addSyntax(info, playerArgument) { sender, args ->
            sender.sendMessage("Amount: ${map.getOrDefault(getP(args.get(playerArgument)), 0)}")
        }

        addSyntax(set, playerArgument, amount) { sender, args ->

            val player = getP(args.get(playerArgument))
            if (player == null) {
                sender.sendMessage("That player does not exist!")
                return@addSyntax
            }

            map[player] = args.get(amount)
        }

        addSyntax(remove, playerArgument, amount) { sender, args ->

            val player = getP(args.get(playerArgument))
            if (player == null) {
                sender.sendMessage("That player does not exist!")
                return@addSyntax
            }

            playerCheck(player)

            map[player] = map[player]!!.minus(args.get(amount))
        }

        addSyntax(add, playerArgument, amount) { sender, args ->

            val player = getP(args.get(playerArgument))
            if (player == null) {
                sender.sendMessage("That player does not exist!")
                return@addSyntax
            }

            playerCheck(player)

            map[player] = map[player]!!.plus(args.get(amount))
        }


    }

    fun playerCheck(player: Player) {
        if (map[player] == null) map[player] = 0
    }
}