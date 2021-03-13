package com.example.wishlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.room.Room

class ListItemDetails : Fragment() {

    lateinit var db: AppDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val database: String = "wishlistdb"
        db = Room.databaseBuilder(context, AppDatabase::class.java, database)
            .allowMainThreadQueries().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = db.ListItemDao()
        val id = arguments?.getLong("id")
        if (id !== null) {
            val item = dao.get(id)
            val titleInput: TextView = view.findViewById(R.id.editTitle)
            val descriptionInput: TextView = view.findViewById(R.id.editDescription)
            titleInput.text = item.title
            descriptionInput.text = item.description
            val updateBtn: Button = view.findViewById(R.id.refresh)
            updateBtn.setOnClickListener {
                val title = titleInput.text.toString()
                val description = descriptionInput.text.toString()
                val afazer = ListItem(title, description)
                afazer.id = id
                dao.update(afazer)
                hideKeyboard()
                view.findNavController().navigate(R.id.list_details)
            }
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