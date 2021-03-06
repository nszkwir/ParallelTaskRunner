package com.spitzer.paralleltaskrunner

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.spitzer.paralleltaskrunner.databinding.ActivityMainBinding
import com.spitzer.paralleltaskrunner.taskrunner.TaskManager
import com.spitzer.paralleltaskrunner.taskrunner.TaskRunnerListener
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task

class MainActivity : AppCompatActivity(), TaskRunnerListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskManager: TaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        taskManager = TaskManager()

        binding.fab.setOnClickListener {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            Log.i("TASK", "MainActivity runCatTasks")
            taskManager.runDogTasks(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun errorRunningTasks() {
        Log.i("TASK", "MainActivity errorRunningTasks")
    }

    override fun uncompleteRunningTasks(uncompleteTasks: ArrayList<Task>) {
        Log.i("TASK", "MainActivity uncompleteRunningTasks")
    }

    override fun successRunningTasks() {
        Log.i("TASK", "MainActivity successRunningTasks")

    }
}