package com.android.daoproject.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.daoproject.R
import com.android.daoproject.stevdza.User
import com.android.daoproject.stevdza.UserDatabase
import com.android.daoproject.stevdza.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class updateFragment : Fragment() {

    private val args by navArgs<updateFragmentArgs>()
    private lateinit var viewmodel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        val view = layoutInflater.inflate(R.layout.fragment_update, null)
        view.findViewById<EditText>(R.id.firstName).setText(args.currentName.firstName)
        view.findViewById<EditText>(R.id.lastName).setText(args.currentName.lastName)
        view.findViewById<EditText>(R.id.age).setText(args.currentName.age.toString())

        view.findViewById<Button>(R.id.button).setOnClickListener {
            val firstName = view.findViewById<EditText>(R.id.firstName).text.toString()
            val lastName = view.findViewById<EditText>(R.id.lastName).text.toString()
            val age = view.findViewById<EditText>(R.id.age).text.toString().toInt()
            val id = args.currentName.id
            GlobalScope.launch(Dispatchers.IO) {
                viewmodel.updateUser(User(id, firstName, lastName, age))
            }

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return view
    }
}