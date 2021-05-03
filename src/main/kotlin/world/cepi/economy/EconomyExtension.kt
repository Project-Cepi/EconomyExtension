package world.cepi.economy

import net.minestom.server.extensions.Extension
import world.cepi.economy.commands.EconomyCommand
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister

class EconomyExtension : Extension() {

    override fun initialize() {
        EconomyCommand.register()
        logger.info("[EconomyExtension] has been enabled!")
    }

    override fun terminate() {
        EconomyCommand.unregister()
        logger.info("[EconomyExtension] has been disabled!")
    }

}