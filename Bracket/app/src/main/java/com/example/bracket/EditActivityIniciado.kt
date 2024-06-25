package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditActivityIniciado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_iniciado)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonFinalizar: Button = findViewById(R.id.finalizar)
        botonFinalizar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val nombreTorneo = intent.getStringExtra(getString(R.string.k_nombreTorneo))
        val tipoEliminacion = intent.getStringExtra(getString(R.string.k_tipoEliminaciono))
        val numEquipos = intent.getStringExtra(getString(R.string.k_numEquipos))
        val playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()

        val textViewNombreTorneo = findViewById<TextView>(R.id.textViewNombreTorneo)
        textViewNombreTorneo.text = nombreTorneo

        val container = findViewById<ViewGroup>(R.id.ly1v3)
        playerNames.shuffle()

        for (i in playerNames.indices step 2) {
            val radioGroup = RadioGroup(this).apply {
                orientation = RadioGroup.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(120, 0, 0, 60)
                }
            }

            val radioButton1 = RadioButton(this).apply {
                text = playerNames[i]
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            radioGroup.addView(radioButton1)

            if (i + 1 < playerNames.size) {
                val radioButton2 = RadioButton(this).apply {
                    text = playerNames[i + 1]
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                radioGroup.addView(radioButton2)
            }

            container.addView(radioGroup)
        }

        val botonEditar: Button = findViewById(R.id.editar)
        botonEditar.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra(getString(R.string.k_nombreTorneo), nombreTorneo)
                putExtra(getString(R.string.k_tipoEliminaciono), tipoEliminacion)
                putExtra(getString(R.string.k_numEquipos), numEquipos)
                putStringArrayListExtra("playerNames", ArrayList(playerNames))
            }
            startActivity(intent)
        }
    }
}
