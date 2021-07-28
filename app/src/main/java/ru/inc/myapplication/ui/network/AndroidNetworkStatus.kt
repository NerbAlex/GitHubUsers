package ru.inc.myapplication.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.inc.myapplication.extensions.test
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import java.util.logging.Logger

class AndroidNetworkStatus(context: Context) : INetworkStatus {

    val LOG = Logger.getLogger(AndroidNetworkStatus::class.java.name)


    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {

        LOG.test("init")
        statusSubject.onNext(false)

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    LOG.test("onAvailable")

                    statusSubject.onNext(true)
                }

                override fun onLost(network: Network) {
                    LOG.test("onLost")

                    statusSubject.onNext(false)
                }
            })

    }

    override fun isOnline() = statusSubject

    override fun isOnlineSingle() = statusSubject.first(false)
}