package com.example.crudwithfirebase.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudwithfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding


    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    val userList = ArrayList<Users>()
    lateinit var usersAdapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view =mainBinding.root
        setContentView(view)

        mainBinding.idFloatingActionButton.setOnClickListener{
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        //zero as the first parameter means i am not using the onMove method
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = usersAdapter.getUserId(viewHolder.adapterPosition)

                myReference.child(id).removeValue()
                Toast.makeText(
                    applicationContext,
                    "The user was deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }).attachToRecyclerView(mainBinding.idRecyclerView)
        retrieveDataFromDatabase()


    }
    fun retrieveDataFromDatabase(){
        myReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Avoid displaying duplicate data
                userList.clear()
                for(eachUser in snapshot.children){
                    val user = eachUser.getValue(Users::class.java)

                    if(user!= null){
                        println("userId: ${user.userId}")
                        println("userId: ${user.userName}")
                        println("userId: ${user.userAge}")
                        println("userId: ${user.userEmail}")
                        println("****************************")
                        //All the data in the database is move to the userList
                        userList.add(user)
                    }
                    usersAdapter = UsersAdapter(this@MainActivity,userList)

                    mainBinding.idRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainBinding.idRecyclerView.adapter = usersAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}