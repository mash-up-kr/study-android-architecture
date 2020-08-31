package miinjung.study.test.ui.base

interface BaseContract {
    interface View{
        fun showProgress()

        fun hideProgress()
    }
    interface  Presenter{
        fun apiStop()
    }
}