package com.spoonart.datasource

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spoonart.datasource.adapter.AnimalAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = AnimalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            viewModel.invalidate()
        }


        viewModel.getInitialLoading().observe(this, Observer {
            adapter.setState(it)
        })

        viewModel.getAnimals().observe(this, Observer { animals ->
            adapter.submitList(animals)
        })

        viewModel.networkState().observe(this, Observer { state ->
            adapter.setState(state)
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}
