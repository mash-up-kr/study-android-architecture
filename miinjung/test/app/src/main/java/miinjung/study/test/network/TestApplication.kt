package miinjung.study.test.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApplication :Application() {

    companion object {
        private var api: ServerInterface? = null
        private var instance: TestApplication? = null
        var baseURL =String.format("https://api.github.com")

        fun getInstance() = instance?: TestApplication().apply { instance = this }
    }

    fun buildServerInterface(): ServerInterface? {

        synchronized(TestApplication::class.java) {

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