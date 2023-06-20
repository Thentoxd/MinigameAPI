package com.minigameapi.instance

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

internal val runnables: MutableList<Runnable> = mutableListOf()

abstract class Runnable(val key: String, var timer: Int) : BukkitRunnable() {
    var pause = false

    init {
        runnables.add(this)
    }

    override fun cancel() {
        runnables.remove(this)
        Bukkit.getScheduler().cancelTask(taskId)
    }
}