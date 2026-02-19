package com.example.crudwithfirebase.view.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudwithfirebase.R
import com.example.crudwithfirebase.databinding.ActivityMainBinding
import com.example.crudwithfirebase.utils.DialogUtils
import com.example.crudwithfirebase.view.adapter.UsersAdapter
import com.example.crudwithfirebase.viewmodel.OperationResult
import com.example.crudwithfirebase.viewmodel.UsersViewModel


class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var usersAdapter: UsersAdapter
    private val viewModel: UsersViewModel by viewModels()


    //val userList = ArrayList<Users>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        mainBinding.toolbar.setTitleTextColor(
            ContextCompat.getColor(this, android.R.color.white)
        )
        supportActionBar?.title = getString(R.string.app_name)

        setupRecyclerView()
        observeUsers()
        viewModel.fetchUsers()

        mainBinding.idFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddUserActivity::class.java))

        }
        setupSwipeToDelete()
        observeOperationStatus()
    }

    private fun setupRecyclerView() {
        usersAdapter = UsersAdapter(this, ArrayList())
        mainBinding.idRecyclerView.layoutManager = LinearLayoutManager(this)
        mainBinding.idRecyclerView.adapter = usersAdapter
    }

    private fun observeUsers() {
        viewModel.users.observe(this) { list ->
            usersAdapter.updateList(list)
        }
    }

    private fun setupSwipeToDelete() {

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {

                val position = viewHolder.adapterPosition
                val id = usersAdapter.getUserId(position)

                // Show confirmation dialog
                DialogUtils.showConfirmation(
                    context = this@MainActivity,
                    title = "Delete User",
                    message = "Are you sure you want to delete this user?",
                    positiveButtonText = "Delete",
                    negativeButtonText = "Cancel",
                    onPositiveClick = {
                        viewModel.deleteUser(id)
                    },
                    onNegativeClick = {
                        // Restore item if user cancels
                        usersAdapter.notifyItemChanged(position)
                    }
                )
            }

        }).attachToRecyclerView(mainBinding.idRecyclerView)
    }
    private fun observeOperationStatus() {

        viewModel.operationStatus.observe(this) { result ->

            when (result) {

                is OperationResult.Success -> {
                    Toast.makeText(
                        this,
                        "User Deleted Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is OperationResult.Error -> {

                    Toast.makeText(
                        this,
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    // Restore RecyclerView state if deletion failed
                    usersAdapter.notifyDataSetChanged()
                }
            }
        }
    }


}