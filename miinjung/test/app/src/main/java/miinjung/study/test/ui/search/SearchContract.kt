package miinjung.study.test.ui.search

import miinjung.study.test.ui.base.BaseContract

interface SearchContract {
    interface View : BaseContract.View{
        fun hideRecyclerView()
        fun showTextView()

    }
    interface Presenter:BaseContract.Presenter{

    }
}