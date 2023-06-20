package com.minigameapi

import com.minigameapi.testMinigame.DeathSwap
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class MinigamePlugin : JavaPlugin(), CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name.equals("start", ignoreCase = true)) {
            DeathSwap(this).addPlayer(Bukkit.getPlayer("Thento")!!).start()
        }

        return false
    }
}
