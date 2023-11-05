package com.barian.animeku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.barian.animeku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Anime>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        list.addAll(getListAnimes())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                Log.i("LOG MENU", "CLICKED PROFILE")
                val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(aboutIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getListAnimes(): ArrayList<Anime> {
        val dataTitle = resources.getStringArray(R.array.data_anime_title)
        val dataPoster = resources.getStringArray(R.array.data_anime_poster)
        val dataRating = resources.getStringArray(R.array.data_anime_rating)
        val dataStatus = resources.getStringArray(R.array.data_anime_status)
        val dataRelease = resources.getStringArray(R.array.data_anime_release)
        val dataGenres = resources.getStringArray(R.array.data_anime_genres)
        val dataEpisodes = resources.getStringArray(R.array.data_anime_episode)
        val dataStudio = resources.getStringArray(R.array.data_anime_studios)
        val dataSynopsis = resources.getStringArray(R.array.data_anime_synopsis)

        val listAnime = ArrayList<Anime>()

        for (idx in dataTitle.indices) {
            val anime = Anime(
                title = dataTitle[idx],
                poster = dataPoster[idx],
                rating = dataRating[idx].toDouble(),
                status = dataStatus[idx],
                release = dataRelease[idx],
                genres = dataGenres[idx],
                studios = dataStudio[idx],
                episodes = dataEpisodes[idx],
                synopsis = dataSynopsis[idx]
            )
            listAnime.add(anime)
        }

        return listAnime
    }


    private fun showDetailAnimeIntent(data: Anime) {
        val detailsAnimeIntent = Intent(this@MainActivity, DetailAnimeActivity::class.java)
        detailsAnimeIntent.putExtra(DetailAnimeActivity.EXTRA_ANIME, data)
        startActivity(detailsAnimeIntent)
    }


    private fun showRecyclerList() {
        binding.rvAnimes.layoutManager = LinearLayoutManager(this)
        val listAnimeAdapter = ListAnimeAdapter(list)
        binding.rvAnimes.adapter = listAnimeAdapter

        listAnimeAdapter.setOnItemClickCallback(object : ListAnimeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Anime) {
                showDetailAnimeIntent(data)
            }
        })
    }
}

