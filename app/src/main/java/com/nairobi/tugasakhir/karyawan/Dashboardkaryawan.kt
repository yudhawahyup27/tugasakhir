package com.nairobi.tugasakhir.karyawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.auth.LoginPage
import com.nairobi.tugasakhir.karyawan.kelabsen.Absen
import com.nairobi.tugasakhir.karyawan.kelcuti.tbCuti
import kotlinx.android.synthetic.main.activity_dashboardkaryawan.*

class Dashboardkaryawan : AppCompatActivity() , View.OnClickListener{
    lateinit var db : CollectionReference
    var nikUser = ""
    val COLLECTION = "User"
    var F_USERNAME = "username"
    val F_PASSWORD = "password"
    val F_NAMA = "nama"
    val F_ROLE = "role"

    var datanik =""

    lateinit var db2 : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboardkaryawan)
        btn_logout.setOnClickListener(this)
        kelolaprofil.setOnClickListener(this)
        btnCuti.setOnClickListener(this)
        btnkelolaabsensi.setOnClickListener(this)

        var code: Bundle? = intent.extras
        nikUser = (code?.getString("nikUser")!!)
    }
    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance().collection(COLLECTION)
        db2 = FirebaseFirestore.getInstance()
        if (nikUser != null){
           F_USERNAME = nikUser
        }
    }
      override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.kelolaprofil-> {
                Intent(this,kelolaProfil::class.java).also {
                    it.putExtra("nikUser", nikUser)
                    startActivity(it)
                    finish()
                }
            }
            R.id.btnCuti -> {
                Intent(this, tbCuti::class.java).also {
                    it.putExtra("nikUser", nikUser)
                    startActivity(it)
                    finish()
                }
            }
  R.id.btnkelolaabsensi -> {
                Intent(this, Absen::class.java).also {
                    it.putExtra("nikUser", nikUser)
                    startActivity(it)
                    finish()
                }
            }
              R.id.btn_logout -> {
//                Toast.makeText(this, "tes", Toast.LENGTH_SHORT).show()
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Apakah anda yakin keluar aplikasi?")
                    .setConfirmButton("Ya", SweetAlertDialog.OnSweetClickListener {
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        finish()
                    })
                    .setCancelButton("Tidak", SweetAlertDialog.OnSweetClickListener {
                        it.dismissWithAnimation()
                    })
                    .show()
            }
        }
    }

}