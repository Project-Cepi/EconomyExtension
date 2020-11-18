package world.cepi.example.commands

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material

class EcoCommand : Command("eco") {
    fun getP(name: String): Player? {
        return MinecraftServer.getConnectionManager().onlinePlayers.firstOrNull { it.username.equals(name, true) }
    }
//add permission later
    init {
        setDefaultExecutor {sender, _ ->
            sender.sendMessage("Usage: /eco <player> <crowns|shards> <amount>")
        }


        val amount = ArgumentType.Integer("amount").max(64).min(1)
        val moneyType = ArgumentType.Word("type").from("crowns", "shards")
        val targ = ArgumentType.Word("player")
        val actionn = ArgumentType.Word("action").from("give", "take")

        addSyntax({sender, args ->
            val player = sender as Player
            if (args.getWord("action") == "give"){
                if (args.getWord("type") == "crowns") {
                    getP(args.getWord("player"))?.inventory?.addItemStack(ItemStack(Material.HONEYCOMB, args.getInteger("amount").toByte()))
                    player.sendMessage("${ChatColor.GRAY} [${ChatColor.BRIGHT_GREEN}+${ChatColor.GRAY}]${ChatColor.DARK_GRAY} Gave ${args.getInteger("amount")} Crowns to ${args.getWord("player")}")
                    if (getP(args.getWord("player"))!== player ) {
                        getP(args.getWord("player"))?.sendMessage("${ChatColor.GRAY} [${ChatColor.BRIGHT_GREEN}+${ChatColor.GRAY}]${ChatColor.DARK_GRAY} You were given ${args.getInteger("amount")} Crowns by $player")

                    }
                } else if (args.getWord("type") == "shards") {
                    getP(args.getWord("player"))?.inventory?.addItemStack(ItemStack(Material.PRISMARINE_SHARD, args.getInteger("amount").toByte()))
                    player.sendMessage("${ChatColor.GRAY} [${ChatColor.BRIGHT_GREEN}+${ChatColor.GRAY}]${ChatColor.DARK_GRAY} Gave ${args.getInteger("amount")} Shards to ${args.getWord("player")}")
                    if (getP(args.getWord("player"))!== player ) {
                        getP(args.getWord("player"))?.sendMessage("${ChatColor.GRAY} [${ChatColor.BRIGHT_GREEN}+${ChatColor.GRAY}]${ChatColor.DARK_GRAY} You were given ${args.getInteger("amount")} Shards by $player")
                    }
                }
            } else if (args.getWord("action") == "take") {
                player.sendMessage("${ChatColor.GRAY} [${ChatColor.RED}+${ChatColor.GRAY}]${ChatColor.DARK_GRAY} This is not implemented yet")
            }
        },targ, actionn, moneyType, amount)

    }
}