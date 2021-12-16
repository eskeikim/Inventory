package com.skimani.inventory.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.skimani.inventory.R
import com.skimani.inventory.data.entities.Products
import com.skimani.inventory.databinding.AddProductBinding
import com.skimani.inventory.ui.viewmodel.ProductsViewmodel
import com.skimani.inventory.utils.Constants
import com.skimani.inventory.utils.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AddProductDialog : CustomRoundedBottomSheet() {
    val productsViewmodel: ProductsViewmodel by viewModels()
    lateinit var binding: AddProductBinding
    private var selectedItem = ""
    private var imageNamePath = ""
    val CAMERA_CODE = 1
    val GALLERY_CODE = 0
    val READ_STORAGE_PERMISSION = 100
    val CAMERA_PERMISSION = 101
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddProductBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initViews()
        return binding.root
    }

    private fun initViews() {

        initSpinner()
        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.imageLayout.setOnClickListener {
            showChooseDialog()
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val code = binding.etCode.text.toString().trim()
            val category = selectedItem
            val type = binding.etType.text.toString().trim()
            val manufacturer = binding.etManufacturer.text.toString().trim()
            val distributor = binding.etDistributor.text.toString().trim()
            val cost = binding.etCost.text.toString().trim()
            val retailPrice = binding.etRetailPrice.text.toString().trim()
            val agentPrice = binding.etAgentPrice.text.toString().trim()
            val wholesalePrice = binding.etWholesalePrice.text.toString().trim()
            val imagePath = imageNamePath
            if (validateFields(
                    name,
                    code,
                    category,
                    type,
                    manufacturer,
                    distributor,
                    cost,
                    retailPrice,
                    agentPrice,
                    wholesalePrice
                )
            ) {
                binding.saveBtn.isEnabled = true
                binding.saveBtn.alpha = 0.3F
                saveProduct(
                    name,
                    code,
                    category,
                    type,
                    manufacturer,
                    distributor,
                    cost,
                    retailPrice,
                    agentPrice,
                    wholesalePrice,
                    imagePath
                )
                dismiss()
            } else {
                Toast.makeText(requireContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Allow user to choose image from camera or gallery
     */
    private fun showChooseDialog() {
        imageNamePath = ""
        val Items = arrayOf<CharSequence>("Camera", "Gallery")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(com.skimani.inventory.R.string.AlertTitle)
        builder.setItems(Items) { dialog, which ->
            for (i in Items.indices) {
                if (Items[i] == "Camera") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            if (ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    arrayOf(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                    ),
                                    CAMERA_PERMISSION
                                )
                            } else {
                                ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    arrayOf(Manifest.permission.CAMERA),
                                    CAMERA_PERMISSION
                                )
                            }
                        } else if (ContextCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                CAMERA_PERMISSION
                            )
                        } else {
                            val CameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (CameraIntent.resolveActivity(requireContext().packageManager) != null) {
                                startActivityForResult(CameraIntent, CAMERA_CODE)
                            }
                        }
                    } else {
                        val CameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (CameraIntent.resolveActivity(requireContext().packageManager) != null) {
                            startActivityForResult(CameraIntent, CAMERA_CODE)
                        }
                    }
                } else if (Items[i] == "Gallery") {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val intentGallery =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        intentGallery.type = "image/*"
                        intentGallery.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(intentGallery, GALLERY_CODE)
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_STORAGE_PERMISSION
                        )
                    }
                }
            }
        }
        builder.show()
    }

    /**
     * init Spinner
     */
    private fun initSpinner() {
        val categoriesList = resources.getStringArray(com.skimani.inventory.R.array.category)
        val categoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            categoriesList
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = categoryAdapter
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Timber.d("SELECTED ${p0?.selectedItem}")
                    selectedItem = p0?.selectedItem.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    /**
     * Add the product to room db
     */
    private fun saveProduct(
        name: String,
        code: String,
        category: String,
        type: String,
        manufacture: String,
        distributor: String,
        cost: String,
        retailPrice: String,
        agentPrice: String,
        wholesalePrice: String,
        imagePath: String
    ) {
        val product = Products(
            name,
            code,
            category,
            type,
            manufacture,
            distributor,
            cost,
            retailPrice,
            agentPrice,
            wholesalePrice,
            imagePath
        )
        productsViewmodel.viewModelScope.launch(Dispatchers.IO) {
            productsViewmodel.addProducts(product)
        }.invokeOnCompletion {
            imageNamePath = ""
        }
    }

    /**
     * Check if all fields are filled correctly
     */
    private fun validateFields(
        name: String,
        code: String,
        category: String,
        type: String,
        manufacturer: String,
        distributor: String,
        cost: String,
        retailPrice: String,
        agentPrice: String,
        wholesalePrice: String
    ): Boolean {
        return !(
            name.isEmpty() || code
                .isEmpty() || category.isEmpty() || type.isEmpty() || manufacturer.isEmpty() || distributor.isEmpty() || cost.isEmpty() || retailPrice.isEmpty() || agentPrice.isEmpty() || wholesalePrice.isEmpty()
            )
    }

    companion object {
        fun newInstance(): AddProductDialog =
            AddProductDialog().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = data?.data
                binding.ivProduct.setImageURI(selectedImage)
                val imageName = Constants.imageName
                val imageBitmap = selectedImage?.let { Util.uriToBitmap(requireContext(), it) }
                if (imageBitmap != null) {
                    val path = Util.saveToInternalStorage(requireContext(), imageBitmap, imageName)
                    Timber.d("PATH $path")
                    imageNamePath = "$path/$imageName"
                } else {
                    Toast.makeText(requireContext(), "Error  saving image", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            1 -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = data?.data
                binding.ivProduct.setImageURI(selectedImage)
                val imageName = Constants.imageName
                val imageBitmap = selectedImage?.let { Util.uriToBitmap(requireContext(), it) }
                if (imageBitmap != null) {
                    val path = Util.saveToInternalStorage(requireContext(), imageBitmap, imageName)
                    Timber.d("PATH $path/$imageName")
                    imageNamePath = "$path/$imageName"
                } else {
                    Toast.makeText(requireContext(), "Error  saving image", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
