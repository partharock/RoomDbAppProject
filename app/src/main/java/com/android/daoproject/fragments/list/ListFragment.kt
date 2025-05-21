package com.android.daoproject.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.daoproject.R
import com.android.daoproject.stevdza.User
import com.android.daoproject.stevdza.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener({
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        })
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val listView = view.findViewById<ListView>(R.id.listView)
        var arrayList: ArrayList<User> = ArrayList()
//        val arrayListvalue as ArrayList<User>
        GlobalScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                viewModel.readAllData.observe(viewLifecycleOwner) {
                    arrayList.clear()
                    arrayList.addAll(it)
                    Log.d("RoomTestActivity", arrayList.toString())
                    val adapter =
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            arrayList
                        )
                    listView.adapter = adapter
                }
            }
        }
//        val adapter = ListViewAdapter(requireContext(),)

        return view
    }

}