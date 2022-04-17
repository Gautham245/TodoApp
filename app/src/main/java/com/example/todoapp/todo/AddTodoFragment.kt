package com.example.todoapp.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Todo
import com.example.todoapp.databinding.FragmentAddTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddTodoFragment() : Fragment() {

    private lateinit var fragmentAddTodoBinding: FragmentAddTodoBinding
    private val todoViewModel: TodoViewModel by activityViewModels()
    lateinit var todo : Todo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddTodoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false)

        return fragmentAddTodoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentAddTodoBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = todoViewModel
            addTodoFragemnt = this@AddTodoFragment
        }
    }

    fun addTodo(){
        todoViewModel.addTodo(Todo(null,fragmentAddTodoBinding.title.text.toString(),fragmentAddTodoBinding.description.text.toString()))
        findNavController().navigate(R.id.action_addTodoFragment_to_todoListFragment)
    }
}