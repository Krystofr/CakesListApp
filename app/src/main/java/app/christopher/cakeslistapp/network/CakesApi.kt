package app.christopher.cakeslistapp.network

import app.christopher.cakeslistapp.model.CakesModel
import retrofit2.Response
import retrofit2.http.GET

//Interface for all functions to access the Api
interface CakesApi {

    @GET("waracle_cake-android-client")
    //Return a Response of type JSON
    suspend fun getCakes(): Response<List<CakesModel>>
}