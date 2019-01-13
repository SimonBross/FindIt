package com.hska.simon.findit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import com.hska.simon.findit.database.DataAccessHelper
import com.hska.simon.findit.model.Job
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var arrayAdapter: ArrayAdapter<Job>? = null
    private var dataAccessHelper: DataAccessHelper? = null
    private var jobs: List<Job>? = null
    var currentNavItem: Int? = null

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
        if (currentNavItem == null)
            currentNavItem = 99
        loadDataFromDatabase(currentNavItem!!)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            loadDataFromDatabase(currentNavItem!!)
        } else {
            loadDataFromDatabase(currentNavItem!!)
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        var searchView: SearchView = findViewById(R.id.mySearchBar)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                loadDataFromDatabase(newText)
                if(jobs?.size == 0){
                    var arrayAdapterEmpty= ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1)
                    arrayAdapterEmpty.add("Keine Stellen gefunden")
                    val listView: ListView = findViewById(R.id.offerlist)
                    listView.setAdapter(arrayAdapterEmpty)
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String): Boolean {
                return true
            }
        })
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

    fun loadDataFromDatabase(jobType: Int) {
        dataAccessHelper = DataAccessHelper(this)
        jobs = ArrayList()
        if (jobType == 99)
            jobs = dataAccessHelper?.getAllFavorites()
        else
            jobs = dataAccessHelper?.getAllJobs(jobType)
        if (jobs?.size != 0) {
            arrayAdapter?.addAll(jobs)
            arrayAdapter?.notifyDataSetChanged()
        }

        arrayAdapter = CustomArrayAdapter(this, jobs)
        val listView = findViewById<ListView>(R.id.offerlist)
        listView.setAdapter(arrayAdapter)
    }

    fun loadDataFromDatabase(keyword: String) {
        dataAccessHelper = DataAccessHelper(this)
        jobs = ArrayList()
        jobs = dataAccessHelper?.getAllJobs(keyword)
        if (jobs?.size != 0) {
            arrayAdapter?.addAll(jobs)
            arrayAdapter?.notifyDataSetChanged()
        }

        arrayAdapter = CustomArrayAdapter(this, jobs)
        val listView = findViewById<ListView>(R.id.offerlist)
        listView.setAdapter(arrayAdapter)
    }

//    @Override
//    public boolean onLongClick (View v) {
//        Intent mapsIntent = new Intent(context, MapsActivity.class);
//        int listSize = getListSize(jobs);
//        for(int i = 0; i < listSize; i++){
//            mapsIntent.putExtra(jobs[i].getId() + "", jobs[i].getCompany());
//        }
//        context.startActivity(mapsIntent);
//        return true;

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_favorites -> {
                loadDataFromDatabase(99)
                currentNavItem = 99
            }
            R.id.nav_working_student -> {
                loadDataFromDatabase(0)
                currentNavItem = 0
            }
            R.id.nav_internship -> {
                loadDataFromDatabase(1)
                currentNavItem = 1
            }
            R.id.nav_thesis -> {
                loadDataFromDatabase(2)
                currentNavItem = 2
            }
            R.id.nav_settings -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            loadDataFromDatabase(currentNavItem!!)
        }
//        if(requestCode == 2){
//            if(resultCode == Activity.RESULT_OK){
//                var job = Job()
//                job.type = data!!.getExtras().getInt("type")
//                job.company = data.getExtras().getString("company")
//                job.position = data.getExtras().getString("position")
//                job.description = data.getExtras().getString("description")
//                job.isfavorite = data.getExtras().getInt("isfavorite")
//                arrayAdapter?.add(job)
//                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_LONG).show()
//                loadDataFromDatabase(0)
//            }
//        }
    }
}
