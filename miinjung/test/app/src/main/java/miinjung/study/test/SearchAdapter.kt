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
import miinjung.study.test.model.Item

class SearchAdapter(val _context: Context) :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    private var dataList : ArrayList<Item> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val convertView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_serch, parent, false)
        return SearchViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        dataList[position].let{data->
            Glide.with(holder.itemView.context).load(data.owner!!.avatarUrl).into(holder.imageSearch)
            if(data.language.isNullOrEmpty())
                holder.textUserLanguage.setText(R.string.nonLang)
            else
                holder.textUserLanguage.text = data.language

            holder.textUserName.text = dataList[position].fullName

            holder.itemBox.setOnClickListener {


                val intent = Intent(_context,DetailActivity::class.java)

                intent.putExtra(DetailActivity.KEY_REPO_NAME,data.name)
                intent.putExtra(DetailActivity.KEY_USER_LOGIN,data.owner?.login)

                _context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setItems(dataList : ArrayList<Item>) {
        this.dataList = dataList
    }

    inner class SearchViewHolder(view: View):RecyclerView.ViewHolder(view){
        var itemBox : LinearLayout = view.findViewById(R.id.itemBox)as LinearLayout
        var imageSearch : ImageView = view.findViewById(R.id.userImage)as ImageView
        var textUserName : TextView = view.findViewById(R.id.userName)as TextView
        var textUserLanguage : TextView = view.findViewById(R.id.userLanguage)as TextView
    }

}