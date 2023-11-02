package farrukh.remotely.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ictschool.handybook.R
import uz.ictschool.handybook.data.Book

class BookAdapter(
    var array: MutableList<Book>,
    var listener: ItemClick
) : RecyclerView.Adapter<BookAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val name:TextView = itemView.findViewById(R.id.book_item_title)
//        val audio:ImageView = itemView.findViewById(R.id.book_item_audio)
        val author:TextView = itemView.findViewById(R.id.book_item_author)
        val rating:TextView = itemView.findViewById(R.id.book_item_rating)
        val img:ImageView = itemView.findViewById(R.id.book_item_image_iv)
        val bookmarkIV:ImageView = itemView.findViewById(R.id.book_item_bookmark_iv)
        val bookMarkCV:CardView = itemView.findViewById(R.id.book_item_bookmark_cardview)

        val layout:CardView = itemView.findViewById(R.id.book_item_image_card_view)
        val parentView:ConstraintLayout = itemView.findViewById(R.id.book_item_parent_constraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val item = array.get(position)

        holder.name.text = item.name
        holder.rating.text = item.reyting.toString()
        holder.author.text = item.author
        holder.img.load(item.image)


        holder.layout.setOnClickListener {
            listener.OnItemClick(item)
        }

    }

    interface ItemClick {
        fun OnItemClick(book: Book)
    }

}