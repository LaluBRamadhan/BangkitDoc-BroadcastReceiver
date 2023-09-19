package com.code.broadcastreceiver

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.code.broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
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
        else ->{}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}