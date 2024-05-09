package com.journal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.journal.databinding.ActivityMainBinding
import com.journal.model.Journal
import com.journal.util.Adapter
import com.journal.viewmodel.JournalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ActionMode.Callback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var recyclerViewAdapter: Adapter? = null
    private lateinit var noDataImage: ImageView
    private val journalViewModel: JournalViewModel by viewModel()
    private var actionMode: ActionMode? = null
    private var tempSelectedItems: IntArray? = null
    private lateinit var backPressedDispatcher: OnBackPressedDispatcher
    var deleteMenuIcon: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.journal_rv)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        noDataImage = findViewById(R.id.no_data_image)
        noDataImage.visibility = View.GONE
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.fabAddJournal.setOnClickListener {
            val addJournalIntent = Intent(this@MainActivity, AddJournalActivity::class.java)
            startActivity(addJournalIntent)
            // tempShowCheckboxes = false
        }
        // Get the back button dispatcher
        backPressedDispatcher = onBackPressedDispatcher

        // Register a callback for the back button press event
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Adapter.reset()
                finish()
            }
        }
        backPressedDispatcher.addCallback(this, callback)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                journalViewModel.allJournals.collect { data ->
                    loadAllJournals(data)
                }
            }
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                journalViewModel.allJournals.collect { data ->
//                    loadAllJournals(data)
//                }
//            }
        }
    }

    private fun enableFab(enable: Boolean) {
        binding.fabAddJournal.isEnabled = enable
    }

    // fun setTempShowCheckboxes(truthy: Boolean) {tempShowCheckboxes = truthy}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.putIntArray("selectedItems", Adapter.checkedPosArray)
        outState.putBoolean("showCheckboxes", Adapter.showCheckboxes)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItems = savedInstanceState.getIntArray("selectedItems")
        val showCheckboxes = savedInstanceState.getBoolean("showCheckboxes")
        selectedItems?.let {
            tempSelectedItems = selectedItems
            if (showCheckboxes) {
                // Adapter.setSelectedItemsArray(tempSelectedItems!!)
                startActionMode()
                enableFab(enable = false)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        noDataImage.visibility = View.GONE
        // tempShowCheckboxes = false
    }

    override fun onDestroy() {
        super.onDestroy()
        // Adapter.reset()
    }

    private fun loadAllJournals(data: List<Journal>) {
        if (data.isNotEmpty()) {
            noDataImage.visibility = View.GONE
            // if (recyclerViewAdapter == null) {
            // if (!Adapter.showCheckboxes) {Adapter.checkedPosArray = IntArray(data.size) { 0 }}
            recyclerViewAdapter = Adapter(data, this)
            recyclerView.adapter = recyclerViewAdapter
            // }
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            noDataImage.visibility = View.VISIBLE
        }
    }

    fun showSelectAllCheckbox(show: Boolean) {
        if (show) {
            // Start action mode
            startActionMode()
            enableFab(enable = false)
        } else {
            endActionMode()
            enableFab(enable = true)
        }
    }

    private fun startActionMode() {
        actionMode = startSupportActionMode(this)
        // recyclerViewAdapter?.notifyDataSetChanged()
    }

    private fun endActionMode() {
        recyclerViewAdapter?.hideCheckBoxes()
        actionMode = null
        Adapter.itemSelectedCount = 0
    }

    private val actionModeCallback = object : ActionMode.Callback {
        var menuActionMode: Menu? = null
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
            menuActionMode = mode?.menu
            val customView = LayoutInflater
                .from(this@MainActivity)
                .inflate(R.layout.delete_journals_toolbar, null)
            mode?.customView = customView
            val checkBox = customView.findViewById<CheckBox>(R.id.selectAllCheckbox)
            checkBox.isChecked = Adapter.selectAllButtonChecked
            checkBox.setOnClickListener {
                Adapter.selectAllButtonChecked = checkBox.isChecked
                Adapter.itemSelectedCount =
                    if (checkBox.isChecked) recyclerViewAdapter!!.itemCount else 0
                checkBox.text = "${Adapter.itemSelectedCount} selected"
                mode!!.menu.getItem(R.id.action_delete_Journal).isEnabled = Adapter.itemSelectedCount > 0
                recyclerViewAdapter?.toggleAllCheckboxes(checkBox.isChecked)
            }
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            showCheckboxCheckedCount()
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.action_delete_Journal -> {
                    deleteJournals()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            showSelectAllCheckbox(false)
        }

        fun enableDeleteIcon() {
            this.menuActionMode?.getItem(R.id.action_delete_Journal)?.isEnabled =
                Adapter.itemSelectedCount > 0
        }
    }

    private fun deleteJournals() {
        lifecycleScope.launch {
            if (Adapter.selectAllButtonChecked) {
            } else {
                Adapter.getSelectedItemList().forEach { position ->
                    journalViewModel.deleteJournal(recyclerViewAdapter!!.getJournal(position))
                    Log.d("POSITION", position.toString())
                }
            }
            actionMode?.finish()
            onDestroyActionMode(actionMode)
        }
    }

    fun showCheckboxCheckedCount() {
        val view = findViewById<CheckBox>(R.id.selectAllCheckbox)
        view.isChecked = Adapter.itemSelectedCount == recyclerViewAdapter?.itemCount
        view.text = "${Adapter.itemSelectedCount} selected"
        if (actionMode != null) deleteMenuIcon?.isEnabled = Adapter.itemSelectedCount > 0
    }

    companion object {
        val instance = MainActivity()
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_action_mode, menu)
        deleteMenuIcon = mode?.menu?.findItem(R.id.action_delete_Journal)
//        deleteMenuIcon?.setOnMenuItemClickListener {
//            deleteJournals()
//
//            true
//        }
        val customView = LayoutInflater
            .from(this@MainActivity)
            .inflate(R.layout.delete_journals_toolbar, null)
        mode?.customView = customView
        val checkBox = customView.findViewById<CheckBox>(R.id.selectAllCheckbox)
        checkBox.isChecked = Adapter.selectAllButtonChecked
        checkBox.setOnClickListener {
            Adapter.selectAllButtonChecked = checkBox.isChecked
            Adapter.itemSelectedCount =
                if (checkBox.isChecked) recyclerViewAdapter!!.itemCount else 0
            checkBox.text = "${Adapter.itemSelectedCount} selected"
            deleteMenuIcon?.isEnabled = Adapter.itemSelectedCount > 0
            recyclerViewAdapter?.toggleAllCheckboxes(checkBox.isChecked)
        }
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        showCheckboxCheckedCount()
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_delete_Journal -> {
                deleteJournals()
                true
            }
            else -> super.onOptionsItemSelected(item!!);
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        showSelectAllCheckbox(false)
    }
}