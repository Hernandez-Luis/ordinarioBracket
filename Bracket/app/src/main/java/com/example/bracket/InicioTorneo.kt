package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bracket.databinding.ActivityMainBinding

class InicioTorneo : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_torneo)

        val tipoEliminacion = arrayOf("Directa", "Doble")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoEliminacion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonCancelar: Button = findViewById(R.id.cancelarTorneo);
        botonCancelar.setOnClickListener {
            // Acción a realizar cuando se hace clic en el botón
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val botonGuardar: Button = findViewById(R.id.guardarTorneo);
        botonGuardar.setOnClickListener {
            // Acción a realizar cuando se hace clic en el botón
            val intent = Intent(this,TorneoIniciado::class.java)
            startActivity(intent)
        }
    }
}