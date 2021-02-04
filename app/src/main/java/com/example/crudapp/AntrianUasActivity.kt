package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.AntrianUas
import kotlinx.android.synthetic.main.activity_antrian_uas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AntrianUasActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var antrianUasAdapter: AntrianUasAdapter
    //menampilkan semua data //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antrian_uas)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadAntrian()
    }

    fun loadAntrian(){
        CoroutineScope(Dispatchers.IO).launch {
            val allAntrian = db.AntrianDao().getAllAntrian()
            Log.d("AntrianUasActivity", "dbResponse: $allAntrian")
            withContext(Dispatchers.Main) {
                antrianUasAdapter.setData(allAntrian)
            }
        }
    }

    fun setupListener() {
        btn_createAntrianuas.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        antrianUasAdapter = AntrianUasAdapter(arrayListOf(), object: AntrianUasAdapter.OnAdapterListener {
            override fun onClick(antrianUas: AntrianUas) {
                // read detail
                intentEdit(antrianUas.id, Constant.TYPE_READ)
            }

            override fun onDelete(antrianuas: AntrianUas) {
                // delete data
                deleteDialog(antrianuas)
            }

            override fun onUpdate(antrianuas: AntrianUas) {
                // edit data
                intentEdit(antrianuas.id, Constant.TYPE_UPDATE)
            }

        })
        list_antrianuas.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = antrianUasAdapter
        }
    }

    fun intentEdit(siswaId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditAntrianUasActivity::class.java)
                .putExtra("intent_id", siswaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(antrianuas: AntrianUas) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.AntrianDao().deleteAntrian(antrianuas)
                    loadAntrian()
                }
            }
        }
        alertDialog.show()
    }
}