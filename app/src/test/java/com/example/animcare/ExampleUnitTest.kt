package com.example.animcare

import com.example.animcare.Authorization.User
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



    fun getCountID(list: ArrayList<Int>):Int{
     list.removeLast()
        return list.size
    }
    @Test
    fun check(){

        assertEquals(4, getCountID(arrayListOf(1,2,3,4,5)))
    }

    @Test
    fun should_return_correctness_email_from_user_object(){
        //Given
        var user: User = User()
        //When
        var correctness = user.setEmail("kamilwin21@gmail.com")
        //Then
        Assert.assertEquals(true, correctness)

    }
    @Test
    fun should_return_incorrect_email_from_user_object(){
        //Given
        var user: User = User()
        //When
        var correctness = user.setEmail("kamilwin21@gmailcom")
        //Then
        Assert.assertEquals(false, correctness)

    }
    @Test
    fun should_return_incorrect_email_from_user_object_without_monkey() {
        //Given
        var user: User = User()
        //When
        var correctness = user.setEmail("kamilwin21gmail.com")
        //Then
        Assert.assertEquals(false, correctness)


    }

    @Test
    fun should_return_true_from_setting_password(){
        //Given
            var user = User()
        //When
            var correctness = user.setPassword("123423123123123sdwa221")
        //Then
        assertEquals(true, correctness)
    }
    @Test
    fun should_return_false_from_setting_password(){
        //Given
            var user = User()
        //When
            var correctness = user.setPassword("12342")
        //Then
        assertEquals(false, correctness)
    }
    @Test
    fun should_return_true_when_name_is_setting_in_object(){
        //Given
            var user = User()
        //When
            var correctness = user.setName("Kasda")
        //Then
        assertEquals(true, correctness)
    }
    @Test
    fun should_return_false_when_name_is_wrong_small_letters_value_in_object(){
        //Given
            var user = User()
        //When
            var correctness = user.setName("fdssfdf")
        //Then
        assertEquals(false, correctness)
    }
    @Test
    fun should_return_false_when_name_is_wrong_two_upper_letters_value_in_object(){
        //Given
            var user = User()
        //When
            var correctness = user.setName("SdssfdfSJ")
        //Then
        assertEquals(false, correctness)
    }

}