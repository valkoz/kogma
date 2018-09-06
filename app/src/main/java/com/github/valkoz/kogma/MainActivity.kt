package com.github.valkoz.kogma

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.github.valkoz.kogma.model.TransformedItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

interface MainView {
    fun showItems(items: List<TransformedItem>)
    fun showError(error: String)
    fun showLoading()
    fun hideLoading()
}

class MainActivity : AppCompatActivity(), MainView {

    private val KEY = "items"
    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        presenter = MainPresenterImpl()
        swipe_refresh.setOnRefreshListener { this.getItems() }
        presenter?.onCreate(this)

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY))
            presenter?.restoreData(savedInstanceState, KEY)
        else
            getItems()

    }

    private fun getItems() {
        if (isNetworkAvailable())
            presenter?.loadData()
        else
            presenter?.noInternetAccess()
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isConnected = false
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null)
            isConnected = activeNetwork.isConnected
        return isConnected
    }

    private fun initView() {

        recycler_view.apply {
            setHasFixedSize(true)
            val linearLayout =  LinearLayoutManager(context)
            layoutManager = linearLayout
        }

        initAdapter()

    }

    private fun initAdapter() {
        if (recycler_view.adapter == null) {
            recycler_view.adapter = ItemAdapter(ArrayList())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!(recycler_view.adapter as ItemAdapter).getItems().isEmpty()) {
            outState.putParcelableArrayList(KEY, ArrayList((recycler_view.adapter as ItemAdapter).getItems()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun showItems(items: List<TransformedItem>) {
        (recycler_view.adapter as ItemAdapter).addItems(items)
    }

    override fun showError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh.isRefreshing = false
    }
}
