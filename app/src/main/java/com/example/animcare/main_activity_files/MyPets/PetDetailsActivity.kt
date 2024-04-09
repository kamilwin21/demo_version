package com.example.animcare.main_activity_files.MyPets

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.*
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.AddNewPetDetailsFragment
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles.DiagnosisFragment
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.GeneralFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_pet_details.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class PetDetailsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)

        val generalFragment = GeneralFragment()
        val addNewPetDetailsFragment = AddNewPetDetailsFragment()
        val diagnosisFragment = DiagnosisFragment()

        navView = findViewById(R.id.navViewPetDetails)
        drawerLayout = findViewById(R.id.drawerLayoutPetDetails)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout,0,0)
        drawerLayout.addDrawerListener(actionBarToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()

        var petInList = getPetInList()
        var position: Int = intent.getIntExtra("position",-1)

        setValuesInNavHeader("",petInList.pName)

        println("ZoneId: ${ZoneId.systemDefault()}")
        val zonedDateTime = ZonedDateTime.of(LocalDateTime.now(ZoneId.of("Europe/Warsaw")), ZoneId.of("Europe/Warsaw"))

        var study: Study = Study()
        study.setName("Pobranie krwi")
        val customDateAndTime = CustomDateAndTime(zonedDateTime.dayOfMonth.toString(), zonedDateTime.dayOfWeek.toString(),
            zonedDateTime.month.toString(), zonedDateTime.monthValue.toString(), zonedDateTime.year.toString(), zonedDateTime.hour.toString(),
            zonedDateTime.minute.toString(), zonedDateTime.second.toString(), zonedDateTime.zone.id
        )
        study.setDate(customDateAndTime)
        var doctor: Doctor = Doctor()
        doctor.setName("Janusz")
        doctor.setSurname("Kowalski")
        doctor.setSpecialization("Weterynarz")

        study.setDoctor(doctor)
        study.setCost(50)
        study.setDescription("Okresowe badanie zwierzaka")
        study.setSymptoms(arrayListOf(
            "Powiększone źrenice",
            "Zmniejszona waga ciała"
        ))
        study.setDiagnosis("Pusta diagnoza")
        var clinic = Clinic()
        clinic.setAddress("Warszawa, ul.Krakowskie Przedmieście 2")
        clinic.setName("Animals Clinic")
        study.setClinic(clinic)

        var pet: Pet = Pet()
        pet.setBirthday(CustomDateAndTime("12", "Friday","12","0","2019","10","40","0","Europe/Warsaw"))

        pet.setNameAndType(PetInList("Yogi", "Pies", ""))
        pet.setWeight(15.0)

        var age: Age = Age()
        age.setYear(2)
        age.setMonths(24)

        pet.setAge(age)
        pet.setHeight(30.0)
        pet.setStudy(arrayListOf(study))

        var periodicalResearch = PeriodicalResearch()
        periodicalResearch.setName("Badanie krwi")
        periodicalResearch.setDescription("Coroczne badanie okresowe")
        periodicalResearch.setPeriod(12)
        periodicalResearch.setLastStudy(CustomDateAndTime("0","0","0","0","0","0","0","0","Europe/Warsaw"))
        periodicalResearch.setNextStudy(CustomDateAndTime("19","Monday","9","0","2022","0","0","0","Europe/Warsaw"))
        periodicalResearch.setReason("Zwyczajna troska")

        pet.setPeriodicalResearch(arrayListOf(periodicalResearch))
        pet.setDBInstance(DataBase.dbReferName)
        //pet.saveValueToDataBase()



//
//        var petGetted1 = Pet()
//        petGetted1.setDBInstance(DataBase.dbReferName)

//
//        var bundle = Bundle()
//        bundle.putSerializable("pet", pet)
//        generalFragment.arguments = bundle




        var currentPet = Pet()
        var usersPets = arrayListOf<PetInList>()

        if (intent.hasExtra("pet")){
            currentPet = intent.getSerializableExtra("pet") as Pet

        }
        usersPets = intent.getSerializableExtra("userPets") as ArrayList<PetInList>


//        nameNavPetDetails.text = "${petInList.pName}"
//        if(intent.getBooleanExtra("first", true)){
//
//            goToFragment(addNewPetDetailsFragment)
//
//
//        }else{
//
//            var bundle = Bundle()
//            bundle.putSerializable("pet", currentPet)
//            generalFragment.arguments = bundle
//            goToFragment(generalFragment)
//        }



//        nameNavPetDetails.text = "${petInList.pName}"


        var dataGetted = DataPet()
        var connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connection.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid).child("${petInList.pName}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        dataGetted = snapshot.getValue(DataPet::class.java)!!
                        val pet = convertToPet(dataGetted)

                        var bundle = Bundle()
                        bundle.putSerializable("pet", pet)
                        bundle.putInt("position", position)
                        bundle.putSerializable("userPets", usersPets)
                        generalFragment.arguments = bundle
                        goToFragment(generalFragment)


                        nameNavPetDetails.text = "${pet.getNameAndType().pName}"

                        supportActionBar?.title = petInList.pName

                        navView.setNavigationItemSelectedListener {
                                it ->
                            when(it.itemId){
                                R.id.general -> {var bundle = Bundle()
                                    println("Kliknięto General")
                                    bundle.putSerializable("pet", pet)
                                    bundle.putInt("position", position)
                                    bundle.putSerializable("userPets", usersPets)
                                    generalFragment.arguments = bundle
                                    goToFragment(generalFragment)
                                    closeNavigationDrawer(drawerLayout)
                                    true
                                }
                                R.id.diagnosis -> {

                                    bundle.putString("PetName", pet.getNameAndType().pName)
                                    diagnosisFragment.arguments = bundle
                                    goToFragment(diagnosisFragment)
                                    closeNavigationDrawer(drawerLayout)

//                                    goToFragment(addNewPetDetailsFragment)
//                                    closeNavigationDrawer(drawerLayout)

                                }

                            }
                            false
                        }



                        if(pet.getProfileSrc().isNotEmpty()){
//                            imageViewPetDetails.setBackgroundColor(Color.BLACK)


                        }else{
                            getPhoto(imageViewPetDetails)
//                            getPhoto(imageViewPetDetails)
                        }

                    }else{
                        if (intent.hasExtra("petInList")){

                            var bundle = Bundle()
                            bundle.putSerializable("nameAndType", intent.getSerializableExtra("petInList") as PetInList)
                            bundle.putInt("position", position)
                            addNewPetDetailsFragment.arguments = bundle
                            goToFragment(addNewPetDetailsFragment)


                        }
                        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE





                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })







    }


//    override fun onBackPressed() {
//
//        println("Simple Name:${supportFragmentManager.fragments[supportFragmentManager.fragments.size-1].javaClass.simpleName}")
//        if (supportFragmentManager.fragments[supportFragmentManager.fragments.size-1].javaClass.simpleName == "EditingExistingFragment"){
////            if (!bottomNavStudiesListFragment.menu.getItem(0).isVisible){
////                bottomNavStudiesListFragment.menu.getItem(1).isVisible = false
////                bottomNavStudiesListFragment.menu.getItem(0).isVisible = true
//
//                println("Edytuj wciśnięte")
//
//            }
//
//        }
//
//
////        super.onBackPressed()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toBack -> {
                onBackPressed()
                return true
            }else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(navView)
        }else{
            drawerLayout.openDrawer(navView)
        }
        return true
    }

    private fun getPhoto(imageView: ImageView){
        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
            connection.child("DefaultPhoto").addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (s in snapshot.children){
                            Picasso.get().load(s.value.toString()).into(imageView)
                        }
                       println("Snapshot: ${snapshot}")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToPet(dataPet: DataPet):Pet{
        var pet = Pet()
        pet.setNameAndType(dataPet.nameAndType)
        pet.setBirthday(dataPet.birthday)
        pet.setWeight(dataPet.weight)
        pet.setStudy(dataPet.studies)
        pet.setHeight(dataPet.height)
        pet.setAge(dataPet.age)
        pet.setProfileSrc(dataPet.profileSrc)
        return pet
    }

    private fun getPetInList():PetInList{
        var petInList = PetInList()
        if (intent.hasExtra("petInList")){
             petInList = intent.getSerializableExtra("petInList") as PetInList

        }
        return petInList
    }
    private fun goToFragment(fragment: Fragment){
        var backName = fragment.javaClass.name
        val manager = supportFragmentManager
        var fragmentsInBackStack = manager.popBackStackImmediate(backName,0)

        if (!fragmentsInBackStack){
            val fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragmentContainerViewPetDetailsActivity, fragment)
//                .addToBackStack(backName)
            fragmentTransaction.commit()
        }

    }

    private fun closeNavigationDrawer(drawerLayout: DrawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }
    private fun setValuesInNavHeader(src: String, name: String){


    }


}