package com.example.raz_demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raz_demo.model.User
import com.example.raz_demo.repository.UserRepository
import com.example.raz_demo.repository.UserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImp: UserRepository
): ViewModel(){
    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepositoryImp.getNewUser()
            Log.d("UserViewModel",user.toString())
        }
    }
    private val  _isLoading : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val users : LiveData<List<User>> by lazy{
        userRepositoryImp.getAllUsers()
    }
    val isLoading :   LiveData<Boolean> get() = _isLoading

    fun addUser(){
        if (_isLoading.value == false){
            viewModelScope.launch(Dispatchers.IO){
                    _isLoading.postValue(true)
                    userRepositoryImp.getNewUser()
                    _isLoading.postValue(false)
            }
        }
    }
    fun deleteUser(toDelete: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepositoryImp.deleteUser(toDelete)
        }
    }
}