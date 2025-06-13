package com.example.todoapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val repository = TodoRepository()

    // Hold the todo list. MutableStateFlow triggers recomposition on change.
    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())
    val todoList: StateFlow<List<Todo>> = _todoList

    // Hold UI loading state (optional)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Fetch all todos from backend
    fun fetchTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todoList.value = repository.getTodos()
            } catch (e: Exception) {
                // In real app, show error!
            }
            _isLoading.value = false
        }
    }

    // Add a todo
    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(title)
            fetchTodos() // Refresh list
        }
    }

    // Update todo completed
    fun updateTodoCompleted(id: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.updateTodo(id, completed)
            fetchTodos() // Refresh list
        }
    }

    // Delete todo
    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.deleteTodo(id)
            fetchTodos() // Refresh list
        }
    }
}
