package com.android.daoproject.fragments.add

import android.os.Bundle
import android.os.Looper
import android.os.Looper.loop
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.daoproject.R
import com.android.daoproject.stevdza.User
import com.android.daoproject.stevdza.UserDatabase
import com.android.daoproject.stevdza.UserRepository
import com.android.daoproject.stevdza.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setHasOptionsMenu(true)
//        val database = UserDatabase.getInstance(requireContext())
//        val repository = UserRepository(database.getDao())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.findViewById<Button>(R.id.button).setOnClickListener({
            GlobalScope.launch(Dispatchers.IO) {
                addUser()
            }
        })
        return view
    }


    suspend fun addUser() {
        val firstName = view?.findViewById<EditText>(R.id.firstName)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.lastName)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.age)?.text.toString()
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && age.toIntOrNull() != null) {
            val user = User(0, firstName, lastName, age.toInt())
            viewModel.addUser(user)
            // navigate back
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "succesfully added", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }
        }
    }


}