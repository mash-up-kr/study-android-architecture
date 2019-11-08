package com.example.myapplication.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.SearchRepo
import com.example.myapplication.R
import org.jetbrains.anko.startActivity

class SearchRecyclerviewAdapter (var ctx : Context, var datalist : ArrayList<SearchRepo>): RecyclerView.Adapter<SearchRecyclerviewAdapter.Holder>(){

    override fun onCreateViewHolder( viewGroup: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        datalist[position].let {
            Glide.with(ctx)
                .load(it.owner.avatar_url)
                .centerCrop()
                .into(holder.img)

            holder.name.text = it.owner.login + "/"
            
            holder.language.text =
                if(it.language.isNullOrEmpty())
                    "No language specified"
                else  datalist[position].language

            holder.repoName.text = it.name     
        }
       
        holder.container.setOnClickListener {
            ctx.startActivity<RepoActivity>("login" to datalist[position].owner.login, "repoName" to datalist[position].name)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var container = itemView.findViewById(R.id.ll_rv_item_search) as LinearLayout
        var img = itemView.findViewById<ImageView>(R.id.imgv_rv_item_search)
        var name = itemView.findViewById<TextView>(R.id.tv_name_rv_item_search)
        var repoName = itemView.findViewById<TextView>(R.id.tv_repo_name_rv_item_search)
        var language = itemView.findViewById<TextView>(R.id.tv_lang_rv_item_search)
    }
}