package miinjung.study.test.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object TestApplication{
    private val baseURL =String.format("https://api.github.com")

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api : ServerInterface = retrofit.create(ServerInterface::class.java)
}
