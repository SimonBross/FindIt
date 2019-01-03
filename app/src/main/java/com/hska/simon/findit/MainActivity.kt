package com.hska.simon.findit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.hska.simon.findit.database.DataAccessHelper
import com.hska.simon.findit.model.Job
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    var arrayAdapter:ArrayAdapter<Job>? = null
    var toggle:ToggleButton? = null
    private var dataAccessHelper: DataAccessHelper? = null
    private var jobs:List<Job>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addbtn.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCheckedChanged(compountButton:CompoundButton, b:Boolean) {
        if(b)
            Toast.makeText(getApplicationContext(), "checked", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_working_student -> {
                arrayAdapter = ArrayAdapter<Job>(this, R.layout.list_layout, R.id.listTextView)
                val listView = findViewById<ListView>(R.id.offerlist)
                listView.setAdapter(arrayAdapter)

                dataAccessHelper = DataAccessHelper(this)
                jobs = ArrayList()
                jobs = dataAccessHelper?.getAllWorkingStudent()
                if(jobs?.size != 0){
                    arrayAdapter?.addAll(jobs)
                    arrayAdapter?.notifyDataSetChanged()
                }

                toggle = findViewById(R.id.myToggleButton)
                toggle!!.setOnCheckedChangeListener(this)
            }
            R.id.nav_internship -> {
                arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
                val listView = findViewById<ListView>(R.id.offerlist)
                listView.setAdapter(arrayAdapter)

                dataAccessHelper = DataAccessHelper(this)
                jobs = ArrayList()
                jobs = dataAccessHelper?.getAllInternship()
                if(jobs?.size != 0){
                    arrayAdapter?.addAll(jobs)
                    arrayAdapter?.notifyDataSetChanged()
                }
            }
            R.id.nav_thesis -> {
                arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
                val listView = findViewById<ListView>(R.id.offerlist)
                listView.setAdapter(arrayAdapter)

                dataAccessHelper = DataAccessHelper(this)
                jobs = ArrayList()
                jobs = dataAccessHelper?.getAllThesis()
                if(jobs?.size != 0){
                    arrayAdapter?.addAll(jobs)
                    arrayAdapter?.notifyDataSetChanged()
                }
            }
            R.id.nav_settings -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?){

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                var job = Job()
                //job.id = data!!.getExtras().getInt("id")
                job.type = data!!.getExtras().getInt("type")
                job.company = data.getExtras().getString("company")
                job.position = data.getExtras().getString("position")
                job.description = data.getExtras().getString("description")
                job.isfavorite = data.getExtras().getInt("isfavorite")
                arrayAdapter?.add(job)
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_LONG).show()

            }
        }

    }
}
