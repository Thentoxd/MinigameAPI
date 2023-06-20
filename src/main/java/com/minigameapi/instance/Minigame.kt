package com.minigameapi.instance

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

abstract class Minigame(protected var plugin: JavaPlugin, private var stage: Stage): Listener {
    val players: MutableList<UUID> = mutableListOf()

    abstract fun start()
    abstract fun reset()

    fun listenForEvents(): Minigame {
        Bukkit.getPluginManager().registerEvents(this, plugin)

        return this
    }

    fun unListenForEvents(): Minigame {
        HandlerList.unregisterAll(this)

        return this
    }

    fun addPlayer(player: Player): Minigame {
        players.add(player.uniqueId)

        log("Player ${player.name} joined.")

        return this
    }

    fun removePlayer(player: Player): Minigame {
        players.remove(player.uniqueId)

        if(players.size <= 1 && stage == Stage.Open) {
            log("Detected too little players, resetting the minigame.")
            reset()
        }

        return this
    }

    protected fun log(message: String) {
        Bukkit.broadcastMessage("${ChatColor.GREEN}[Minigame] ${ChatColor.WHITE}$message")
    }

    protected fun consoleLog(message: String) {
        Bukkit.getConsoleSender().sendMessage("${ChatColor.GREEN}[Minigame] ${ChatColor.WHITE}$message")
    }
}

enum class Stage {
    Open, Development
}