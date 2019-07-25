package com.example.androidwithkotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.R.attr.start
import android.R.attr.bitmap
import android.provider.MediaStore
import android.R.attr.data
import android.app.Activity
import java.io.IOException


class MainActivity : AppCompatActivity() ,View.OnClickListener {


    private lateinit var show_btn: Button
    private lateinit var message: String
    private lateinit var phone_etx: EditText
    private lateinit var image_view: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        show_btn = findViewById(R.id.show_btn)
        phone_etx = findViewById(R.id.phone_etx)
        image_view = findViewById(R.id.imageView)
        show_btn.setOnClickListener(this)
        image_view.setOnClickListener(this)
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    @SuppressLint("NewApi")
    override fun onClick(view: View?) {
        if (view == show_btn) {
            message = phone_etx.text.toString()
            Toast.makeText(
                applicationContext, "Your Phone Number Is : " + message
                , Toast.LENGTH_LONG
            ).show()
        } else if (view == image_view) {
            var galleryIntent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.setType("image/*")

            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //permision array
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permission, IMAGE_PICK_CODE)

            } else {
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), IMAGE_PICK_CODE);

            }

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
        }

    }
}
