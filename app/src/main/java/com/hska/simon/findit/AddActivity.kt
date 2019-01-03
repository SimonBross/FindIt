package com.hska.simon.findit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.hska.simon.findit.database.DataAccessHelper
import com.hska.simon.findit.model.Job

import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    private var dataAccessHelper: DataAccessHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        var spinner: Spinner = findViewById(R.id.spinner1)
        var adapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this, R.array.job_types, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        spinner.setOnItemSelectedListener(this)

        val companyTextField: EditText = findViewById(R.id.companyTextField)
        val positionTextField: EditText = findViewById(R.id.positionTextField)
        val descriptionTextField: EditText = findViewById(R.id.descriptionTextField)
        val saveButton: FloatingActionButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener { view ->

            dataAccessHelper = DataAccessHelper(getApplicationContext())
            var type: Int = spinner.getSelectedItemId().toInt()
            var company: String = companyTextField.getText().toString()
            var position: String = positionTextField.getText().toString()
            var description: String = descriptionTextField.getText().toString()

            dataAccessHelper?.addJob(Job(type, company, position, description, 0))


            var returnIntent: Intent = Intent()
            returnIntent.putExtra("type", spinner.getSelectedItemId().toInt())
            returnIntent.putExtra("company", companyTextField.getText().toString())
            returnIntent.putExtra("position", positionTextField.getText().toString())
            returnIntent.putExtra("description", descriptionTextField.getText().toString())
            returnIntent.putExtra("isfavorite", 0)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }
}
