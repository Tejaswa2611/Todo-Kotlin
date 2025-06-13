package com.example.todoapp.data.repository
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.network.RetrofitInstance

class TodoRepository {
    // Fetch all todos from server
    suspend fun getTodos() = RetrofitInstance.api.getTodos()

    // Add a new todo
    suspend fun addTodo(title: String) = RetrofitInstance.api.addTodo(mapOf("title" to title))

    // Update completed status
    suspend fun updateTodo(id: Int, completed: Boolean) =
        RetrofitInstance.api.updateTodo(id, mapOf("completed" to completed))

    // Delete todo
    suspend fun deleteTodo(id: Int) = RetrofitInstance.api.deleteTodo(id)
}
