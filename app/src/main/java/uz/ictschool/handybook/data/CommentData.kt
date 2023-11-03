package uz.ictschool.handybook.data

data class CommentData (
    val book_id: String,
    val id: Int,
    val reyting: String,
    val text: String,
    val user_id: String
)