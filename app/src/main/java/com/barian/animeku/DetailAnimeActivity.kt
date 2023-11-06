package com.barian.animeku

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.barian.animeku.databinding.ActivityDetailAnimeBinding
import com.bumptech.glide.Glide

class DetailAnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnimeBinding

    companion object {
        const val EXTRA_ANIME = "extra_anime"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        val anime = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Anime>(EXTRA_ANIME, Anime::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Anime>(EXTRA_ANIME)
        }

        binding.iconBack.setOnClickListener {
            finish()
        }

        if (anime != null) {
            binding.includeDetailAnime.tvContentTitle.text = anime.title
            Glide.with(this@DetailAnimeActivity)
                .load(anime.poster)
                .into(binding.contentPoster)
            binding.includeDetailAnime.tvContentGenre.text = anime.genres
            binding.includeDetailAnime.tvContentEpisode.text = anime.episodes
            binding.includeDetailAnime.tvContentRating.text = anime.rating.toString()
            binding.includeDetailAnime.tvContentStatus.text = anime.status
            binding.includeDetailAnime.tvContentStudio.text = anime.studios
            binding.includeDetailAnime.tvContentRelease.text = anime.release

            binding.includeSynopsis.tvContentSynopsis.text = anime.synopsis
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_anime, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                Log.i("DetailAnimeActivity", "ACTION_SHARE")
                val detailAnimeView = findViewById<View>(R.id.include_detail_anime)
                val image = createImageFromView(detailAnimeView)
                val uri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, image, null, null))

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createImageFromView(view: View): Bitmap {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


}