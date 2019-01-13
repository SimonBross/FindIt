package com.hska.simon.findit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.hska.simon.findit.database.DataAccessHelper
import com.hska.simon.findit.model.Job
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    private var dataAccessHelper: DataAccessHelper? = null
    var encodedString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        var window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.colorPrimaryDark)

        var spinner: Spinner = findViewById(R.id.spinner1)
        var adapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this, R.array.job_types, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        val companyTextField: EditText = findViewById(R.id.companyTextField)
        val positionTextField: EditText = findViewById(R.id.positionTextField)
        val descriptionTextField: EditText = findViewById(R.id.descriptionTextField)
        val pdfButton: Button = findViewById(R.id.pdfButton)
        val saveButton: FloatingActionButton = findViewById(R.id.saveButton)

        pdfButton.setOnClickListener { view ->

            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, R.string.choose_pdf.toString()), 1)

        }

        saveButton.setOnClickListener { view ->

            dataAccessHelper = DataAccessHelper(applicationContext)
            var type: Int = spinner.selectedItemId.toInt()
            var company: String = companyTextField.text.toString()
            var position: String = positionTextField.text.toString()
            var description: String = descriptionTextField.text.toString()

            dataAccessHelper?.addJob(Job(type, company, position, description, 0, encodedString))
            setResult(1)


//            var returnIntent: Intent = Intent()
//            returnIntent.putExtra("type", spinner.getSelectedItemId().toInt())
//            returnIntent.putExtra("company", companyTextField.getText().toString())
//            returnIntent.putExtra("position", positionTextField.getText().toString())
//            returnIntent.putExtra("description", descriptionTextField.getText().toString())
//            returnIntent.putExtra("isfavorite", 0)
//            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Intent returns uri, not the whole file
            var uri = resultData!!.data
            var path = uri.path
            var cutPath = path.split(":")[1]
            pdfTextField.text = cutPath
            var inputFile = Files.readAllBytes(Paths.get("storage/emulated/0/" + cutPath))
            var encodedBytes = Base64.getEncoder().encode(inputFile)
            encodedString = String(encodedBytes)
            Log.i("---PDF_BASE64---", encodedString)

        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}