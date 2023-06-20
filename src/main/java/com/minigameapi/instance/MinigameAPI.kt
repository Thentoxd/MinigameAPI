package com.minigameapi.instance

object MinigameAPI {

    fun getRunnableByKey(key: String): Runnable? {
        for(runnable in runnables) {
            if(runnable.key.equals(key, ignoreCase = true)) {
                return runnable
            }
        }

        return null
    }
}