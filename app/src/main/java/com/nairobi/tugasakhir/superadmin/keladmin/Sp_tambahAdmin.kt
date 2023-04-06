package com.nairobi.tugasakhir.superadmin.keladmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nairobi.tugasakhir.R
import kotlinx.android.synthetic.main.activity_sp_tambah_admin.*
import kotlinx.android.synthetic.main.activity_tb_cuti.*

class sp_tambahAdmin : AppCompatActivity() , View.OnClickListener{

    lateinit var db : FirebaseFirestore
    lateinit var db1 : CollectionReference

    val COLLECTION = "Admin"
    val F_NAMAADMIN = "nama_admin"
    val F_EMAILADMIN = "email_admin"
    val F_TELEPONADMIN = "telepon_admin"
    val F_PASSWORDADMIN = "password_admin"


    val COLLECTION1 = "User"
    val F_USERNAME = "username"
    val F_PASSWROD = "password"
    val F_NAMAAKUN = "nama"
    val F_ROLE = "role"
    val F_TKANTOR = "tkantor"

     var id = ""
    var nama = ""
    var email = ""
    var telp = ""
    var password = ""

    var emailAdmin = ""
    var code = ""
    var docId = ""
 var role = F_ROLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_tambah_admin)

        btn_backdasboard_tambahadmin.setOnClickListener(this)
        btn_saveadmin.setOnClickListener(this)
        btn_delete_Admin.setOnClickListener(this)

        var paket: Bundle? = intent.extras
        code = (paket?.getString("code")!!)

        //kondisi untuk tambah warga
        viewCondition()
    }

  override fun onStart() {
        super.onStart()
        if (code == "0"){
            db = FirebaseFirestore.getInstance()
        } else if (code == "1") {
            db = FirebaseFirestore.getInstance()
            db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
            db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    firebaseFirestoreException.message?.let {
                        Log.e("firestore :", it)
                    }
                }
                detailadmin()
            }
        }
    }


    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_backdasboard_tambahadmin -> {
                Intent(this, listDataAdmin::class.java).also {
                    it.putExtra("emailAdmin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_saveadmin -> {

                nama = txin_namaAdmin.text.toString()
                email = txin_emailAdmin.text.toString()
                telp = txin_teleponAdmin.text.toString()
                password = txin_passwordAdmin.text.toString()

                if (nama.isEmpty() || email.isEmpty() || telp.isEmpty() ||
                    password.isEmpty()) {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Lengkapi data terlebih dahulu!")
                        .show()

                }
                else if (email == F_EMAILADMIN) {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf, Email Sudah Terdaftar Mohon Dicek Kembali!")
                        .show()
                }
                else if (telp.length != 12 && telp.length != 13 ) {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Maaf, Nomer Telepon Karyawan Minimal 12 atau 13 DIGIT!")
                        .show()
                }
                else {
                    val hm = HashMap<String, Any>()
                    hm.set(F_NAMAADMIN, txin_namaAdmin.text.toString())
                    hm.set(F_EMAILADMIN, txin_emailAdmin.text.toString())
                    hm.set(F_TELEPONADMIN, txin_teleponAdmin.text.toString())
                    hm.set(F_PASSWORDADMIN, txin_passwordAdmin.text.toString())

                    val hm1 = HashMap<String, Any>()
                    hm1.set(F_USERNAME, txin_emailAdmin.text.toString())
                    hm1.set(F_PASSWROD, txin_passwordAdmin.text.toString())
                    hm1.set(F_NAMAAKUN, txin_namaAdmin.text.toString())
                    hm1.set(F_ROLE, "admin")
                    db.collection(COLLECTION).document(txin_emailAdmin.text.toString()).set(hm)
                        .addOnSuccessListener {
                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Data admin berhasil disimpan")
                                .setConfirmClickListener {
                                    Intent(this, listDataAdmin::class.java).also {
                                        it.putExtra("emailAdmin", emailAdmin)
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                }
                                .show()
                        }.addOnFailureListener { e ->
                        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data admin gagal disimpan!")
                            .show()
                    }
                    db.collection(COLLECTION1).document(txin_emailAdmin.text.toString()).set(hm1)
                }
            }
            R.id.btn_delete_Admin -> {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Apakah anda yakin untuk menghapus data?")
                    .setConfirmButton("Ya", SweetAlertDialog.OnSweetClickListener {
                        db.collection(COLLECTION).whereEqualTo(F_EMAILADMIN,docId).get().addOnSuccessListener {
                                result ->
                            for (doc : QueryDocumentSnapshot in result){
                                db.collection(COLLECTION).document(doc.id).delete()
                                    .addOnSuccessListener {
                                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Data Admin berhasil dihapus")
                                            .setConfirmClickListener {
                                                Intent(this, listDataAdmin::class.java).also {
                                                    it.putExtra("emailAdmin", emailAdmin)
                                                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(it)
                                                    finish()
                                                }
                                            }
                                            .show()
                                    }
                            }
                        }
                        db.collection("User").whereEqualTo(F_EMAILADMIN,txin_emailAdmin.text.toString()).get()
                    })
                    .setCancelButton("Batal", SweetAlertDialog.OnSweetClickListener {
                        it.dismissWithAnimation()
                    })
                    .show()
            }
        }
    }

    fun detailadmin() {

        title_admin.setText("DETAIL ADMIN")

        val showDetail = db1.whereEqualTo("email_admin", docId)
        showDetail.get().addOnSuccessListener { result ->
            for (doc in result) {

                nama = doc.get(F_NAMAADMIN).toString()
                email = doc.get(F_EMAILADMIN).toString()
                telp = doc.get(F_TELEPONADMIN).toString()
                password = doc.get(F_PASSWORDADMIN).toString()

                txin_namaAdmin.setText(nama)
                txin_teleponAdmin.setText(telp)
                txin_emailAdmin.setText(email)
                txin_passwordAdmin.setText(password)
            }
        }
    }

    private fun viewCondition(){
        //kondisi untuk tambah petugas
        if (code == "0"){
            var paket: Bundle? = intent.extras
            emailAdmin = (paket?.getString("email_admin")!!)
        }
        //kondisi untuk detail petugas
        else if (code == "1") {
            var paket: Bundle? = intent.extras
            emailAdmin = (paket?.getString("email_admin")!!)
            docId = (paket?.getString("id")!!)
                btn_delete_Admin.visibility = View.VISIBLE
            txin_emailAdmin.isEnabled = false
        }
    }
}