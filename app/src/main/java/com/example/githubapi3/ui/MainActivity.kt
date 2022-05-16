package com.example.githubapi3.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubapi3.R
import com.example.githubapi3.ui.adapter.RecyclerViewAdapter
import com.example.githubapi3.ui.viewmodel.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchViewFAB: FloatingActionButton
    private lateinit var searchView: EditText
    private lateinit var currentSearch: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        initRecyclerView()
        initViewModel()
        initSwipeUp()
        initSearch()
        newQuery("Kotlin")
    }

    private fun initRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastVisibleItemPosition()
                val numItems = recyclerView.adapter!!.itemCount - 1

                if (pos >= numItems) {
                    mainActivityViewModel.makeApiCall(pagination = true)
                    swipeLayout.isRefreshing = true
                }
            }
        })
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getLiveObserver().observe(
            this
        ) { list ->

            if (list != null) recyclerViewAdapter.addUpdatedList(list)
            else Toast.makeText(this@MainActivity, "No data found", Toast.LENGTH_SHORT)
                .show()

            swipeLayout.isRefreshing = false
        }
    }

    private fun initSwipeUp() {

        swipeLayout = findViewById(R.id.refreshLayout)
        swipeLayout.setOnRefreshListener {
            recyclerViewAdapter.resetList()
            mainActivityViewModel.makeApiCall(pagination = false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putParcelable("SAVE_STATE", recyclerView.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelable<Parcelable>("SAVE_STATE")?.let {
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }

    private fun initSearch() {

        searchViewFAB = findViewById(R.id.fab_search)
        searchView = findViewById(R.id.sv_query_param)

        searchViewFAB.setOnClickListener {
            searchViewFAB.isVisible = false
            searchView.isVisible = true

            searchView.requestFocus()
            searchView.showKeyboard()

            searchView.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    newQuery(searchView.text.toString())
                    searchView.hideKeyboard()
                    true
                } else false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun newQuery(queryParam: String) {
        searchView.isVisible = false
        searchViewFAB.isVisible = true

        recyclerViewAdapter.resetList()
        swipeLayout.isRefreshing = true

        currentSearch = findViewById(R.id.tv_current_search)
        currentSearch.text = "Current search: $queryParam"

        mainActivityViewModel.makeApiCall(queryParam, false)
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun View.showKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}