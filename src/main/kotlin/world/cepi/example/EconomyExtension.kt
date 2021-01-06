package world.cepi.example

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.example.commands.EcoCommand

class EconomyExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(EcoCommand())
        logger.info("[EconomyExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[EconomyExtension] has been disabled!")
    }

}