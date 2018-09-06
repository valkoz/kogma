package com.github.valkoz.kogma

import android.os.AsyncTask
import android.os.Bundle
import com.github.valkoz.kogma.model.Item
import com.github.valkoz.kogma.model.Rss
import com.github.valkoz.kogma.model.TransformedItem
import org.simpleframework.xml.core.Persister
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

interface MainPresenter {
    fun onCreate(mvpView: MainView)
    fun onDestroy()

    fun noInternetAccess()
    fun loadData()
    fun restoreData(viewState: Bundle, key: String)
}

class MainPresenterImpl: MainPresenter {

    private val ERROR_MESSAGE = "No Internet access"
    private var view: MainView? = null
    private var task: LoadTask? = null

    override fun onCreate(mvpView: MainView) {
        view = mvpView
    }

    override fun onDestroy() {
        view = null
        if (task != null && task?.status == AsyncTask.Status.RUNNING) {
            task?.cancel(true)
        }
    }

    override fun noInternetAccess() {
        view?.hideLoading()
        view?.showError(ERROR_MESSAGE)
    }

    override fun loadData() {
        view?.showLoading()
        if (task == null || task?.status != AsyncTask.Status.RUNNING) {
            task = LoadTask(view)
            task?.execute()
        }
    }

    override fun restoreData(viewState: Bundle, key: String) {
        view?.showItems(viewState.getParcelableArrayList(key))
    }

    class LoadTask(private val view: MainView?) : AsyncTask<String, Void, List<TransformedItem>>() {

        override fun doInBackground(vararg uri: String): List<TransformedItem>? {

            val connection = URL("https://habr.com/rss/hubs/all/").openConnection() as HttpURLConnection

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val data = connection.inputStream.bufferedReader().readText()

                val xmlReader = StringReader(data)
                val serializer = Persister()
                val rss = serializer.read<Rss>(Rss::class.java, xmlReader, false)
                val items = rss.channel?.items

                val transformedItems = ArrayList<TransformedItem>()
                if (items != null) {
                    for (item: Item in items) {
                        val description = item.description?.replace(Regex("<[^\\P{Graph}>]+(?: [^>]*)?>"), "")
                        transformedItems.add(
                                TransformedItem(item.title.toString(),
                                        description.toString(),
                                        item.pubDate.toString(),
                                        item.creator.toString(),
                                        item.categories.toString()))
                    }
                }
                return transformedItems
            }
            return null
        }

        override fun onPostExecute(result: List<TransformedItem>?) {
            super.onPostExecute(result)
            if (view != null) {
                view.hideLoading()
                if (result != null)
                    view.showItems(result)
            }

        }
    }
}