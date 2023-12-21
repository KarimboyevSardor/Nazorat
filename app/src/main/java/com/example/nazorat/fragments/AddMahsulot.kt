package com.example.nazorat.fragments

import android.app.Activity.RESULT_OK
import android.content.ContentProvider
import android.content.ContentProviderResult
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import com.example.nazorat.databinding.FragmentAddMahsulotBinding
import com.example.nazorat.models.Mahsulotlar
import com.example.nazorat.mydb.MyDb
import com.example.nazorat.myobjects.MyObjects.getViewModel
import com.example.nazorat.myobjects.MyObjects.mahsulotlarList
import java.net.URI

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddMahsulot : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var binding: FragmentAddMahsulotBinding? = null
    lateinit var myDb: MyDb
    private var PICK_IMAGE_REQUEST = 1
    lateinit var imgFilePath: Uri
    lateinit var imageToStore: Bitmap
    lateinit var bitmap: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddMahsulotBinding.inflate(inflater, container, false)

        binding!!.apply {
            myDb = MyDb(requireContext())
            val viewModel = getViewModel(requireActivity())
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
                    myDb.addMahsulot(Mahsulotlar(nomi = name, narxi = narxi1, olchami = olchami1, rangi = rangi1, fasli = fasli1, rasm = bitmap))
                    mahsulotlarList = myDb.getMahsulot()
                    viewModel.mahsulotlarMutableLiveData!!.value = mahsulotlarList
                    Toast.makeText(requireContext(), "Malumot qo'shildi", Toast.LENGTH_SHORT).show()
                    nameEt.text.clear()
                    rangi.text.clear()
                    narxi.text.clear()
                    olchami.text.clear()
                    fasli.text.clear()
                }
            }

            val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let { selectedUri ->
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedUri)
                    rasm.setImageBitmap(bitmap)
                }
            }
            rasm.setOnClickListener {
                getContent.launch("image/*")
            }

        }

        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMahsulot().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
            binding!!.rasm.setImageBitmap(bitmap)
        }
    }
}