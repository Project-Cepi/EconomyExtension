package world.cepi.economy.commands

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.asSubcommand

class EcoCommand : Command("eco") {

    companion object {
        val economy: Object2IntMap<Player> = Object2IntOpenHashMap()
    }

    init {

        val info = "info".asSubcommand()
        val set = "set".asSubcommand()
        val add = "add".asSubcommand()
        val remove = "remove".asSubcommand()

        val amount = ArgumentType.Integer("amount")
        val playerArgument = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true)

        addSyntax(info, playerArgument) { sender, args ->
            sender.sendFormattedMessage(ecoAmount, Component.text(economy.getInt(args.get(playerArgument).find(sender)[0]), NamedTextColor.BLUE))
        }

        addSyntax(set, playerArgument, amount) { sender, args ->

            val player = args.get(playerArgument).find(sender)[0] as? Player

            if (player == null) {
                sender.sendFormattedMessage(ecoPlayerNotExist)
                return@addSyntax
            }

            economy[player] = args.get(amount)

            sender.sendFormattedMessage(
                ecoSetAmount,
                Component.text(args.get(amount), NamedTextColor.BLUE),
                Component.text(player.username, NamedTextColor.BLUE)
            )
        }

        addSyntax(remove, playerArgument, amount) { sender, args ->

            val player = args.get(playerArgument).find(sender)[0] as? Player

            if (player == null) {
                sender.sendFormattedMessage(ecoPlayerNotExist)
                return@addSyntax
            }

            economy[player] = economy.getInt(player) - args.get(amount)

            sender.sendFormattedMessage(
                ecoRemoveAmount,
                Component.text(args.get(amount), NamedTextColor.BLUE),
                Component.text(player.username, NamedTextColor.BLUE)
            )
        }

        addSyntax(add, playerArgument, amount) { sender, args ->

            val player = args.get(playerArgument).find(sender)[0] as? Player
            
            if (player == null) {
                sender.sendFormattedMessage(ecoPlayerNotExist)
                return@addSyntax
            }

            economy[player] = economy.getInt(player) + args.get(amount)

            sender.sendFormattedMessage(
                ecoAddAmount,
                Component.text(args.get(amount), NamedTextColor.BLUE),
                Component.text(player.username, NamedTextColor.BLUE)
            )
        }


    }
}