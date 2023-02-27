package com.nairobi.tugasakhir.admin.karyawan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nairobi.tugasakhir.R

class ad_tambahKaryawan : AppCompatActivity() {


    lateinit var db : FirebaseFirestore
    lateinit var db1 : CollectionReference

    val COLLECTION = "Karyawan"
    val F_NAMAKARYAWAN = "nama_karyawan"
    val F_EMAILKARYAWAN = "email_karyawan"
    val F_TELEPONKARYAWAN = "telepon_karyawan"
    val F_PASSWORDKARYAWAN = "password_karyawan"

        val COLLECTION1 = "User"
    val F_USERNAME = "username"
    val F_PASSWROD = "password"
    val F_NAMAAKUN = "nama"
    val F_ROLE = "role"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_tambah_karyawan)



    }
}