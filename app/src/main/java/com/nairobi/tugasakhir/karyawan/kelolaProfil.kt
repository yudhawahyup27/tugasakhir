package com.nairobi.tugasakhir.karyawan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nairobi.tugasakhir.R
import kotlinx.android.synthetic.main.activity_kelola_profil.*

class kelolaProfil : AppCompatActivity(), View.OnClickListener  {

    lateinit var db1 : CollectionReference
    lateinit var db : FirebaseFirestore
    val COLLECTION = "Karyawan"
    val F_NAMAKARYAWAN = "nama"
    val F_TELEPONKARYAWAN = "telepon_karyawan"
    val F_NIK = "nik"
    val F_TGLLAHIRKARYAWAN = "tgl_lahir"
    val F_ALAMATKARTAWAN = "Alamar_kar"
    val F_JENISKELAMINKARYAWAN = "jenis_kelamin"

    var nikUser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_profil)
        btn_saveProfilWarga.setOnClickListener(this)

        var paket: Bundle? = intent.extras
        nikUser = (paket?.getString("nikUser")!!)
    }
    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
        db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                firebaseFirestoreException.message?.let {
                    Log.e("firestore :", it)
                }
            }
            detailWarga()
        }
    }
    private fun detailWarga() {
        val showDetail = db1.whereEqualTo("nik", nikUser)
        showDetail.get().addOnSuccessListener { result ->
            for (doc in result) {
                text_nik.setText(nikUser)
                text_nama.setText(doc.get(F_NAMAKARYAWAN).toString())
                text_tgllhr.setText(doc.get(F_TGLLAHIRKARYAWAN).toString())
                text_jeniskelamin.setText(doc.get(F_JENISKELAMINKARYAWAN).toString())
                text_alamat.setText(doc.get(F_ALAMATKARTAWAN).toString())
                text_Telepon.setText(doc.get(F_TELEPONKARYAWAN).toString())
            }
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_saveProfilWarga -> {
                val hm = HashMap<String, Any>()
                hm.set(F_NIK, nikUser)
                hm.set(F_NAMAKARYAWAN, text_nama.text.toString())
                hm.set(F_TGLLAHIRKARYAWAN, text_tgllhr.text.toString())
                hm.set(F_ALAMATKARTAWAN, text_alamat.text.toString())
                hm.set(F_TELEPONKARYAWAN, text_Telepon.text.toString())
                hm.set(F_JENISKELAMINKARYAWAN, text_jeniskelamin)
                db.collection(COLLECTION).document(nikUser).set(hm)
                    .addOnSuccessListener {
                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("No Telepon berhasil diperbarui")
                            .show()
                    }.addOnFailureListener { e ->
                        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No Telepon gagal diperbarui!")
                            .show()
                    }
            }
        }
    }
}