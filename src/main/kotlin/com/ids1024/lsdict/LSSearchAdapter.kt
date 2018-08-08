package com.ids1024.lsdict

import android.database.Cursor
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Html
import android.support.v7.widget.RecyclerView

class LSSearchAdapter(cursor: Cursor) : RecyclerView.Adapter<LSSearchAdapter.ViewHolder>() {
    private val cursor = cursor

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)
        val html = cursor.getString(3)
        holder.text_view.text = Html.fromHtml(html)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result, null)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text_view: TextView = view.findViewById(R.id.result_text)
    }
}
