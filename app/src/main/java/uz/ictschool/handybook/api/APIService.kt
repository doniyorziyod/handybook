package uz.ictschool.handybook.api

import retrofit2.Call
import retrofit2.http.GET
import uz.ictschool.handybook.data.Book

interface APIService {
    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>
}