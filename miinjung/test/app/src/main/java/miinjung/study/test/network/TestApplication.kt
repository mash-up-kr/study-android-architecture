package miinjung.study.test.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TestApplication{
    var api : ServerInterface? = null
    val baseURL =String.format("https://api.github.com")

    init{
        synchronized(TestApplication::class.java) {

            if (api == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                api = retrofit.create(ServerInterface::class.java)
            }
        }
    }
}
