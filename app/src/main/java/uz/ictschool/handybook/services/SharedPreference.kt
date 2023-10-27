package uz.ictschool.handybook.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.ictschool.handybook.data.User

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

    fun setLoginData(mutableList: MutableList<User>){
        edit.putString("Login", gson.toJson(mutableList)).apply()
    }
    fun getLoginData(): MutableList<User>{
        val data: String = sharedPreferences.getString("Login", "")!!
        if (data == ""){
            return mutableListOf()
        }
        val typeToken = object : TypeToken<MutableList<User>>(){}.type
        return gson.fromJson(data, typeToken)
    }

}