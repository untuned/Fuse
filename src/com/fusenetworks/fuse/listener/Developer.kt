package com.fusenetworks.fuse.listener

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object Developer : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent): Boolean {
        val dev =  Bukkit.getPluginManager().getPlugin("Fuse").getConfig().getString("server.dev")
        val player = event.player
        if (dev == "true") {
            player.sendMessage(ChatColor.DARK_AQUA.toString() + "Warning: The server is currently in development mode. "
                    + "This means there may be unstable plugin builds on this server, and the server could crash more than normal!")
            return true
        }
        return true
    }

}