package es.ua.eps.filmoteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.NavUtils
import es.ua.eps.filmoteca.databinding.ActivityFilmListBinding



class FilmListActivity : AppCompatActivity() {
    companion object {
        private val add_film = Menu.FIRST
        private val about = Menu.FIRST + 1
    }
    // Utilizamos el primer id disponible (FIRST)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listFilm = findViewById<ListView>(R.id.filmList)
        refresh()
        listFilm.setOnItemClickListener{ parent: AdapterView<*>, view: View,
                                     position: Int, id: Long ->
            //val elemento = adapter.getItem(position)
            goFilm(position)
         }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        // Identificador de grupo
        val groupId = Menu.NONE
        // Identificador único del elemento del menú.
        val itemId = add_film
        // Posición del elemento. Con NONE indicamos que nos es indiferente
        val itemOrder = Menu.NONE
        // Texto de la opción de menú
        val itemText = R.string.menuOptionAdd

        val groupId2 = Menu.NONE
        val itemId2 = about
        val itemOrder2 = Menu.NONE
        val itemText2 = R.string.menuOptionAbout

        val item1 = menu.add(groupId, itemId, itemOrder, itemText)
        val item2 = menu.add(groupId2, itemId2, itemOrder2, itemText2)

        item2.intent = Intent(this, AboutActivity::class.java)
        item1.setOnMenuItemClickListener {
            val f5 = Film()
            f5.title = getString(R.string.film5)
            f5.director = "Kenneth Branagh"
            f5.imageResId = R.mipmap.ic_launcher
            f5.comments = ""
            f5.format = Film.FORMAT_DIGITAL
            f5.genre = Film.GENRE_ACTION
            f5.imdbUrl = "https://www.imdb.com/title/tt0800369/?ref_=fn_al_tt_1"
            f5.year = 2011
            FilmDataSource.films.add(f5)
            refresh()
            true
        }

        return true
    }

    private fun goFilm(position: Int){
        val intentFilm = Intent(this@FilmListActivity, FilmDataActivity::class.java)
        intentFilm.putExtra("position", position)
        startActivity(intentFilm)
    }

    private fun refresh(){
        val listFilm = findViewById<ListView>(R.id.filmList)
        val adapter = FilmsArrayAdapter(
            this,
            R.layout.item_film,
            FilmDataSource.films
        )
        listFilm.adapter = adapter
    }

}