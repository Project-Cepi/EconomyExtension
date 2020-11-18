package world.cepi.example.commands

import net.minestom.server.chat.ChatColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class EcoCommand : Command("eco") {

    init {
        setDefaultExecutor {sender, _ ->
            sender.sendMessage("Usage: /eco <player> <reason>")
        }

        val amount = ArgumentType.Integer("amount").max(64).min(1)
        val moneyType = ArgumentType.Word("type").from("crowns", "shards")
        val targ = ArgumentType.Word("player")

        addSyntax({sender, args ->
            val player = sender as Player
            if (args.getWord("type") == "crowns") {
                player.inventory.addItemStack(ItemStack(Material.HONEYCOMB, args.getInteger("amount").toByte()))
                player.sendMessage("${ChatColor.BRIGHT_GREEN} + Gave Money")
            }
        }, moneyType, amount)

    }
}