package com.nairobi.tugasakhir.superadmin.kantor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nairobi.tugasakhir.R

class sp_tambahKantor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_tambah_kantor)

        lateinit var db : FirebaseFirestore
        lateinit var db1 : CollectionReference

         val COLLECTION = "kantor"
         val F_NAMAKANTOR = "nama_kantor"
         val F_ALAMAT      = "alamat_kantor"

    }


}