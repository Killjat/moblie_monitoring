package com.example.permissionmonitor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppPermissionAdapter(
    private val apps: List<AppPermissionInfo>
) : RecyclerView.Adapter<AppPermissionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.ivAppIcon)
        val appName: TextView = view.findViewById(R.id.tvAppName)
        val packageName: TextView = view.findViewById(R.id.tvPackageName)
        val permissions: TextView = view.findViewById(R.id.tvPermissions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_permission, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        
        holder.appIcon.setImageDrawable(app.icon)
        holder.appName.text = app.appName
        holder.packageName.text = app.packageName
        holder.permissions.text = "权限: ${app.permissions.joinToString(", ")}"
    }

    override fun getItemCount() = apps.size
}