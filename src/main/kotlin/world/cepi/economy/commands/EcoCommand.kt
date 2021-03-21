package world.cepi.economy.commands

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.subcommands.Help
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

        addSubcommand(Help(
            Component.text("The economy command is a supertool"),
            Component.text("to manage the money of users."),
            Component.space(),
            Component.text("There are four actions: info, ").append(Component.text("set, add, and remove.", NamedTextColor.BLUE)),
            Component.text("Set, add, and remove take"),
            Component.text("2 paramaters: ").append(Component.text("<player: selector> and [<amount: number>]", NamedTextColor.BLUE)),
            Component.text("Example: ").append(Component.text("/eco add User 5", NamedTextColor.YELLOW)),
            Component.space(),
            Component.text("The info action allows you to get the money of a user."),
            Component.text("Example: ").append(Component.text("/eco info User", NamedTextColor.YELLOW)),
            Component.text("The example will return the amount of money they have.")
        ))

    }
}