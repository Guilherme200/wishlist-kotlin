package com.example.wishlist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val ListItemDao: ListItemDao) :
    RecyclerView.Adapter<ListAdapter.listItemHolder>() {

    val list: MutableList<ListItem>

    init {
        list = ListItemDao.getAll().toMutableList()
    }

    class listItemHolder(v: View, val data: MutableList<ListItem>) : RecyclerView.ViewHolder(v) {
        val title: TextView
        val description: TextView
        val remove: Button

        init {
            title = v.findViewById(R.id.title)
            description = v.findViewById(R.id.description)
            remove = v.findViewById(R.id.remove)
            title.setOnClickListener(::details)
            description.setOnClickListener(::details)
        }

        fun details(v: View) {
            val data = data[adapterPosition]
            val args = Bundle()
            args.putLong("id", data.id)
            v.findNavController().navigate(R.id.lista_para_detalhes, args)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return listItemHolder(view, list)
    }

    override fun onBindViewHolder(holder: listItemHolder, position: Int) {
        val context = holder.itemView.context
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        holder.remove.setOnClickListener {
            remove(position, context)
        }
    }

    override fun getItemCount() = list.size

    fun remove(position: Int, context: Context) {
        val item = list[position]
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Tem certeza que deseja remover este item?")
            .setCancelable(false)
            .setPositiveButton("Remover") { dialog, id ->
                ListItemDao.delete(item)
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount - position)
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun add(title: String) {
        if (title.length != 0) {
            val item = ListItem(title, "(Sem Descrição)")
            item.id = ListItemDao.insert(item)
            list.add(0, item)
            notifyItemInserted(0)
            notifyItemRangeChanged(0, itemCount)
        }
    }
}