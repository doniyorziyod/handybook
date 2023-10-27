package uz.ictschool.handybook.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.data.User
import uz.ictschool.handybook.data.loginDetails

interface APIService {
    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>

    @POST("/book-api/login")
    fun login(@Body loginDetails: loginDetails): Call<User>
}