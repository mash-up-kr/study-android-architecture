package miinjung.study.test

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import miinjung.study.test.Model.item
import miinjung.study.test.DetailActivity

class SearchAdapter(val _context: Context) :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    internal var dataList : ArrayList<item> = arrayListOf()
    val nonLang : String = "No language specified"
    var pageData : item ?= null

    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val convertView = LayoutInflater.from(_context).inflate(R.layout.fragment_serch, parent, false)
        return SearchViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        dataList[position].let{data->
            Glide.with(holder.itemView.context).load(data.owner!!.avatar_url).into(holder.ImageSearch)

            holder.TextUserLanguage.text =
                if(data.language.equals(""))
                    nonLang
                else
                   data.language

            holder.TextUserName.text = dataList[position].full_name
            holder.itemView.setOnClickListener { listener?.onItemClick(data)}
        }

        holder.itemBox.setOnClickListener {
            pageData = dataList[position]
            val context : Context = it.context

            Log.i("msg",dataList[position].toString())

            val intent : Intent = Intent(context,Detail2Activity::class.java)
//            intent.putExtra("item", pageData)z
            intent.putExtra("fullName",dataList[position].full_name)
            intent.putExtra("language",dataList[position].language)
            intent.putExtra("description",dataList[position].description)
            intent.putExtra("star",dataList[position].stargazers_count)
            intent.putExtra("update",dataList[position].updated_at)
            intent.putExtra("image",dataList[position].owner!!.avatar_url)
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun setItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    interface ItemClickListener {

        fun onItemClick(item: item)
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

//    override fun onClick(v: View) {
//        val id = v.id
//        when (id) {
//            R.id.item_box -> {
//                val intent = Intent(_context, DetailActivity::class.java)
//                intent.putExtra("item", item)
//                _context?.startActivity(intent)
//            }
//        }
//    }

}