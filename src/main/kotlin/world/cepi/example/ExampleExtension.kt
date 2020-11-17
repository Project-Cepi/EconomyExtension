package world.cepi.example

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension;
import world.cepi.example.commands.EcoCommand

class ExampleExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
        MinecraftServer.getCommandManager().register(EcoCommand())
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

}