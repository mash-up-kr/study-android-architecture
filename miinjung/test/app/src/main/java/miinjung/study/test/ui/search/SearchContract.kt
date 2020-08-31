package miinjung.study.test.ui.search

import miinjung.study.test.model.Item
import miinjung.study.test.ui.base.BaseContract

interface SearchContract {
    interface View : BaseContract.View{
        fun showRecyclerView()
        fun showTextView()
        fun initRecycleview()
        fun rvDataBinding(item : ArrayList<Item>)
        fun queryTextSubmit(query:String)
        fun changeActivity(login:String, name:String)

    }
    interface Presenter:BaseContract.Presenter{
        fun searchRepos(query: String)
    }
}