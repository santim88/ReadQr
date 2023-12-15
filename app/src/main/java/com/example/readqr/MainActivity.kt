package com.example.readqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.readqr.databinding.ActivityMainBinding
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tvResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvResult = findViewById(R.id.tvResult)
        binding.btnScanner.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.PDF_417)
        integrator.setPrompt("Suscribete")
        integrator.setOrientationLocked(false) // You can set this to true if you want to lock the orientation
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.addExtra("TRY_HARDER", true) // Enable tryHarder mode

        integrator.addExtra(Intents.Scan.PDF417_MODE, 200)
        /*        integrator.addExtra(Intents.Scan.HEIGHT, 200)*/

        integrator.initiateScan()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val scannedValue = result.contents
                tvResult.text = "El valor escaneado es: $scannedValue"
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}