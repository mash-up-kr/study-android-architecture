package com.example.myapplication.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.model.SearchRepo
import com.example.myapplication.R
import com.example.myapplication.ui.repo.RepoActivity
import org.jetbrains.anko.startActivity

class SearchRecyclerviewAdapter (var ctx : Context, var datalist : ArrayList<SearchRepo>): RecyclerView.Adapter<SearchRecyclerviewAdapter.Holder>(){

    companion object{
        lateinit  var queryUserName : String
        lateinit var queryRepoName : String
    }
    override fun onCreateViewHolder( viewGroup: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        datalist[position].let {repo->
            with(holder){
                Glide.with(ctx)
                    .load(repo.owner.userImg)
                    .centerCrop()
                    .into(img)

                name.text = repo.owner.userName + "/"
                repoName.text = repo.name

               language.text =
                    if(repo.language.isNullOrEmpty())
                        "No language specified"
                    else  datalist[position].language

               container.setOnClickListener {
                    setQuery(repo.owner.userName, repo.name)
                    ctx.startActivity<RepoActivity>()
                }
            }

        }
    }

    private fun setQuery(user : String, repo : String){
        queryUserName = user
        queryRepoName = repo
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val container = itemView.findViewById(R.id.llRvItemSearch) as LinearLayout
        val img = itemView.findViewById<ImageView>(R.id.ivUserImgRvItemSearch)
        val name = itemView.findViewById<TextView>(R.id.tvUserNameRvItemSearch)
        val repoName = itemView.findViewById<TextView>(R.id.tvRepoNameRvItemSearch)
        val language = itemView.findViewById<TextView>(R.id.tvLanguageRvItemSearch)
    }

}