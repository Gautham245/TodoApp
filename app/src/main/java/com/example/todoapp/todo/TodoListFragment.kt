package com.example.todoapp.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodolistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var fragmentTodolistBinding: FragmentTodolistBinding
    private val todoViewModel: TodoViewModel by activityViewModels()
    lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTodolistBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_todolist, container, false)

        return fragmentTodolistBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTodolistBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = todoViewModel
            fragment = this@TodoListFragment
        }

        todoAdapter = TodoAdapter { todo ->
            MaterialAlertDialogBuilder(this.requireActivity())
                .setTitle(getString(R.string.alert))
                .setMessage(getString(R.string.do_u_want_delete))
                .setNegativeButton(getString(R.string.delete)) { dialog, where ->
                    todoViewModel.deleteTodo(todo)
                    todoViewModel.getTodos()
                    dialog.dismiss()
                }
                .setNeutralButton(getString(R.string.cancel)) { dialog, where ->
                    dialog.dismiss()
                }
                .show()
        }

        setUpRecyclerView()

        todoViewModel.todos.observe(viewLifecycleOwner, Observer { todos ->
            todos?.let {
                todoAdapter.submitList(todos)
            }
        })

    }

    private fun setUpRecyclerView() {
        fragmentTodolistBinding.recycler.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        todoViewModel.getTodos()
    }

    fun navigateToAddTodo() {
        findNavController().navigate(R.id.action_todoListFragment_to_addTodoFragment)
    }
}