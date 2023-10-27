package uz.ictschool.handybook.data

data class User(
    var username: String,
    var fullname: String,
    var email: String,
    val password: String
)
