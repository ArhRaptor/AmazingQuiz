package by.quizzes.amazingquiz.repository

import by.quizzes.amazingquiz.db.UserDao
import by.quizzes.amazingquiz.model.db.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun addUser(user: User) = userDao.addUser(user)
    suspend fun updateUser(name: String, surname: String, userUid: String?) = userDao.updateUser(name, surname, userUid)
    suspend fun updateUser(name: String, surname: String, photoUrl: String, userUid: String) = userDao.updateUser(name, surname, photoUrl, userUid)
    suspend fun getUser(userUid: String?) = userDao.getUser(userUid)
    suspend fun getUserId(userUid: String?) = userDao.getUserId(userUid)
}