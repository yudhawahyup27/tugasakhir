package com.nairobi.tugasakhir.superadmin.keladmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SimpleAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.admin.dashboardAdmin
import com.nairobi.tugasakhir.superadmin.keladmin.sp_tambahAdmin
import kotlinx.android.synthetic.main.activity_list_data_admin.*
import kotlinx.android.synthetic.main.activity_list_data_admin.btn_backdasboard_tambahadmin

class listDataAdmin : AppCompatActivity(), View.OnClickListener {

     lateinit var db : FirebaseFirestore
    lateinit var db1 : CollectionReference
    lateinit var alAdmin :ArrayList<HashMap<String,Any>>
    lateinit var adapterAdmin : SimpleAdapter

    val COLLECTION = "Admin"
    val F_IDADMIN = "id"
    val F_NAMAADMIN = "nama_admin"
    val F_EMAIL = "email_admin"
    val F_TELEPON = "telepon_admin"

    var emailAdmin = ""
    var docId = ""

    var searchId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_admin)

        alAdmin = ArrayList()

        lsadmin.setOnItemClickListener(itemClick)
        btn_tambahadmin.setOnClickListener(this)
        btn_backdasboard_tambahadmin.setOnClickListener(this)



        search()
    }


    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
            if (e != null) Log.d("firestore", e.message.toString())
            showDataAdmin()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_tambahadmin -> {
                Intent(this, sp_tambahAdmin::class.java).also {
                    it.putExtra("code", "0")
                    it.putExtra("email_admin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_backdasboard_tambahadmin -> {
                Intent(this, dashboardAdmin::class.java).also {
                    it.putExtra("email_admin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
        }
    }

        val itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
            val hm : HashMap<String,Any> = alAdmin.get(position)
            docId  = hm.get(F_EMAIL).toString()
            Intent(this, sp_tambahAdmin::class.java).also {
                it.putExtra("code", "1")
                it.putExtra("id", docId)
                it.putExtra("email_admin", emailAdmin)
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                finish()
            }
        }

    fun showDataAdmin(){
        db.collection(COLLECTION).orderBy("nama_admin", Query.Direction.ASCENDING).get().addOnSuccessListener { result ->
            alAdmin.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NAMAADMIN,doc.get(F_NAMAADMIN
                ).toString())
                hm.set(F_EMAIL,doc.get(F_EMAIL).toString())
                hm.set(F_TELEPON,doc.get(F_TELEPON).toString())
                alAdmin.add(hm)
            }
            adapterAdmin = SimpleAdapter(this, alAdmin, R.layout.listadmin,
                arrayOf(F_NAMAADMIN
                    , F_EMAIL, F_TELEPON),
                intArrayOf( R.id.ls_namaAdmin, R.id.ls_email, R.id.ls_telepon))
            lsadmin.adapter = adapterAdmin

        }
    }

    fun search(){
        searchadmin.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    searchId = p0
//                    searchCode = "1"
                }
                db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
                db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        firebaseFirestoreException.message?.let {
                            Log.e("firestore :", it)
                        }
                    }
                    showSearch()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                db = FirebaseFirestore.getInstance()
                db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
                    if (e != null) Log.d("firestore", e.message.toString())
                    showDataAdmin()
                }
                return false
            }
        })
    }

    private fun showSearch(){
        val showDetail = db1.whereEqualTo("nama_admin", searchId)
        showDetail.get().addOnSuccessListener { result ->
            alAdmin.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NAMAADMIN,doc.get(F_NAMAADMIN).toString())
                hm.set(F_EMAIL,doc.get(F_EMAIL).toString())
                hm.set(F_TELEPON, doc.get(F_TELEPON).toString())
                alAdmin.add(hm)
            }
            adapterAdmin = SimpleAdapter(this, alAdmin, R.layout.listadmin,
                arrayOf(F_NAMAADMIN, F_EMAIL, F_TELEPON),
                intArrayOf(R.id.ls_namaAdmin, R.id.ls_email, R.id.ls_telepon))
            lsadmin.adapter = adapterAdmin
//            var jml : Int = alPetugas.count()
//            jmlah_warga.setText(Integer.toString(jml))
        }
    }
}