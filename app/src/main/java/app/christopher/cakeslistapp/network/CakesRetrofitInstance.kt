package app.christopher.cakeslistapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CakesRetrofitInstance {

    //By lazy, means it won't be initialized right away, but when we first access the API
    val API: CakesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/")
            .addConverterFactory(GsonConverterFactory.create()) //Retrofit needs to know how it takes the JSON data it gets and how it should parse that to the data class we created
            .build()
            .create(CakesApi::class.java)
    }
}