package com.nairobi.tugasakhir.admin.karyawan

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
import kotlinx.android.synthetic.main.activity_list_karyawan.*

class listKaryawan : AppCompatActivity(), View.OnClickListener {

    lateinit var db : FirebaseFirestore
    lateinit var db1 : CollectionReference
    lateinit var alKayawan :ArrayList<HashMap<String,Any>>
    lateinit var adapterKaryawan: SimpleAdapter

    val COLLECTION = "Karyawan"
    val F_NAMAKARYAWAN = "nama"
    val F_TELEPONKARYAWAN = "telepon_karyawan"
    val F_NIK = "nik"
    val F_TGLLAHIRKARYAWAN = "tgl_lahir"
    val F_ALAMATKARTAWAN = "Alamar_kar"
    val F_JENISKELAMINKARYAWAN = "jenis_kelamin"


    var emailAdmin = ""
    var docId = ""

    var searchId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_karyawan)
        alKayawan = ArrayList()

        lskaryawan.setOnItemClickListener(itemClick)
        btn_tambahkaryawan.setOnClickListener(this)
        btn_backdasboard_karyawan.setOnClickListener(this)
//        btn_editWarga.setOnClickListener(this)
//        btn_hapusWarga.setOnClickListener(this)
//
//        var paket: Bundle? = intent.extras
//        emailAdmin = (paket?.getString("emailAdmin")!!)

        search()
        var jml : Int = alKayawan.count()
        jmlah_karyawan.setText(Integer.toString(jml))
    }
    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
            if (e != null) Log.d("firestore", e.message.toString())
            showDataKaryawan()
        }
    }
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_tambahkaryawan -> {
                Intent(this, ad_tambahKaryawan::class.java).also {
                    it.putExtra("code", "0")
                    it.putExtra("emailadmin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_backdasboard_karyawan -> {
                Intent(this, dashboardAdmin::class.java).also {
                    it.putExtra("emailadmin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
        }
        }
    val itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
        val hm : HashMap<String,Any> = alKayawan.get(position)
        docId  = hm.get(F_NIK).toString()
        Intent(this, ad_tambahKaryawan::class.java).also {
            it.putExtra("code", "1")
            it.putExtra("id", docId)
            it.putExtra("emailAdmin", emailAdmin)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
            finish()
        }
    }
    fun showDataKaryawan(){
        db.collection(COLLECTION).orderBy("nik", Query.Direction.ASCENDING).get().addOnSuccessListener { result ->
            alKayawan.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NIK,doc.get(F_NIK).toString())
                hm.set(F_NAMAKARYAWAN,doc.get(F_NAMAKARYAWAN).toString())
                hm.set(F_TGLLAHIRKARYAWAN,doc.get(F_TGLLAHIRKARYAWAN).toString())
                hm.set(F_ALAMATKARTAWAN, doc.get(F_ALAMATKARTAWAN).toString())
                hm.set(F_TELEPONKARYAWAN, doc.get(F_TELEPONKARYAWAN).toString())
                alKayawan.add(hm)
            }
            adapterKaryawan = SimpleAdapter(this, alKayawan, R.layout.listkaryawan,
                arrayOf(F_NIK, F_NAMAKARYAWAN, F_TGLLAHIRKARYAWAN, F_ALAMATKARTAWAN),
                intArrayOf(R.id.ls_nik, R.id.ls_namakaryawan, R.id.ls_tgllahirkaryawan, R.id.ls_AlamatKaryawan))
            lskaryawan.adapter = adapterKaryawan
            var jml : Int = alKayawan.count()
            jmlah_karyawan.setText(Integer.toString(jml))
        }
    }
    fun search(){
        searchView_karyawan.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    searchId = p0
                    db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
                    db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        if (firebaseFirestoreException != null) {
                            firebaseFirestoreException.message?.let {
                                Log.e("firestore :", it)
                            }
                        }
                        showSearch()
                    }
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                db = FirebaseFirestore.getInstance()
                db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
                    if (e != null) Log.d("firestore", e.message.toString())
                    showDataKaryawan()
                }
                return false
            }
        })
    }
    private fun showSearch(){

        val showDetail = db1.whereEqualTo("nik", searchId)
        showDetail.get().addOnSuccessListener { result ->
            alKayawan.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NIK,doc.get(F_NIK).toString())
                hm.set(F_NAMAKARYAWAN,doc.get(F_NAMAKARYAWAN).toString())
                hm.set(F_TGLLAHIRKARYAWAN,doc.get(F_TGLLAHIRKARYAWAN).toString())
                hm.set(F_ALAMATKARTAWAN, doc.get(F_ALAMATKARTAWAN).toString())
                alKayawan.add(hm)
            }
            adapterKaryawan = SimpleAdapter(this, alKayawan, R.layout.listkaryawan,
                arrayOf(F_NIK, F_NAMAKARYAWAN, F_TGLLAHIRKARYAWAN, F_ALAMATKARTAWAN),
                intArrayOf(R.id.ls_nik, R.id.ls_namakaryawan, R.id.ls_tgllahirkaryawan, R.id.ls_AlamatKaryawan))
            lskaryawan.adapter = adapterKaryawan
        }
    }

}