package com.example.bracket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditActivityIniciado : AppCompatActivity() {

    private lateinit var playerNames: ArrayList<String>
    private lateinit var bracketContainer: LinearLayout
    private var matchups: MutableList<Pair<String, String>> = mutableListOf()
    private lateinit var nombreTorneo:String
    private lateinit var tipoEliminacion:String
    private lateinit var numEquipos:String


    @SuppressLint("ResourceType")
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

        nombreTorneo = intent.getStringExtra(getString(R.string.k_nombreTorneo)).toString()
        tipoEliminacion = intent.getStringExtra(getString(R.string.k_tipoEliminaciono)).toString()
        numEquipos = intent.getStringExtra(getString(R.string.k_numEquipos)).toString()

//        val mensaje = "Tipo de Eliminación: $tipoEliminacion \nNúmero de Equipos: $numEquipos"
//        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

        playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()

        val textViewNombreTorneo = findViewById<TextView>(R.id.textViewNombreTorneo)
        textViewNombreTorneo.text = nombreTorneo

        bracketContainer = findViewById(R.id.ly1v3)
        mostrarBracket()

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

        val buttonSiguienteRonda: Button = findViewById(R.id.buttonSiguienteRonda)
        buttonSiguienteRonda.setOnClickListener {
            siguienteRonda()
        }
    }

    private fun mostrarBracket() {
        matchups.clear()
        bracketContainer.removeAllViews()

        playerNames.shuffle()

        for (i in playerNames.indices step 2) {
            if (i + 1 < playerNames.size) {
                matchups.add(Pair(playerNames[i], playerNames[i + 1]))
            } else {
                matchups.add(Pair(playerNames[i], "ADIÓS :c"))
            }
        }

        for ((player1, player2) in matchups) {
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
                text = player1
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            radioGroup.addView(radioButton1)

            if (player2 != "ADIÓS :c") {
                val radioButton2 = RadioButton(this).apply {
                    text = player2
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                radioGroup.addView(radioButton2)
            }

            bracketContainer.addView(radioGroup)
        }
    }

    private fun siguienteRonda() {
        val nuevosGanadores = mutableListOf<String>()
        val nuevosPerdedores = mutableListOf<String>()
        for (i in 0 until bracketContainer.childCount) {
            val view = bracketContainer.getChildAt(i)
            if (view is RadioGroup) {
                val selectedRadioButtonId = view.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    nuevosGanadores.add(selectedRadioButton.text.toString())
                } else {
                    val perdedor = view.findViewById<RadioButton>(selectedRadioButtonId)
                    nuevosPerdedores.add(perdedor.text.toString())
                }
            }
        }

        if (nuevosGanadores.size == 1) {
            // Mostrar el ganador
            val textViewGanador = TextView(this).apply {
                text = "\t\t ¡¡GANADOR:!! \n\n Felicidades ¡${nuevosGanadores[0]}! ganaste el torneo de '${nombreTorneo}'"
                textSize = 24f
                setTextColor(resources.getColor(R.color.golden))
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(30, 32, 16, 0)
                }
            }
            bracketContainer.removeAllViews()
            bracketContainer.addView(textViewGanador)
            val btnSiguiente:Button = findViewById(R.id.buttonSiguienteRonda)
            btnSiguiente.visibility = View.GONE
        } else {
            // Agrega solo a los ganadores de la ronda anterior
            playerNames = ArrayList(nuevosGanadores)
            mostrarBracket()

            if (tipoEliminacion == "Doble"){

            }
        }
    }
}

