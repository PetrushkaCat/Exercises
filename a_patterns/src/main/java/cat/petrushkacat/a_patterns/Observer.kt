package cat.petrushkacat.a_patterns

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

interface Observer {
    fun update(weather: Int)
}

object WeatherSource {
    private val observers: MutableList<Observer> = mutableListOf()
    private var weather: Int = 0
    var job = Job()

    private fun notifyAll(weather: Int) {
        observers.forEach {
            Log.d("observer", it.toString())
            it.update(weather)
        }
    }

    fun addObserver(observer: Observer) {
        observers.add(observer)
        Log.d("observer", observer.toString())
        if(observers.size == 1) {
            job = Job()
            CoroutineScope(job + Dispatchers.Default).launch {
                startGeneratingWeather()
            }
        }
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
           if(observers.isEmpty()) {
                job.cancel()
           }

    }

    private suspend fun startGeneratingWeather() {
        while (observers.size > 0 && job.isActive) {
            delay(5000)
            weather = (Date().time % 35).toInt()
            notifyAll(weather)
        }
    }
}