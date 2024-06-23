package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bracket.databinding.ActivityEditBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipoEliminacion = arrayOf("Directa", "Doble")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoEliminacion)
        binding.actvTipoEliminacion.setAdapter(adapter)

        val numeroEquipos = arrayOf("4", "8")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numeroEquipos)
        binding.actvNumEquipos.setAdapter(adapter2)

        binding.actvNumEquipos.setOnItemClickListener { _, _, position, _ ->
            val numPlayers = numeroEquipos[position].toInt()
            addPlayerInputs(numPlayers)
        }

        val botonCancelar: Button = findViewById(R.id.cancelarTorneo)
        botonCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val botonGuardar: Button = findViewById(R.id.guardarTorneo)
        botonGuardar.setOnClickListener {
            val intent2 = Intent(this, EditActivityIniciado::class.java)
            startActivity(intent2)
        }
    }

    private fun addPlayerInputs(numPlayers: Int) {
        val container = findViewById<ViewGroup>(R.id.playersContainer)
        container.removeAllViews()

        for (i in 1..numPlayers) {
            val textInputLayout = LayoutInflater.from(this).inflate(R.layout.input_player, container, false) as TextInputLayout
            textInputLayout.hint = "Nombre del jugador $i"
            container.addView(textInputLayout)
        }
    }
}
