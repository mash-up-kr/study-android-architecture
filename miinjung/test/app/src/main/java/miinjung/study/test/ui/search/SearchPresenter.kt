package miinjung.study.test.ui.search

class SearchPresenter(
    private val searchContract: SearchContract.View
) :SearchContract.Presenter{
    override fun searchRepos(query: String){
        searchContract.showProgress()

        apiCall = api?.search(query)
        apiCall?.enqueue(object :Callback<List>{
            override fun onResponse(call: Call<List>, response: Response<List>) {
                var data = response.body()

                hideProgress()

                if(response.isSuccessful && data != null) {
                    if(data.totalCount > 0){
                        data.items.let{
                            searchAdapter!!.setItems(it)
                            searchAdapter!!.setItems(it)
                            searchAdapter!!.notifyDataSetChanged()
                        }
                    }else{
                        hideRecyclerView()
                        showTextView()
                    }
                }
            }

            override fun onFailure(call: Call<List>, t: Throwable) {
                Log.e("errer","error")
                hideProgress()
            }
        })
    }

}