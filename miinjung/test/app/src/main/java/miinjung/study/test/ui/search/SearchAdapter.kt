package miinjung.study.test.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miinjung.study.test.R
import miinjung.study.test.model.Item

class SearchAdapter :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    private var dataList : ArrayList<Item> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val convertView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_serch, parent, false)
        return SearchViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        dataList[position].let{data->
            Glide.with(holder.itemView.context).load(data.owner!!.avatarUrl).into(holder.imageSearch)
            if(data.language.isEmpty())
                holder.textUserLanguage.setText(R.string.nonLang)
            else
                holder.textUserLanguage.text = data.language

            holder.textUserName.text = dataList[position].fullName

            holder.itemBox.setOnClickListener {

            }

        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setItems(dataList : ArrayList<Item>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }


    class SearchViewHolder(view: View):RecyclerView.ViewHolder(view){
        val itemBox : LinearLayout = view.findViewById(R.id.itemBox)as LinearLayout
        val imageSearch : ImageView = view.findViewById(R.id.userImage)as ImageView
        val textUserName : TextView = view.findViewById(R.id.userName)as TextView
        val textUserLanguage : TextView = view.findViewById(R.id.userLanguage)as TextView
    }
}

