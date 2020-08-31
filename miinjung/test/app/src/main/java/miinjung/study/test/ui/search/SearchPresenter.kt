package miinjung.study.test.ui.search

import android.util.Log
import android.view.View
import android.widget.SearchView
import miinjung.study.test.model.List
import miinjung.study.test.network.TestApplication
import retrofit2.Call


class SearchPresenter(
    private val searchContract: SearchContract.View
) :SearchContract.Presenter{

    private val api by lazy { TestApplication.api }
    private lateinit var apiCall: Call<List>

    override fun searchRepos(query: String){
        searchContract.showProgress()
        apiCall = api?.search(query)
        apiCall.enqueue(object : retrofit2.Callback<List> {
            override fun onResponse(call: retrofit2.Call<List>, response: retrofit2.Response<List>) {
                var data = response.body()
                searchContract.hideProgress()
                if(response.isSuccessful && data != null) {
                    if(data.totalCount > 0){
                        data.items.let{
                            searchContract.rvDataBinding(it)
                        }
                    }else{
                        searchContract.showTextView()
                    }
                }else {
                    searchContract.showTextView()
                }
            }
            override fun onFailure(call: retrofit2.Call<List>, t: Throwable) {
                Log.e("errer","error")
                searchContract.hideProgress()

            }
        })
    }

    override fun apiStop() {
        apiCall?.run { cancel() }
    }
}