package com.ids1024.lsdict

import android.database.Cursor
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

class LSSearchAdapter(
    cursor: Cursor,
) : RecyclerView.Adapter<LSSearchAdapter.ViewHolder>() {
    private val cursor = cursor

    override fun getItemCount(): Int = cursor.count

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        cursor.moveToPosition(position)
        val html = cursor.getString(3)
        holder.text_view.text = HtmlCompat.fromHtml(html, 0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result, null)
        return ViewHolder(view)
    }

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        val text_view: TextView = view.findViewById(R.id.result_text)
    }
}
