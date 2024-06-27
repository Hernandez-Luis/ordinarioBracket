package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditActivityIniciado : AppCompatActivity() {

    private lateinit var playerNames: ArrayList<String>
    private lateinit var bracketContainer: LinearLayout
    private var round: Int = 0
    private var matchups: MutableList<Pair<String, String>> = mutableListOf()
    private lateinit var nombreTorneo:String
    private lateinit var buttonSiguienteRonda: Button
    private lateinit var figuraPerfil: ImageView
    private lateinit var figuraGanador: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_iniciado)

        enableEdgeToEdge()

        figuraGanador = findViewById(R.id.figura_ganador)

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
        val tipoEliminacion = intent.getStringExtra(getString(R.string.k_tipoEliminaciono))
        val numEquipos = intent.getStringExtra(getString(R.string.k_numEquipos))
        playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()

        val textViewNombreTorneo = findViewById<TextView>(R.id.textViewNombreTorneo)
        textViewNombreTorneo.text = nombreTorneo

        bracketContainer = findViewById(R.id.ly1v3)
        mostrarBracket()

        buttonSiguienteRonda = findViewById(R.id.buttonSiguienteRonda)
        buttonSiguienteRonda.setOnClickListener {
            if (validarSelecciones()) {
                siguienteRonda()
            } else {
                Toast.makeText(this, "Selecciona un ganador para cada bina.", Toast.LENGTH_SHORT).show()
            }
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

        figuraPerfil = findViewById(R.id.figura_perfil)
    }

    private fun mostrarBracket() {
        matchups.clear()
        bracketContainer.removeAllViews()

        playerNames.shuffle()

        for (i in playerNames.indices step 2) {
            if (i + 1 < playerNames.size) {
                matchups.add(Pair(playerNames[i], playerNames[i + 1]))
            } else {
                matchups.add(Pair(playerNames[i], "BYE"))
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

            if (player2 != "BYE") {
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
        for (i in 0 until bracketContainer.childCount) {
            val view = bracketContainer.getChildAt(i)
            if (view is RadioGroup) {
                val selectedRadioButtonId = view.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                    nuevosGanadores.add(selectedRadioButton.text.toString())
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

            // Ocultar el botón y la figura de perfil
            figuraGanador.visibility = View.VISIBLE
            buttonSiguienteRonda.visibility = View.GONE
            figuraPerfil.visibility = View.GONE
        } else {
            playerNames = ArrayList(nuevosGanadores)
            mostrarBracket()
        }
    }

    private fun validarSelecciones(): Boolean {
        var todasSeleccionadas = true
        for (i in 0 until bracketContainer.childCount) {
            val view = bracketContainer.getChildAt(i)
            if (view is RadioGroup) {
                val selectedRadioButtonId = view.checkedRadioButtonId
                todasSeleccionadas = todasSeleccionadas && (selectedRadioButtonId != -1)
            }
        }
        return todasSeleccionadas
    }
}
