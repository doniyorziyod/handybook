package uz.ictschool.handybook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book

class BookAdapter(var list: List<Book>, var onPressed: OnPressed): RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var rasm = view.findViewById<ImageView>(R.id.book_item_image)
        var name = view.findViewById<TextView>(R.id.book_item_title)
        var yozuvchi = view.findViewById<TextView>(R.id.book_item_author)
        var narxi = view.findViewById<TextView>(R.id.book_item_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_books_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookAdapter.MyViewHolder, position: Int) {
        val a = list[position]
        holder.name.text = a.name
        holder.narxi.text = "$" + a.count_page + ".00"
        holder.rasm.load(a.image)
        holder.yozuvchi.text = a.author
        holder.itemView.setOnClickListener {
            onPressed.onPressed(book = a)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnPressed{
        fun onPressed(book: Book)
    }
}