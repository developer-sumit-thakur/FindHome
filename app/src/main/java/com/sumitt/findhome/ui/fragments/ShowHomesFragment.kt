package com.sumitt.findhome.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.sumitt.findhome.R
import com.sumitt.findhome.model.Homes
import com.sumitt.findhome.ui.adapter.ListingAdapter
import com.sumitt.findhome.viewmodel.ListingViewModel
import android.widget.ArrayAdapter


/**
 * Fragment to display UI
 * @author sumit.T
 * */
class ShowHomesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        val TAG: String = ShowHomesFragment::class.java.simpleName
    }

    private val start = 0
    private val end = 5
    lateinit var adapter: ListingAdapter
    lateinit var recyclerView: RecyclerView
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    var listingViewModel: ListingViewModel? = null
    var fragmentListener: FragmentListener? = null
    lateinit var apiLoadSpinner: Spinner
    private val spinnerValues = arrayOf("0-5", "6-10", "11-15", "16-20", "21-25")

    interface FragmentListener {
        fun onSuccess()
        fun onError()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is FragmentListener) {
            fragmentListener = activity as FragmentListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listingViewModel = ViewModelProviders.of(this).get(ListingViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.show_homes_fragment, container, false)

        recyclerView = view.findViewById(R.id.homes_listview)
        apiLoadSpinner = view.findViewById(R.id.spinner)

        val spinnerAdapter = ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, spinnerValues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        apiLoadSpinner.setAdapter(spinnerAdapter)
        apiLoadSpinner.setOnItemSelectedListener(this)

        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView.setLayoutManager(mLayoutManager)

        adapter = ListingAdapter(activity, ArrayList())
        recyclerView.setAdapter(adapter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listingViewModel?.start = start
        listingViewModel?.end = end
        listingViewModel?.listings?.observe(this, Observer {
            it?.apply { showUI(it) } ?: showError()
        })
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

    fun showError() {
        Log.d(TAG, "Error in getting response")
        fragmentListener?.onError()
    }

    fun showUI(result: List<Homes>) {
        Log.d(TAG, "Response : $result")
        fragmentListener?.onSuccess()
        takeIf { result.size > 0 }?.apply {
            adapter?.setHomes(result)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(TAG, "onNotingSelected.")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "onItemSelected $position")
        loadData(position)
    }

    private fun loadData(position: Int) {
        listingViewModel?.start = position
        listingViewModel?.end = position + 5
        listingViewModel?.loadData(listingViewModel?.listings)
    }
}