package uz.ictschool.handybook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book
import uz.ictschool.handybook.services.SharedPreference



class CustomBooksList(var list: MutableList<Book>, var onClick: OnClick, var onBosildi: OnBosildi): RecyclerView.Adapter<CustomBooksList.MyViewHolder>(){
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var rasm = view.findViewById<ImageView>(R.id.custom_book_image)
        var title = view.findViewById<TextView>(R.id.name_home)
        var authir = view.findViewById<TextView>(R.id.name_author_home)
        var saved_ic = view.findViewById<ImageView>(R.id.custom_selected)
        var rating = view.findViewById<TextView>(R.id.rating_custom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_poem_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val a = list[position]
        holder.rasm.load(a.image)
        holder.authir.text = a.author
        holder.title.text = a.name
        holder.rating.text = a.reyting.toString()
        if (a.book_progress == 0){
            holder.saved_ic.setImageResource(R.drawable.bookmark_icon)
        }
        if (a.book_progress == 1){
            holder.saved_ic.setImageResource(R.drawable.book_marked_true)
        }

        holder.saved_ic.setOnClickListener {
            if (a.book_progress == 0){
                a.book_progress = 1
                holder.saved_ic.setImageResource(R.drawable.book_marked_true)
                notifyDataSetChanged()
            }else if (a.book_progress==1){
                a.book_progress = 0
                holder.saved_ic.setImageResource(R.drawable.bookmark_icon)
                notifyDataSetChanged()
            }
            onClick.onClick(a, position)
        }
        holder.itemView.setOnClickListener {
            onBosildi.onBosildi(a)
        }
    }

    interface OnClick{
        fun onClick(book: Book, position: Int)
    }
    interface OnBosildi{
        fun onBosildi(book: Book)
    }
}