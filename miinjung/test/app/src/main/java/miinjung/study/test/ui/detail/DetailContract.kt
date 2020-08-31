package miinjung.study.test.ui.detail

import miinjung.study.test.model.Item
import miinjung.study.test.ui.base.BaseContract

interface DetailContract {
    interface View:BaseContract.View{
        fun getKey()
        fun dataBind(item:Item)
    }
    interface Presenter:BaseContract.Presenter{
        fun searchRepos(ownerLogin:String,name:String)
    }
}