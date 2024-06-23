package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bracket.databinding.ActivityEditBinding
import com.example.bracket.databinding.ActivityMainBinding

class EditActivityIniciado : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setContentView(R.layout.activity_edit_iniciado)

        val tipoEliminacion = arrayOf("Directa", "Doble")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoEliminacion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonFinalizar: Button = findViewById(R.id.finalizar)
        botonFinalizar.setOnClickListener {
            // Acción a realizar cuando se hace clic en el botón
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()
        val container = findViewById<ViewGroup>(R.id.ly1v3)
        for (playerName in playerNames) {
            val textView = TextView(this)
            textView.text = playerName
            container.addView(textView)
        }
    }
}