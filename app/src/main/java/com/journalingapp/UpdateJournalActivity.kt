package com.journalingapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.journalingapp.model.Journal
import com.journalingapp.util.toast
import com.journalingapp.viewmodel.JournalViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date


class UpdateJournalActivity : AppCompatActivity() {
    private lateinit var mTitle: EditText
    private lateinit var mBody: EditText
    private var id: Long = -1L
    private lateinit var journal: Journal
    private val journalViewModel: JournalViewModel by viewModel()
    private var mBodyTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val newText = s.toString()
            if (newText != journal.body) { enableUpdate(true) }
            else {enableUpdate(false)}
        }
    }
    private var mTitleTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val newText = s.toString()
            if (newText != journal.title) { enableUpdate(true) }
            else {enableUpdate(false)}
        }
    }
    private var canUpdate = false
    private lateinit var backPressedDispatcher: OnBackPressedDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_update_journal)
        mTitle = findViewById(R.id.editTextUpdateTitle)
        mBody = findViewById(R.id.editTextUpdateBody)
        id = intent.getStringExtra("id")!!.toLong()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                journalViewModel.getJournal(id).collect { data ->
                    setJournalToViews(data)
                }
            }
        }

        // Get the back button dispatcher
        backPressedDispatcher = onBackPressedDispatcher

        // Register a callback for the back button press event
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (canUpdate) {
                    handleUpdate()
                }
                finish()
            }
        }
        backPressedDispatcher.addCallback(this, callback)
        mBody.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Scroll to the current cursor position
                val cursorPosition = mBody.selectionStart
                mBody.setSelection(cursorPosition)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update_journal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                if (canUpdate) {
                    handleUpdate()
                } else {
                    toast(this, "No changes made!", Toast.LENGTH_SHORT)
                }
                true
            }
            R.id.action_cancel_update -> {
                finish()
                true
            }
            android.R.id.home -> {
                backPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("savedIntent", intent)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable("savedIntent", Intent::class.java)
            } else {
                savedInstanceState.getParcelable("savedIntent")
            }
        if (savedIntent != null) {
            intent = savedIntent
        }
    }

    private fun enableUpdate(truthy: Boolean) {
        canUpdate = truthy
    }

    private fun handleUpdate() {
        val title: String = mTitle.text.toString()
        val body: String = mBody.text.toString()
        updateJournal(title, body)
        finish()
        toast(this, "Journal Updated.", Toast.LENGTH_SHORT)
    }

    private fun updateJournal(title: String, body: String) {
        val date = Date()
        val newUpdate = Journal(id, title, body, journal.date, date)
        journalViewModel.updateJournal(newUpdate)
    }

    private fun setEditTextChangeListener() {
        mTitle.addTextChangedListener(mTitleTextWatcher)
        mBody.addTextChangedListener(mBodyTextWatcher)
    }

    private fun setJournalToViews(data: Journal) {
        mTitle.setText(data.title)
        mBody.setText(data.body)
        journal = data
        setEditTextChangeListener()
    }
}