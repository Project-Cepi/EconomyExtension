package world.cepi.economy

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.economy.commands.EcoCommand

class EconomyExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(EcoCommand)
        logger.info("[EconomyExtension] has been enabled!")
    }

    override fun terminate() {
        MinecraftServer.getCommandManager().unregister(EcoCommand)
        logger.info("[EconomyExtension] has been disabled!")
    }

}