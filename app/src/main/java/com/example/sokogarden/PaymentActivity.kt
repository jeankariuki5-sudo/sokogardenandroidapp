package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        Find views by use of their Id's

        val txtname = findViewById<TextView>(R.id.txtProductName)
        val txtcost = findViewById<TextView>(R.id.txtProductCost)
        val imgproduct = findViewById<ImageView>(R.id.imgProduct)

//        retrieve the data of the prev activity
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost",0)
        val product_photo = intent.getStringExtra("product_photo")


//        update the textview with the data first from the prev act
        txtname.text = name
        txtcost.text = "Ksh $cost"

//        specify the image url
        val imageUrl = "https://kbenkamotho.alwaysdata.net/static/images/$product_photo"

        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgproduct)

//        Find the edit text and the pay now button by use of their ids
        val phone = findViewById<EditText>(R.id.phone)
        val btnpay = findViewById<Button>(R.id.pay)

        btnpay.setOnClickListener {
            val api = "https://kbenkamotho.alwaysdata.net/api/mpesa_payment"

//            create a request params
            val data = RequestParams()

//            insert data into the request params
            data.put("amount", cost)
            data.put("phone", phone)

//            import the helper class
            val Helper = ApiHelper(applicationContext)

//            Acces the post function inside the Helper class
            Helper.post(api, data)


//            clear the phone no from the edit text
            phone.text.clear()
        }

    }
}