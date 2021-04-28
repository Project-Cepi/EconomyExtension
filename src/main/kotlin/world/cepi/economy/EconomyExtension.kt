package world.cepi.economy

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.economy.commands.EconomyCommand

class EconomyExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(EconomyCommand)
        logger.info("[EconomyExtension] has been enabled!")
    }

    override fun terminate() {
        MinecraftServer.getCommandManager().unregister(EconomyCommand)
        logger.info("[EconomyExtension] has been disabled!")
    }

}