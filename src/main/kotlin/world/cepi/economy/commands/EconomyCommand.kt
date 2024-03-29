package world.cepi.economy.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.entity.EntityFinder
import world.cepi.economy.EconomyHandler
import world.cepi.kepi.command.subcommand.applyHelp
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand

internal object EconomyCommand : Kommand({

    val info = "info".literal()
    val set = "set".literal()
    val add = "add".literal()
    val remove = "remove".literal()

    val amount = ArgumentType.Integer("amount")

    val playerArgument = ArgumentType.Entity("player")
        .onlyPlayers(true)
        .singleEntity(true)
        .setDefaultValue {
            EntityFinder().setTargetSelector(EntityFinder.TargetSelector.SELF)
        }

    syntax(info, playerArgument) {
        sender.sendFormattedTranslatableMessage("economy", "amount",
            Component.text(EconomyHandler[
                    MinecraftServer
                        .getConnectionManager()
                        .getPlayer(context.get(playerArgument).find(sender)[0].uuid)!!
                                         ], NamedTextColor.BLUE)
        )
    }

    syntax(set, amount, playerArgument) {

        val player = context.get(playerArgument).find(sender)[0] as? Player

        if (player == null) {
            sender.sendFormattedTranslatableMessage("common", "target.not_found")
            return@syntax
        }

        EconomyHandler[player] = context.get(amount).toLong()

        sender.sendFormattedTranslatableMessage(
            "economy", "set",
            Component.text(context.get(amount), NamedTextColor.BLUE),
            Component.text(player.username, NamedTextColor.BLUE)
        )
    }

    syntax(remove, amount, playerArgument) {

        val player = context.get(playerArgument).find(sender)[0] as? Player

        if (player == null) {
            sender.sendFormattedTranslatableMessage("common", "target.not_found")
            return@syntax
        }

        EconomyHandler[player] -= context.get(amount).toLong()

        sender.sendFormattedTranslatableMessage(
            "economy", "remove",
            Component.text(context.get(amount), NamedTextColor.BLUE),
            Component.text(player.username, NamedTextColor.BLUE)
        )
    }

    syntax(add, amount, playerArgument) {

        val player = context.get(playerArgument).find(sender)[0] as? Player

        if (player == null) {
            sender.sendFormattedTranslatableMessage("common", "target.not_found")
            return@syntax
        }

        EconomyHandler[player] += context.get(amount).toLong()

        sender.sendFormattedTranslatableMessage(
            "economy", "add",
            Component.text(context.get(amount), NamedTextColor.BLUE),
            Component.text(player.username, NamedTextColor.BLUE)
        )
    }

    applyHelp {
        """
            The economy command is a supertool
            to manage the money of users.
            
            There are four actions: <blue>info, set, add, and remove.
            Set, add, and remove take
            2 paramaters: <blue> (player: selector) and [(amount: number)]
            Example: <yellow>/eco add User 5
            
            The info action allows you to get the money of a user.
            Example: <yellow>eco info User
            The example will return the amount of money they have.
        """.trimIndent()
    }

}, "eco")