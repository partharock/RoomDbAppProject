package com.android.daoproject.stevdza

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter(
     var items: List<User>
) : BaseAdapter() {

    private var selectedPosition: Int = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        textView.text = items[position].toString()

        // Highlight selected item
        if (position == selectedPosition) {
            textView.setBackgroundColor(Color.LTGRAY)
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT)
        }

        return view
    }

    fun updateData(newItems: List<User>) {
        items = newItems
        notifyDataSetChanged()
    }
}