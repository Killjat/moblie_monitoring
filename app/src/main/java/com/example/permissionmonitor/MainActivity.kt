package com.example.permissionmonitor

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permissionmonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AppPermissionAdapter
    private val permissionApps = mutableListOf<AppPermissionInfo>()

    companion object {
        private const val USAGE_STATS_PERMISSION_REQUEST = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupClickListeners()
        
        // 检查并请求权限
        checkPermissions()
    }

    private fun setupRecyclerView() {
        adapter = AppPermissionAdapter(permissionApps)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        binding.btnHelloWorld.setOnClickListener {
            val input = binding.etInput.text.toString()
            if (input.lowercase() == "helloworld") {
                Toast.makeText(this, "Hello World! 权限检测开始...", Toast.LENGTH_SHORT).show()
                scanAppsWithPermissions()
            } else {
                Toast.makeText(this, "请输入 'helloworld'", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRefresh.setOnClickListener {
            scanAppsWithPermissions()
        }
    }

    private fun checkPermissions() {
        // 检查使用情况访问权限
        if (!hasUsageStatsPermission()) {
            requestUsageStatsPermission()
        }
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivityForResult(intent, USAGE_STATS_PERMISSION_REQUEST)
    }

    private fun scanAppsWithPermissions() {
        permissionApps.clear()
        
        try {
            val packageManager = packageManager
            val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

            for (appInfo in installedApps) {
                // 跳过系统应用（可选）
                if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    continue
                }

                val permissions = mutableListOf<String>()
                
                try {
                    val packageInfo = packageManager.getPackageInfo(
                        appInfo.packageName, 
                        PackageManager.GET_PERMISSIONS
                    )
                    
                    packageInfo.requestedPermissions?.forEach { permission ->
                        when (permission) {
                            Manifest.permission.READ_CONTACTS -> {
                                permissions.add("读取通讯录")
                            }
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES -> {
                                permissions.add("读取图片")
                            }
                            Manifest.permission.CAMERA -> {
                                permissions.add("相机权限")
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                permissions.add("位置信息")
                            }
                        }
                    }
                } catch (e: Exception) {
                    // 处理获取权限信息失败的情况
                }

                if (permissions.isNotEmpty()) {
                    val appName = packageManager.getApplicationLabel(appInfo).toString()
                    val appIcon = packageManager.getApplicationIcon(appInfo)
                    
                    permissionApps.add(
                        AppPermissionInfo(
                            appName = appName,
                            packageName = appInfo.packageName,
                            permissions = permissions,
                            icon = appIcon
                        )
                    )
                }
            }

            adapter.notifyDataSetChanged()
            binding.tvResult.text = "找到 ${permissionApps.size} 个应用有敏感权限"
            
        } catch (e: Exception) {
            Toast.makeText(this, "扫描失败: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == USAGE_STATS_PERMISSION_REQUEST) {
            if (hasUsageStatsPermission()) {
                Toast.makeText(this, "权限已授予", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "需要使用情况访问权限才能完整检测", Toast.LENGTH_LONG).show()
            }
        }
    }
}