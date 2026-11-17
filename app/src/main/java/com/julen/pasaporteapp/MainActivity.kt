package com.julen.pasaporteapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CountryAdapter
    private lateinit var progressBar: ProgressBar

    private var todosLosPaises: List<Country> = emptyList()
    private var regionSeleccionada: String = "Todos"
    private var textoBusqueda: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        val rvCountries = findViewById<RecyclerView>(R.id.rvCountries)
        val etBuscador = findViewById<EditText>(R.id.etBuscador)

        // Configurar cuadrícula de 2 columnas
        adapter = CountryAdapter(emptyList())
        rvCountries.layoutManager = GridLayoutManager(this, 2)
        rvCountries.adapter = adapter

        // Configurar botones de región
        configurarBotonesRegion()

        // Configurar filtro al escribir en el buscador
        etBuscador.addTextChangedListener { editable ->
            textoBusqueda = editable.toString().trim()
            aplicarFiltros()
        }

        // Cargar datos de la API automáticamente
        cargarTodosLosPaises()
    }

    private fun configurarBotonesRegion() {
        val botones = listOf(
            findViewById<MaterialButton>(R.id.btnPildora) to "Todos",
            findViewById<MaterialButton>(R.id.btnPildora2) to "Europe",
            findViewById<MaterialButton>(R.id.btnPildora3) to "Asia",
            findViewById<MaterialButton>(R.id.btnPildora4) to "Africa",
            findViewById<MaterialButton>(R.id.btnPildora5) to "Americas"
        )

        botones.forEach { (boton, region) ->
            boton.setOnClickListener {
                regionSeleccionada = region
                aplicarFiltros()
            }
        }
    }

    private fun aplicarFiltros() {
        val filtrados = todosLosPaises.filter { pais ->
            val coincideRegion = regionSeleccionada == "Todos" ||
                    pais.region.equals(regionSeleccionada, ignoreCase = true)
            val coincideNombre = textoBusqueda.isEmpty() ||
                    pais.name.contains(textoBusqueda, ignoreCase = true)

            coincideRegion && coincideNombre
        }
        adapter.updateList(filtrados)
    }

    private fun cargarTodosLosPaises() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                todosLosPaises = RetrofitClient.apiService.getAllCountries()
                    .sortedBy { it.name }

                aplicarFiltros()
            } catch (e: Exception) {
                Log.e("API_PAISES", "Error al cargar países: ${e.message}")
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
