package world.cepi.example.commands

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class EcoCommand : Command("eco") {
    init {
        val amount = ArgumentType.Integer("amount").max(1).min(64)
        val moneyType = ArgumentType.Word("type").from("crowns", "shards")
        setCondition { sender, _ ->
            if (!sender.isPlayer) {
                sender.sendMessage("Only players can use this command!")
                return@setCondition false
            } else return@setCondition true
        }

        addSyntax({ sender: CommandSender, args: Arguments ->
            val player = sender as Player
            player.inventory.addItemStack(ItemStack(Material.HONEYCOMB, args.getInteger("amount").toByte()))
        }
        )
    }
}
