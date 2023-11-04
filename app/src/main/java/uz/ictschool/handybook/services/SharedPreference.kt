package uz.ictschool.handybook.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.data.User
import uz.ictschool.handybook.data.UserToken

class SharedPreference private constructor(context: Context){
    val sharedPreferences = context.getSharedPreferences("data", 0)
    val edit = sharedPreferences.edit()
    val gson = Gson()

    companion object{
        private var instance:SharedPreference? = null
        fun newInstance(contexT: Context): SharedPreference {
            if (instance == null){
                instance = SharedPreference(contexT)
            }
            return instance!!
        }
    }

    fun setLoginData(mutableList: MutableList<UserToken>){
        edit.putString("Login", gson.toJson(mutableList)).apply()
    }
    fun getLoginData(): MutableList<UserToken>{
        val data: String = sharedPreferences.getString("Login", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<UserToken>>(){}.type
        return gson.fromJson(data, typeToken)
    }


    fun GetSelectedBooks(): MutableList<Book>{
        val data: String = sharedPreferences.getString("Selected", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<Book>>(){}.type
        return gson.fromJson(data, typeToken)
    }
    fun SetSelectedBooks(mutableList: MutableList<Book>){
        edit.putString("Selected", gson.toJson(mutableList)).apply()
    }

    fun getInProgressBook(): MutableList<Book>{
        val data: String = sharedPreferences.getString("InProgress", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<Book>>(){}.type
        return gson.fromJson(data, typeToken)
    }
    fun setInProgressBook(mutableList: MutableList<Book>){
        edit.putString("InProgress", gson.toJson(mutableList)).apply()
    }

    fun setThisBook(mutableList: MutableList<Book>) {
        edit.putString("audiobook", gson.toJson(mutableList)).apply()
    }

    fun getThisBook() : MutableList<Book> {
        val data: String = sharedPreferences.getString("audiobook", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<Book>>(){}.type
        return gson.fromJson(data, typeToken)
    }
    fun getFinishedBook(): MutableList<Book>{
        val data: String = sharedPreferences.getString("Finished", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<Book>>(){}.type
        return gson.fromJson(data, typeToken)
    }
    fun setFinishedBook(mutableList: MutableList<Book>){
        edit.putString("Finished", gson.toJson(mutableList)).apply()
    }

//    fun setNavBar(mutableList: MutableList<String>){
//        edit.putString("NavBar", gson.toJson(mutableList)).apply()
//    }
//    fun getNavBar(): MutableList<String>{
//        val data: String = sharedPreferences.getString("NavBar", "")!!
//        if (data == ""){
//            return mutableListOf()
//        }
//        val typeToken = object : TypeToken<MutableList<String>>(){}.type
//        return gson.fromJson(data, typeToken)
//    }

}