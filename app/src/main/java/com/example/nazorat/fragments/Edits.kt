package com.example.nazorat.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.nazorat.R
import com.example.nazorat.databinding.FragmentEditsBinding
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.mydb.MyDb
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Edits : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: FragmentEditsBinding? = null
    private var PICK_IMAGE_REQUEST = 1
    lateinit var bitmap: Bitmap
    lateinit var myDb: MyDb
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEditsBinding.inflate(inflater, container, false)

        binding!!.apply {
            myDb = MyDb(requireContext())
            val viewModel = getViewModel(requireActivity())
            val m = arguments?.getInt("mah")!!
            val mahsulot = mahsulotlarList.filter { it.id == m}[0]
            narxi.setText(mahsulot.narxi)
            nameEt.setText(mahsulot.nomi)
            olchami.setText(mahsulot.olchami)
            rangi.setText(mahsulot.rangi)
            fasli.setText(mahsulot.fasli)
            rasm.setImageBitmap(mahsulot.rasm)
            val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let { selectedUri ->
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedUri)
                    rasm.setImageBitmap(bitmap)
                }
            }
            rasm.setOnClickListener {
                getContent.launch("image/*")
            }
            save.setOnClickListener {
                val name = nameEt.text.toString()
                val narxi1 = narxi.text.toString()
                val rangi1 = rangi.text.toString()
                val olchami1 = olchami.text.toString()
                val fasli1 = fasli.text.toString()
                if (name.isNotEmpty() && narxi1.isNotEmpty() && rangi1.isNotEmpty() && olchami1.isNotEmpty() && fasli1.isNotEmpty()) {
                    rasm.isDrawingCacheEnabled = true
                    rasm.buildDrawingCache()
                    val bitmap = (rasm.drawable as BitmapDrawable).bitmap
                    myDb.updateMahsulot(Mahsulotlar(id = m,nomi = name, narxi = narxi1, olchami = olchami1, rangi = rangi1, fasli = fasli1, rasm = bitmap))
                    mahsulotlarList = myDb.getMahsulot()
                    viewModel.mahsulotlarMutableLiveData!!.value = mahsulotlarList
                    Toast.makeText(requireContext(), "Malumot yangilandi.", Toast.LENGTH_SHORT).show()

                }
            }
        }

        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Edits().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
            binding!!.rasm.setImageBitmap(bitmap)
        }
    }
}