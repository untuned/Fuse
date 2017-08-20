package com.fusenetworks.fuse

import com.fusenetworks.fuse.listener.*
import com.fusenetworks.fuse.commands.CMD_Handler
import com.fusenetworks.fuse.commands.CMD_Loader
import com.fusenetworks.fuse.util.NLog
import com.fusenetworks.fuse.util.NUtil
import java.io.File
import java.io.IOException
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.mcstats.Metrics

class Fuse : JavaPlugin() {
    internal var jarFile = this.file

    override fun onLoad() {
        Fuse.plugin = this
        Fuse.server = plugin.server
        NLog.setServerLogger(server.logger)
        NLog.setServerLogger(server.logger)
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(AutoUpdate(), Fuse.plugin)
        server.pluginManager.registerEvents(CommandBlocker(), Fuse.plugin)
        server.pluginManager.registerEvents(Commandspy(), Fuse.plugin)
        server.pluginManager.registerEvents(ConfigListener(), Fuse.plugin)
        server.pluginManager.registerEvents(Developer(), Fuse.plugin)
        server.pluginManager.registerEvents(Launchpads(), Fuse.plugin)
        server.pluginManager.registerEvents(LoginMessages(), Fuse.plugin)
        server.pluginManager.registerEvents(NoFall(), Fuse.plugin)
        server.pluginManager.registerEvents(NoHunger(), Fuse.plugin)
        server.pluginManager.registerEvents(PotionListener(), Fuse.plugin)
        server.pluginManager.registerEvents(SignPatch(), Fuse.plugin)
        Config.loadConfigs()
        object : BukkitRunnable() {
            override fun run() {
                CMD_Loader.commandMap
                CMD_Loader.scan()
            }
        }.runTaskLater(plugin, 20L)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Fuse"), {
            if (NUtil.NEntityWiper.wipeEntities(true, true) == 1) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entity removed")
            } else if (NUtil.NEntityWiper.wipeEntities(true, true) != 0) {
                NLog.info(NUtil.NEntityWiper.wipeEntities(true, true).toString() + " entities removed")
            }
        }, 1L, 300.toLong() * 20)
        instance = this

        try {
            val metrics = Metrics(this)
            metrics.start()
        } catch (e: IOException) {
            // Failed to submit the stats :-(
        }

    }

    override fun onDisable() {}

    override fun onCommand(sender: CommandSender?, cmd: Command?, commandLabel: String?, args: Array<String>?): Boolean {
        return CMD_Handler.handleCommand(sender, cmd, commandLabel, args)
    }

    companion object {

        var plugin: Fuse
        var server: Server
        var instance: Fuse


        var buildDate = "8/15/17"
        var buildCreator = "Telesphoreo"
    }
}