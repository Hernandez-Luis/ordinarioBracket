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

        val nombreTorneo = intent.getStringExtra(getString(R.string.k_nombreTorneo))
        val tipoEliminacion = intent.getStringExtra(getString(R.string.k_tipoEliminaciono))
        val numEquipos = intent.getStringExtra(getString(R.string.k_numEquipos))
        val playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()

        binding.tieNombreTorneo.setText(nombreTorneo)
        binding.actvTipoEliminacion.setText(tipoEliminacion, false)
        binding.actvNumEquipos.setText(numEquipos, false)

        val tipoEliminacionArray = arrayOf("Directa", "Doble")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoEliminacionArray)
        binding.actvTipoEliminacion.setAdapter(adapter)

        val numeroEquiposArray = arrayOf("4", "8")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numeroEquiposArray)
        binding.actvNumEquipos.setAdapter(adapter2)

        binding.actvNumEquipos.setOnItemClickListener { _, _, position, _ ->
            val numPlayers = numeroEquiposArray[position].toInt()
            addPlayerInputs(numPlayers)
        }

        addPlayerInputs(playerNames.size)
        setPlayerNames(playerNames)

        val botonCancelar: Button = findViewById(R.id.cancelarTorneo)
        botonCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val botonGuardar: Button = findViewById(R.id.guardarTorneo)
        botonGuardar.setOnClickListener {
            val intent = Intent(this, EditActivityIniciado::class.java)
            val playerNames = getPlayerNames()
            intent.putStringArrayListExtra("playerNames", ArrayList(playerNames))
            intent.putExtra(getString(R.string.k_nombreTorneo), binding.tieNombreTorneo.text.toString())
            startActivity(intent)

            enviarInfo()
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

    private fun getPlayerNames(): List<String> {
        val container = findViewById<ViewGroup>(R.id.playersContainer)
        val playerNames = mutableListOf<String>()

        for (i in 0 until container.childCount) {
            val textInputLayout = container.getChildAt(i) as TextInputLayout
            val editText = textInputLayout.editText
            val playerName = editText?.text.toString()
            if (playerName.isNotBlank()) {
                playerNames.add(playerName)
            }
        }
        return playerNames
    }

    private fun setPlayerNames(playerNames: List<String>) {
        val container = findViewById<ViewGroup>(R.id.playersContainer)
        for (i in 0 until container.childCount) {
            val textInputLayout = container.getChildAt(i) as TextInputLayout
            val editText = textInputLayout.editText
            editText?.setText(playerNames[i])
        }
    }

    fun enviarInfo() {
        val intent = Intent()
        intent.putExtra(getString(R.string.k_nombreTorneo), binding.tieNombreTorneo.text.toString())
        intent.putExtra(getString(R.string.k_tipoEliminaciono), binding.actvTipoEliminacion.text.toString())
        intent.putExtra(getString(R.string.k_numEquipos), binding.actvNumEquipos.text.toString())

        setResult(RESULT_OK, intent)
        finish()
    }
}
