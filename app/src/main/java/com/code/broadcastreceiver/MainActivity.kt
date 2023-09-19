package com.code.broadcastreceiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.code.broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val ACTION_DOWNLOAD_STATS = "download_status"
    }

    private lateinit var downloadReceiver: BroadcastReceiver
    private var binding: ActivityMainBinding? = null
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)

        downloadReceiver = object:BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }
    val requestPersmissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted: Boolean ->
        if(isGranted){
            Toast.makeText(this, "SMS receiver diterima", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "SMS receiver ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_permission -> requestPersmissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        val notifyFinishIntent = Intent().setAction(ACTION_DOWNLOAD_STATS)
                        sendBroadcast(notifyFinishIntent)
                    },
                    3000
                )
            }
        else ->{}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }
}