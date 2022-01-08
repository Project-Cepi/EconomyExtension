package world.cepi.economy

import net.minestom.server.extensions.Extension
import world.cepi.economy.commands.EconomyCommand
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.kstom.util.log

class EconomyExtension : Extension() {

    override fun initialize(): LoadStatus {
        EconomyCommand.register()
        log.info("[EconomyExtension] has been enabled!")

        return LoadStatus.SUCCESS
    }

    override fun terminate() {
        EconomyCommand.unregister()
        log.info("[EconomyExtension] has been disabled!")
    }

}