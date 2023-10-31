package farrukh.remotely.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        var name = itemView.findViewById<TextView>(R.id.name_home)
        var layout = itemView.findViewById<ConstraintLayout>(R.id.card_home)
        //        var discount = itemView.findViewById<TextView>(R.id.discount_discount)
        var author = itemView.findViewById<TextView>(R.id.name_author_home)
        var rating = itemView.findViewById<TextView>(R.id.rating_home)
        var img = itemView.findViewById<ImageView>(R.id.home_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_poem_category, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val item = array.get(position)

        holder.name.text = item.name
        holder.rating.text = item.reyting.toString()
//        holder.discount.text = item.discountPercentage.toString()
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