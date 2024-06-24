package com.example.bracket

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
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

        val nombreTorneo = intent.getStringExtra(getString(R.string.k_nombreTorneo))

        // Mostrar el nombre del torneo en un TextView u otro elemento
        val textViewNombreTorneo = findViewById<TextView>(R.id.textViewNombreTorneo)
        textViewNombreTorneo.text = nombreTorneo

        val playerNames = intent.getStringArrayListExtra("playerNames") ?: arrayListOf()
        playerNames.shuffle()
        val container = findViewById<ViewGroup>(R.id.ly1v3)
        // Agrupar los nombres de dos en dos y crear RadioButtons
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
    }

}