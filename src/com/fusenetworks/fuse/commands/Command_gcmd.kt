package com.fusenetworks.fuse.commands

import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

// Credit to TF

@CommandPermissions(source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Makes a player execute a command", usage = "/<command>")
class Command_gcmd : BaseCommand() {
    override fun run(sender: CommandSender, sender_p: Player, cmd: Command, commandLabel: String, args: Array<String>, senderIsConsole: Boolean): Boolean {
        if (!sender.hasPermission("fuse.gcmd")) {
            sender.sendMessage(Messages.MSG_NO_PERMS)
            return true
        }
        if (args.size < 2) {
            sender.sendMessage("/gcmd <player> <command>")
            return true
        }

        val player = getPlayer(args[0])

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND)
            return true
        }

        val outCommand = StringUtils.join(args, " ", 1, args.size)

        try {
            sender.sendMessage(ChatColor.GRAY.toString() + "Sending command as " + player.name + ": " + outCommand)
            if (server.dispatchCommand(player, outCommand)) {
                sender.sendMessage(ChatColor.GRAY.toString() + "Command sent.")
            } else {
                sender.sendMessage(ChatColor.GRAY.toString() + "Unknown error sending command.")
            }
        } catch (ex: Throwable) {
            sender.sendMessage(ChatColor.GRAY.toString() + "Error sending command: " + ex.message)
        }

        return true
    }
}
