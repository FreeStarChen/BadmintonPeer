package com.mark.badmintonpeer.creategroup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.CreateGroupFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.TimeCalculator.toDateLong
import com.mark.badmintonpeer.util.TimeCalculator.toTimeLong
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoDatePicker
import com.mark.badmintonpeer.util.TimeCalculator.transformIntoTimePicker
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.sql.Timestamp

class CreateGroupFragment : Fragment() {

    private val viewModel by viewModels<CreateGroupViewModel> { getVmFactory() }
    lateinit var binding: CreateGroupFragmentBinding

    //for upload image
    var uri: Uri? = null
    var PICK_CONTACT_REQUEST = 1
    var REQUEST_CODE = 42
    var img1: ImageView? = null
    var img2: ImageView? = null
    val FILE_NAME = "photo.jpg"
    var photoFile: File? = null

    companion object {
        fun newInstance() = CreateGroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CreateGroupFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextDate.transformIntoDatePicker(requireContext(), "yyyy/MM/dd")
        binding.editTextStartTime.transformIntoTimePicker(requireContext(), "HH:mm")
        binding.editTextEndTime.transformIntoTimePicker(requireContext(), "HH:mm")

        viewModel.leave.observe(viewLifecycleOwner) {
            it?.let { needRefresh ->
                if (needRefresh) {
                    ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
                        refresh()
                    }
                }
                findNavController().navigateUp()
                viewModel.onLeft()
            }
        }

        val characteristics = mutableListOf<String>()
        val degree = mutableListOf<String>()

        viewModel.haveWaterDispenser.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintWaterDispenser.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("飲水機")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintWaterDispenser.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("飲水機")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveAirCondition.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintAirCondition.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("冷氣")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintAirCondition.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("冷氣")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.havePuGround.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintPuGround.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("PU地面")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintPuGround.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("PU地面")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveSpotlight.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintSpotlight.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("側面燈光")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintSpotlight.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("側面燈光")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveShower.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintShower.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("淋浴間")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintShower.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("淋浴間")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveParking.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintParking.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("停車場")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintParking.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("停車場")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveCutlery.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintCutlery.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("餐飲販售")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintCutlery.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("餐飲販售")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.haveHairDryer.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintHairDryer.setBackgroundResource(R.drawable.bg_light_blue_radius_10dip)
                characteristics.add("吹風機")
                viewModel.group.value?.characteristic = characteristics
            } else {
                binding.constraintHairDryer.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                characteristics.remove("吹風機")
                viewModel.group.value?.characteristic = characteristics
            }
        }

        viewModel.degree1.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree1.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("新手")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree1.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("新手")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree2.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree2.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("初階")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree2.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("初階")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree3.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree3.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("初中")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree3.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("初中")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree4.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree4.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("中階")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree4.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("中階")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree5.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree5.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("中上")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree5.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("中上")
                viewModel.group.value?.degree = degree
            }
        }

        viewModel.degree6.observe(viewLifecycleOwner) {
            if (it) {
                binding.constraintDegree6.setBackgroundResource(R.drawable.bg_orange_radius_10dip)
                degree.add("高階")
                viewModel.group.value?.degree = degree
            } else {
                binding.constraintDegree6.setBackgroundResource(R.drawable.bg_white_radius_10dip)
                degree.remove("高階")
                viewModel.group.value?.degree = degree
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.group.value?.classification =
                radioGroup.findViewById<RadioButton>(i).text.toString()
            Timber.d("classification=${radioGroup.findViewById<RadioButton>(i).text.toString()}")
        }

        //for upload image with camara
        //打開攝影機----------------------------------------------------------------------
        //打開相機按鈕
//        binding.buttonEditCamera.setOnClickListener {
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            photoFile = getPhotoFile(FILE_NAME)
//
//            val fileProvider = context?.let { it1 -> FileProvider.getUriForFile(it1,"com.zongmin.cook.fileprovider",
//                photoFile!!
//            ) }
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
////            if(takePictureIntent.resolveActivity(MainActivity().packageManager)!= null){
////            }
//            startActivityForResult(takePictureIntent,REQUEST_CODE)
//        }

        img1 = binding.imageShowPhoto //上傳的照片
//        img2 = binding.imageEditMain //顯示firestorge照片


        //上傳照片按鈕
        binding.constraintPhoto.setOnClickListener {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }

        return binding.root
    }

    fun getDatePickerDialogDateAndTime() {
        Timber.d("date=${binding.editTextDate.text.toString()}")
        val date = binding.editTextDate.text.toString()
        val dateToLong = date.toDateLong()
        viewModel.group.value?.date = Timestamp(dateToLong)
        val startTime = binding.editTextStartTime.text.toString()
        getPeriod(startTime)
        val startTimeToLong = startTime.toTimeLong()
        viewModel.group.value?.startTime = Timestamp(startTimeToLong)
        val endTime = binding.editTextEndTime.text.toString()
        val endTimeToLong = endTime.toTimeLong()
        viewModel.group.value?.endTime = Timestamp(endTimeToLong)
    }

    fun getPeriod(startTime: String) {
        val hour = startTime.subSequence(0,1)
        val hourToInt = hour.toString().toInt()
        if (hourToInt in 6..11) {
            viewModel.group.value?.period = "早上"
        }else if (hourToInt in 12..17) {
            viewModel.group.value?.period = "下午"
        }else if (hourToInt in 18..23) {
            viewModel.group.value?.period = "晚上"
        }else {
            viewModel.group.value?.period = "凌晨"
        }
    }

    fun callViewModelAddGroupResult() {
        uploadImageToStorage()
        val listUri = listOf(uri.toString())
        viewModel.group.value?.images = listUri
        getDatePickerDialogDateAndTime()
        viewModel.addGroupResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //來自檔案
        if (requestCode == PICK_CONTACT_REQUEST) {
            uri = data?.data
            img1?.setImageURI(uri)
            Timber.d("上傳拿到的uri -> $uri")

        }
        //來自相機
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val takeImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
            Timber.d("拍照拿的takeImage -> $takeImage")
            img1?.setImageBitmap(takeImage)
            uri = context?.let { getImageUri(it, takeImage) }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    fun uploadImageToStorage() {
        var storageReference = FirebaseStorage.getInstance().getReference()
        var unusedFileName = 0L

        //上傳圖片   應該要改去viewModel用coroutineScope.launch
        val time = System.currentTimeMillis()
        val picStorage = storageReference.child("image$time")
        Timber.d("點擊更換圖片1，看一下picStorage是啥 -> $picStorage")

        uri?.let { it1 ->
            picStorage.putFile(it1).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("上傳成功")
                    picStorage.downloadUrl.addOnSuccessListener {
//                            Timber.d("看一下uri ->$it ")
//                            Glide.with(this /* context */)
//                                .load(it)
//                                .into(img2!!)

                        Timber.d("成功更換圖片")
                        if (unusedFileName == 0L) {
                            unusedFileName = time
                            Timber.d("沒有過去的圖片")
                        } else {
                            storageReference.child("image$unusedFileName").delete()
                            unusedFileName = time
                            Timber.d("刪除上次張上傳的圖片")
                        }
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                } else {
                    Timber.d("上傳失敗")
                }
            }
        }

    }

    //Bitmap to Uri 為了傳firebase
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    //    取得暫存圖片檔案
    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }


}