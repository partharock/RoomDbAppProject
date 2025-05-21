package com.android.daoproject

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.android.daoproject.cheezy.data.User
import com.android.daoproject.cheezy.data.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomTestActivity : AppCompatActivity() {
    lateinit var database: UserDatabase
    val TAG = "RoomTestActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setupActionBarWithNavController(findNavController(R.id.fragment))
        // as database is same throughout the application
        database = UserDatabase.getInstance(this)
        GlobalScope.launch {
            database.userDao().insertContact(User(0,"john","connor",43))
        }
        fetchData()
    }

    fun fetchData() {
        database.userDao().getContacts().observe(this, Observer{
//            Log.d(TAG, "fetchData: " + it.toString())
            Log.d(TAG, "fetchData: creation" + it.toString())
          //  findViewById<TextView>(R.id.textview).text = it.toString()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}