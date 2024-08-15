package java.br.edu.ifsp.arq.dmos5.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.br.edu.ifsp.arq.dmos5.model.User


@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    fun loadUserById(userId: String?): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun login(email: String?, password: String?): User?

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

}