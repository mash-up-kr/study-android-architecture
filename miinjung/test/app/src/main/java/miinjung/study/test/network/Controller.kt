package miinjung.study.test.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Controller :Application() {

    private var api: ServerInterface? = null
    val serverInterface: ServerInterface?
        get() {
            api = buildServerInterface()
            return api
        }

    companion object {
        private var instance: Controller? = null
        var baseURL =String.format("https://api.github.com")

        fun getInstance() = instance?: Controller().apply { instance = this }
    }
//    override fun onCreate() {
//        super.onCreate()
//        Controller.instance = this
//    }

    fun buildServerInterface(): ServerInterface? {

        synchronized(Controller::class.java) {

            if (api == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                api = retrofit.create(ServerInterface::class.java)
            }
        }
        return api
    }
}