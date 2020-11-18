package world.cepi.example.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Arguments
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.Argument
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class EcoCommand : Command("eco") {

    fun getP(name: String): Player? {

       return MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.username.equals(name, true) }
    }

    init {


        val amount = ArgumentType.Integer("amount")
        val moneyType = ArgumentType.Word("moneytype").from("crowns", "shards")
        val playr = ArgumentType.String("player")
        val action = ArgumentType.Word("action").from("give", "take")



        addSyntax({ sender, args ->
            val player = sender as Player
            if (args.getWord("action") == "give") {
                if (args.getWord("moneytype") == "crowns") {
                    getP(args.getWord("player"))?.inventory?.addItemStack(ItemStack(Material.HONEYCOMB, args.getInteger("amount").toByte()))
                } else if (args.getWord("moneytype") == "shards") {
                    player.inventory.addItemStack(ItemStack(Material.PRISMARINE_SHARD, args.getInteger("amount").toByte()))
                }
            }

                

        }, playr, moneyType, amount
        )
    }
}
