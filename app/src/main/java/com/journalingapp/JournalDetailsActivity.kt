package com.journalingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.journalingapp.model.Journal
import com.journalingapp.viewmodel.JournalViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class JournalDetailsActivity : AppCompatActivity() {
    private lateinit var mTitle: TextView
    private lateinit var mBody: TextView
    private lateinit var mDate: TextView
    private var id: Long = -1L
    private val journalViewModel: JournalViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_details)
        mDate = findViewById(R.id.journal_details_date_tv)
        mTitle = findViewById(R.id.journal_details_title_tv)
        mBody = findViewById(R.id.journal_details_body_tv)

        id = if (intent.hasExtra("id")) intent.getStringExtra("id")!!.toLong()
            else -1L
        if (id != -1L) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    journalViewModel.getJournal(id).collect { data ->
                        setJournalToViews(data)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("savedJournalIntent", intent)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable("savedJournalIntent", Intent::class.java)
            } else {
                savedInstanceState.getParcelable("savedJournalIntent")
            }
        if (savedIntent != null) {
            intent = savedIntent
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setJournalToViews(journal: Journal) {
        mDate.text = SimpleDateFormat("E dd MMM yyyy, hh:mm a").format(journal.updatedAt)
        mTitle.text = journal.title
        mBody.text = journal.body
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(applicationContext, UpdateJournalActivity::class.java)
                intent.putExtra("id", id.toString())
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}