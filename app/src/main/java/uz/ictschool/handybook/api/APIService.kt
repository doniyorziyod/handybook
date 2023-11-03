package uz.ictschool.handybook.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.data.CategoryData
import uz.ictschool.handybook.data.User
import uz.ictschool.handybook.data.LoginDetails
import uz.ictschool.handybook.data.UserToken


interface APIService {
    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>

    @POST("/book-api/login")
    fun login(@Body loginDetails: LoginDetails): Call<UserToken>

    @POST("/book-api/register")
    fun register(@Body user: User): Call<UserToken>

    @GET("/book-api/all-category")
    fun getAllCategory():Call<List<CategoryData>>

    @GET("/book-api/category")
    fun getBookByCategory(@Query("name")name: String):Call<List<Book>>

    @GET("/book-api/search-name")
    fun search(@Query("name") name: String):Call<List<Book>>

    @GET("/book-api/main-book")
    fun getMainBook():Call<Book>

    @GET("/book-api/comment")
    fun getAllComments(@Query("id") id: Int):Call<List<uz.ictschool.handybook.data.Comment>>


    @POST("/comment-api/create")
    fun giveCommentToTheBook(@Body commentData: uz.ictschool.handybook.data.CommentDataOrigin):Call<uz.ictschool.handybook.data.CommentData>



}