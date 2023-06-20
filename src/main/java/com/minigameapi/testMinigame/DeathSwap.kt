package com.minigameapi.testMinigame

import com.minigameapi.instance.Minigame
import com.minigameapi.instance.Runnable
import com.minigameapi.instance.Stage
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin

class DeathSwap(plugin: JavaPlugin) : Minigame(plugin, Stage.Development) {

    override fun start() {
        if(players.size < 2) {
            log("Cannot start game. Not sufficient players!")
            return
        } else if(players.size % 2 == 0) {
            log("Cannot start game. Imbalance of players: Requires a number divisible by 2!")
            return
        }

        listenForEvents()

        object : Runnable("death.swap_mainTimer", (2 * 60)) {
            override fun run() {
                when(timer) {
                    in 10 downTo 1 -> {
                        log("${ChatColor.RED}Swapping in $timer seconds!")
                    }

                    0 -> {
                        swapPlayers()
                        timer = (2 * 60)
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20)
    }

    override fun reset() {
        unListenForEvents()
    }

    fun swapPlayers() {
        for((index, uuid) in players.withIndex()) {
            val playerOne = Bukkit.getPlayer(uuid)!!

            if(index % 2 == 0) {
                val playerTwo = Bukkit.getPlayer(players[index])!!
                swap(playerOne, playerTwo)
            }
        }

        players.shuffle() // This randomizes the list - So the same players don't teleport to the other same players each time
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity

        if(players.contains(player.uniqueId)) {
            log("${player.name} died!")
            event.deathMessage = null

            reset()
        }
    }

    fun swap(playerOne: Player, playerTwo: Player) {
        val location = playerOne.location

        playerOne.teleport(playerTwo.location)
        playerTwo.teleport(location)
    }
}