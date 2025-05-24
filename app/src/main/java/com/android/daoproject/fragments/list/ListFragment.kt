package com.android.daoproject.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.daoproject.R
import com.android.daoproject.databinding.FragmentListBinding
import com.android.daoproject.fragments.update.updateFragmentArgs
import com.android.daoproject.stevdza.ListViewAdapter
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
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var viewModel: UserViewModel

    private var selectedUser: User? = null

    private var viewBinding: FragmentListBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var adapter : ListViewAdapter

    private lateinit var finalData : ArrayList<User>

    private lateinit var mListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentListBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_list, container, false)
        viewBinding!!.floatingActionButton.findViewById<FloatingActionButton>(R.id.floatingActionButton)
            .setOnClickListener({
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            })
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val listView =  viewBinding!!.listView
        mListView = listView
        var arrayList: ArrayList<User> = ArrayList()
//        val arrayListvalue as ArrayList<User>
        GlobalScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.Main) {
            viewModel.readAllData.observe(viewLifecycleOwner) {
                arrayList.clear()
                arrayList.addAll(it)
                finalData = arrayList
                Log.d("RoomTestActivity", arrayList.toString())
//                    val adapter = ArrayAdapter(
//                        requireContext(), android.R.layout.simple_list_item_1, arrayList
//                    )
                 adapter = ListViewAdapter(arrayList)
                listView.adapter = adapter
            }
        }
//        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val user = listView.getItemAtPosition(position) as User
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                findNavController().navigate(action)
            }

        listView.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val user = listView.getItemAtPosition(position) as User
                selectedUser = user
                true
            }

        return viewBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.delete_menu, menu)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search_menu_item)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null || query.isEmpty()) {
            val arrayList = ArrayList<User>(finalData)
            adapter.updateData(arrayList)
            return false
        }
        val queryString = "%$query%"
        val searchResult = viewModel.searchUser(queryString)?.observe(this, { it ->
            Log.d("roomtestactivity", "onQueryTextSubmit: size of it " + it.size)
            val arrayList = ArrayList<User>()
            arrayList.clear()
            arrayList.addAll(it)
//            adapter = ListViewAdapter(arrayList)
            adapter.updateData(arrayList)
//            Log.d(TAG, "onQueryTextSubmit: null check ")
//            viewBinding!!.listView.adapter = adapter
        })

//        Log.d("roomtestactivity", "onQueryTextSubmit: " + searchResult?.size)
//        if (searchResult != null) {
//            val adapter = ListViewAdapter(searchResult)
//            viewBinding?.listView?.adapter = adapter
//        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText == null || newText.isEmpty()) {
            val arrayList = ArrayList<User>(finalData)
            adapter.updateData(arrayList)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_menu_item) {
            GlobalScope.launch(Dispatchers.IO) {
                if (selectedUser != null) {
                    viewModel.repository.deleteUser(selectedUser as User)
                } else
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "no selected user", Toast.LENGTH_SHORT).show()
                    }
            }

            Toast.makeText(requireContext(), "deleted", Toast.LENGTH_LONG).show()
            return true
        }
//        else if (item.itemId == R.id.search_menu_item) {
//
//        }

        return false
    }

    fun searchDatabase(query: String, listView: ListView) {
        val searchQuery = "%$query%"
        val searchResult = viewModel.searchUser(searchQuery)

    }


}