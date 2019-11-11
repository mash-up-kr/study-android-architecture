package miinjung.study.test

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miinjung.study.test.Model.item

class SearchAdapter(val _context: Context) :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    internal var dataList : ArrayList<item> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.fragment_serch, parent, false)
        return SearchViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        dataList[position].let{data->
            Glide.with(holder.itemView.context).load(data.owner!!.avatar_url).into(holder.ImageSearch)
            if(data.language.equals(null))
                holder.TextUserLanguage.setText(R.string.nonLang)
            else
                holder.TextUserLanguage.text = data.language

            holder.TextUserName.text = dataList[position].full_name
        }

        holder.itemBox.setOnClickListener {


            val intent = Intent(_context,DetailActivity::class.java)

            intent.putExtra("name",dataList[position].name)
            intent.putExtra("ownerLogin",dataList[position].owner?.login)
            _context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setItems(dataList : ArrayList<item>) {
        this.dataList = dataList
    }

    inner class SearchViewHolder(view: View):RecyclerView.ViewHolder(view){
        var itemBox : LinearLayout = view.findViewById(R.id.item_box)as LinearLayout
        var ImageSearch : ImageView = view.findViewById(R.id.user_image)as ImageView
        var TextUserName : TextView = view.findViewById(R.id.user_name)as TextView
        var TextUserLanguage : TextView = view.findViewById(R.id.user_language)as TextView
    }

}