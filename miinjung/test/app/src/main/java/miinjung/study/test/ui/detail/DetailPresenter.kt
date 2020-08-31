package miinjung.study.test.ui.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import miinjung.study.test.model.Item
import miinjung.study.test.network.TestApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(
    private val context:Context,
    private val detailContract: DetailContract.View
) : DetailContract.Presenter {
    private val api by lazy { TestApplication.api }
    private lateinit var apiCall: Call<Item>

    override fun searchRepos(ownerLogin:String,name:String){
        detailContract.showProgress()
        apiCall = api.getRepository(ownerLogin,name)
        apiCall.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                var data = response.body()
                detailContract.hideProgress()

                if (response.isSuccessful && data != null) {
                    data.let{
                        detailContract.dataBind(it)

                    }
                } else {
                    Toast.makeText(context,"Unexpected Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                detailContract.hideProgress()
                Log.i("errer","error")
            }
        })
    }


    override fun apiStop() {
        apiCall?.run { cancel() }
    }

}