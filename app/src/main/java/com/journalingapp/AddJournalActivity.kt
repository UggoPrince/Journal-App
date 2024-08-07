package com.journalingapp

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journalingapp.databinding.ActivityAddJournalBinding
import com.journalingapp.model.Journal
import com.journalingapp.util.toast
import com.journalingapp.viewmodel.JournalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

/*
 * This class adds a journal
 */
class AddJournalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddJournalBinding
    private lateinit var mTitle: EditText
    private lateinit var mBody: EditText
    private val journalViewModel: JournalViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJournalBinding.inflate(layoutInflater)
//        val actionBar: ActionBar? = supportActionBar
//        actionBar?.setHomeButtonEnabled(true)
//        actionBar?.setDisplayHomeAsUpEnabled(true)
        // setSupportActionBar(binding)
        setContentView(R.layout.activity_add_journal)
        mTitle = findViewById(R.id.editTextJournalTitle)
        mBody = findViewById(R.id.editTextJournalBody)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_journal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                save()
                true
            }
            R.id.action_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun save() {
        val title: String = mTitle.text.toString()
        val body: String = mBody.text.toString()
        val date = Date()
        if (TextUtils.isEmpty(title)) {
            toast(this, "Enter a title!", Toast.LENGTH_SHORT)
            return
        }
        if (TextUtils.isEmpty(body)) {
            toast(this, "Enter a Journal", Toast.LENGTH_SHORT)
            return
        }
        val journal = Journal(title = title, body = body, date = date, updatedAt = date)
        journalViewModel.addJournal(journal)
        finish()
    }
}