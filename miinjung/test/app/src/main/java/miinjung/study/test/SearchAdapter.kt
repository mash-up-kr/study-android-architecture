package miinjung.study.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miinjung.study.test.Model.item

class SearchAdapter(val _context: Context) :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    internal var dataList : ArrayList<item> = arrayListOf<item>()

//    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.fragment_serch, parent, false)
        return SearchViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        dataList[position].let{
            Glide.with(holder.itemView.context).load(it.owner.avatar_url).into(holder.ImageSearch)

            holder.TextUserLanguage.text =
                if(it.language.equals(""))
                    "No language specified"
                else
                   it.language

            holder.TextUserName.text = dataList[position].full_name
        }

    }

    override fun getItemCount(): Int = dataList.size

    fun setItems(dataList : ArrayList<item>) {
        this.dataList = dataList
    }

    inner class SearchViewHolder(view: View):RecyclerView.ViewHolder(view){
        var ImageSearch : ImageView = view.findViewById(R.id.user_image)as ImageView
        var TextUserName : TextView = view.findViewById(R.id.user_name)as TextView
        var TextUserLanguage : TextView = view.findViewById(R.id.user_language)as TextView
    }

//    interface ItemClickListener {
//
//        fun onItemClick(repository: item)
//    }
}