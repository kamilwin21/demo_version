package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.Age
import com.example.animcare.main_activity_files.MyPets.Class.CustomDateAndTime
import com.example.animcare.main_activity_files.MyPets.Pet
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_editing_existing.*
import kotlinx.android.synthetic.main.fragment_general.*
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditingExistingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditingExistingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editing_existing, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        val bundle = arguments
        var counting = 0
        calendar_editin_existing.visibility = View.GONE

        radioGroup_editingExisitng.visibility = View.GONE


        var imageStatusClicked = 0
        imageView_animal_type_editing_existing.setOnClickListener {
            setVisibilityRadioGroup()
            imageStatusClicked = setStatusArrowIconInAnimalType(imageStatusClicked)

        }


        animalType_editing_existing_fragment.setOnClickListener {
            setVisibilityRadioGroup()
            imageStatusClicked = setStatusArrowIconInAnimalType(imageStatusClicked)
        }


        val nameAndType = bundle!!.getSerializable("nameAndType") as PetInList
        val height = bundle!!.getDouble("height")
        val age = bundle!!.getString("age")
        val birthday = bundle!!.getSerializable("birthday") as CustomDateAndTime
        val customDateFormat = "${birthday.day}/${birthday.monthValue}/${birthday.year}"
        val pet = bundle!!.getSerializable("pet") as Pet
        var usersPets = bundle!!.getSerializable("userPets") as ArrayList<PetInList>
        var oldPetName = pet.getNameAndType().pName
        var oldPetType = pet.getNameAndType().type
        var oldBirthday = pet.getBirthday()
        var oldHeight = pet.getHeight()


        var grade = pet.getNameAndType().type
        println("Pet.getType(): ${pet.getNameAndType().type}")
        animalType_editing_existing_fragment.setText("${pet.getNameAndType().type}")



        checkbox_1.setOnClickListener{
            grade = checkbox_1.text.toString()
            animalType_editing_existing_fragment.setText(grade)
        }
        checkbox_2.setOnClickListener{
            grade = checkbox_2.text.toString()
            animalType_editing_existing_fragment.setText(grade)

        }
        checkbox_3.setOnClickListener{
            grade = checkbox_3.text.toString()
            animalType_editing_existing_fragment.setText(grade)

        }


        var currentLocalDate = LocalDate.of(pet.getBirthday().year.toInt(), pet.getBirthday().monthValue.toInt(), pet.getBirthday().day.toInt())
        var ageDifference = Period.between(currentLocalDate, LocalDate.now())
        if (ageDifference.years > 0){
            setDefaultAgeInfoText("${resources.getString(R.string.inYears)}")
        }else if (ageDifference.years == 0){
            setDefaultAgeInfoText("${resources.getString(R.string.inMonths)}")
        }


        var year = pet.getBirthday().year.toInt()
        var month = pet.getBirthday().monthValue.toInt()
        var day = pet.getBirthday().day.toInt()
        animalName_editing_existing_fragment.setText(pet.getNameAndType().pName)

//        animalType_editing_existing_fragment.setText(grade)
        setRadioCorrectRadioButton(pet.getNameAndType().type)
        animalAge_editing_existing_fragment.setText(countAge(LocalDate.of(year,month,day)))
        animalHeight_editing_existing_fragment.setText(height.toString())
        animalBirthday_editing_existing_fragment.setText(customDateFormat)


        
        calendar_editin_existing.maxDate = System.currentTimeMillis()
        calendar_editin_existing.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(
                view: CalendarView, year: Int, month: Int, dayOfMonth: Int
            ) {
                val stringDate = "${dayOfMonth}/${month+1}/${year}"
                animalBirthday_editing_existing_fragment.setText(stringDate)
                var currentLocalDate = convertToLocalDate(stringDate)
                val datesDifference = Period.between(currentLocalDate, LocalDate.now())
                if (datesDifference.years > 0){
                    animalAge_editing_existing_fragment.setText(datesDifference.years.toString())
                    ageViewInfo_editing_existing.setText("${resources.getString(R.string.age)}(${resources.getString(R.string.inYears)})")
                }else if (datesDifference.years == 0){
                    ageViewInfo_editing_existing.setText("${resources.getString(R.string.age)}(${resources.getString(R.string.inMonths)})")
                    animalAge_editing_existing_fragment.setText(datesDifference.months.toString())
                }

            }


        })

        animalBirthday_editing_existing_fragment.setOnClickListener {
            counting = setStatusArrayIcon(counting)
            setVisibilityForCalendar()

        }

        imageView_EditinExisting.setOnClickListener {
            counting = setStatusArrayIcon(counting)
            setVisibilityForCalendar()

        }


        btn_save_new_detailes_editing_existing_fragment.setOnClickListener {
                        var newPetValues = pet
                        newPetValues.setNameAndType(PetInList("${animalName_editing_existing_fragment.text}",
                            "${grade}",
                            "${pet.getProfileSrc()}"))

                        var local=  convertToLocalDate(animalBirthday_editing_existing_fragment.text.toString())

                        var age = countingDifferenceBetweenTwoDatesAndGetAge(local)
                        newPetValues.setAge(age)

                        var newBirthday = pet.getBirthday()

                        println("LocalDateAccually: ${animalBirthday_editing_existing_fragment.text.toString()}")

                        newBirthday.year = local.year.toString()
                        newBirthday.monthValue = local.monthValue.toString()
                        newBirthday.month = local.month.toString()
                        newBirthday.day = local.dayOfMonth.toString()
                        newBirthday.dayOfWeek = local.dayOfWeek.toString()



                        pet.setBirthday(newBirthday)
                        pet.setHeight(animalHeight_editing_existing_fragment.text.toString().toDouble())
                        println("Local Date: ${local.year}/ ${local.monthValue}/ ${local.dayOfMonth}")
                        parentFragmentManager.popBackStack()




//                        val connectToDBUsersPet = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
//                        connectToDBUsersPet.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)


                        var condition = checkCorrectness(pet, oldPetName, oldPetType, oldBirthday, oldHeight)
                        if(condition) {
                            val removingConnection =
                                FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                            removingConnection.child("Pets")
                                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .child(oldPetName).removeValue()

                            nameAndType.imagePath = pet.getNameAndType().imagePath
                            removeAndSaveNewPetItemFromPetInListInDB(
                                usersPets,
                                nameAndType,
                                PetInList(
                                    "${newPetValues.getNameAndType().pName}",
                                    "${newPetValues.getNameAndType().type}",
                                    "${newPetValues.getNameAndType().imagePath}"
                                )
                            )
                        }

                        val connectionToDB = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                        connectionToDB.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .child(pet.getNameAndType().pName).setValue(pet)

                        setTextInRequireActivityContextInPreviousFragment(requireActivity(), pet)

        }





    }
    //===========================================================
    //LISTENERS
    //===========================================================


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTextInRequireActivityContextInPreviousFragment(requireActivity: FragmentActivity, pet: Pet){

        var currentLocalDate = LocalDate.of(pet.getBirthday().year.toInt(), pet.getBirthday().monthValue.toInt(), pet.getBirthday().day.toInt())
        var ageDifference = Period.between(currentLocalDate, LocalDate.now())
        if (ageDifference.years > 0){
//            setDefaultAgeInfoText("${resources.getString(R.string.inYears)}")
            requireActivity.ageViewInfo.text = "${resources.getString(R.string.age)}(${resources.getString(R.string.inYears)})"
            requireActivity.animalAge.text = ""
            requireActivity.animalAge.text = "${pet.getAge().getYear()}"
        }else if (ageDifference.years == 0){
            requireActivity.ageViewInfo.text = "${resources.getString(R.string.age)}(${resources.getString(R.string.inMonths)})"
            requireActivity.animalAge.text = ""
            requireActivity.animalAge.text = "${pet.getAge().getMonths()}"
//            setDefaultAgeInfoText("${resources.getString(R.string.inMonths)}")
        }



        requireActivity.animalName.text = "${pet.getNameAndType().pName}"
        requireActivity.animalType.text = "${pet.getNameAndType().type}"

        requireActivity.animalBirthday.text = "${animalBirthday_editing_existing_fragment.text}"
        requireActivity.animalHeight.text = "${pet.getHeight()}"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkCorrectness(pet: Pet, firstValue: String, secondValue: String,
                                 oldBirthday: CustomDateAndTime, oldHeight: Double): Boolean{
        var correctness = false
        println("000000000000000000000000000000000000000000")
        println("checkCorrectness()")
        println("name: ${pet.getNameAndType().pName}")
        println("type: ${pet.getNameAndType().type}")
        println("firstValue: ${firstValue}")
        println("secondValue: ${secondValue}")
        println("000000000000000000000000000000000000000000")

        if (pet.getNameAndType().pName != firstValue ||
                pet.getNameAndType().type != secondValue || pet.getBirthday() != oldBirthday
            || pet.getHeight() != oldHeight
                ){
            correctness = true
        }else{
            correctness = false
        }

        println("Correctness: ${correctness}")
        return correctness
    }

    private fun setStatusArrowIconInAnimalType(integer: Int): Int{
        var imageStatusClicked = integer
        when(imageStatusClicked){
            0 -> {
                imageView_animal_type_editing_existing.background =  requireContext().getDrawable(R.drawable.ic_baseline_arrow_back_24)
                imageStatusClicked = 1
            }
            1->{
                imageView_animal_type_editing_existing.background = requireContext().getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                imageStatusClicked = 0
            }
        }
        return imageStatusClicked
    }

    private fun setVisibilityForCalendar(){
        when(calendar_editin_existing.visibility){
            View.GONE -> {
                calendar_editin_existing.visibility = View.VISIBLE
            }
            View.VISIBLE -> {
                calendar_editin_existing.visibility = View.GONE
            }
        }

    }

    private fun setStatusArrayIcon(integer: Int): Int{
        var counting = integer
        when(counting){
            0 -> {
                imageView_EditinExisting.background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_back_24)
                counting = 1
            }
            1 -> {
                imageView_EditinExisting.background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                counting = 0
            }
        }
        return counting
    }


    private fun setVisibilityRadioGroup(){

        when(radioGroup_editingExisitng.visibility){
            View.GONE -> {
                radioGroup_editingExisitng.visibility = View.VISIBLE
            }
            View.VISIBLE -> {
                radioGroup_editingExisitng.visibility = View.GONE
            }
        }
    }

    private fun setRadioCorrectRadioButton(animalType: String){
        when(animalType){
            "Pies" -> {
                checkbox_1.isChecked = true
            }
            "Kot" -> {
                checkbox_2.isChecked = true
            }
            "Przykład" -> {
                checkbox_3.isChecked = true
            }
        }

    }
    private fun setDefaultAgeInfoText(ageType: String){
        ageViewInfo_editing_existing.setText("${resources.getString(R.string.age)}(${ageType})")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun countingDifferenceBetweenTwoDatesAndGetAge(local: LocalDate): Age{
        var dateDifference = Period.between(local, LocalDate.now())
        var age = Age()
        if (dateDifference.years > 0){

            age.setValues(animalAge_editing_existing_fragment.text.toString().toInt())
        }else if (dateDifference.years == 0){
            age.setMonths(dateDifference.months)
        }
        return age
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun removeAndSaveNewPetItemFromPetInListInDB(list: ArrayList<PetInList>, pet: PetInList, newPet: PetInList){
        var petsInList = list
        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference

        println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        println("PET. name: ${pet.pName}")
        println("PET. type: ${pet.type}")
        println("PET. img: ${pet.imagePath}")
//        println("Boolean: ${petsInList.remove(pet)}")
        println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        println("----------------------------------------------------------")
        println("PET NEW VALUE: ${newPet.pName}")
        println("PET NEW VALUE: ${newPet.type}")
        println("PET NEW VALUE: ${newPet.imagePath}")
        println("----------------------------------------------------------")
//        var objectToRemove = petsInList.find { it.pName == pet.pName && it.type == pet.type && it.imagePath == pet.imagePath }
//        petsInList.removeIf{ it.pName == pet.pName && it.type == pet.type && it.imagePath == pet.imagePath }

        for (i in petsInList){

            println("================================================")
            println("Name: ${i.pName}")
            println("Type: ${i.type}")
            println("Image: ${i.imagePath}")
            println("================================================")
        }

        for (i in 0 until petsInList.size){
            if (petsInList[i].pName == pet.pName && petsInList[i].type == pet.type){
                petsInList.removeAt(i)
                break
            }

        }
        petsInList.add(newPet)


        connection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(petsInList)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToLocalDate(date: String):LocalDate{
        var regex = Regex("[0-9]+[\\/][0-9]+[\\/][0-9]+")
        var summary = date.matches(regex)
        var localDate = LocalDate.of(1000,1,1)
        when(summary){
            true -> {
                val separator = "/"
                var currentDate = date.split(separator)
                localDate = LocalDate.of(currentDate[2].toInt(), currentDate[1].toInt(), currentDate[0].toInt())


            }
            false -> {
                Toast.makeText(requireContext().applicationContext, "Nieprawidłowy format daty", Toast.LENGTH_SHORT).show()
            }
        }



        return localDate
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun countAge(birthday: LocalDate):String{
        var localDateNow = LocalDate.now()
        var returnedValue = 0
        Period.between(localDateNow, birthday)

        println("Period.beteen: ${Period.between(localDateNow, birthday).years}")
        var year = abs(Period.between(localDateNow, birthday).years)
        if (year > 0){
            returnedValue = year
//            setContentInView(animalAge_editing_existing_fragment, requireActivity().getString(R.string.inYears))
        }else if (year == 0){
            returnedValue = abs(Period.between(localDateNow, birthday).months)
//            setContentInView(animalAge_editing_existing_fragment, requireActivity().getString(R.string.inMonths))
        }
        return returnedValue.toString()
    }

//    private fun setContentInView(textView: EditText, newValue: String){
//        var getText = textView.text.toString()
//        textView.setText("${getText}(${newValue})")
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditingExistingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditingExistingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}