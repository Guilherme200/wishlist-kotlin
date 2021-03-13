package com.example.wishlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class List : Fragment() {

    lateinit var db: AppDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val database: String = "wishlistdb"
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            database
        ).allowMainThreadQueries().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list: RecyclerView = view.findViewById(R.id.listView)
        list.layoutManager = LinearLayoutManager(context)
        list.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        val adapter = ListAdapter(db.ListItemDao())
        list.adapter = adapter
        val newItem: TextView = view.findViewById(R.id.newItem)
        val buttonAdd: Button = view.findViewById(R.id.add)
        buttonAdd.setOnClickListener {
            adapter.add(newItem.text.toString())
            hideKeyboard()
            newItem.setText("")
        }
    }

    fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = this.requireActivity().currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}