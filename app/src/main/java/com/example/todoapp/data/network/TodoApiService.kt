package com.example.todoapp.data.network

import com.example.todoapp.data.model.Todo
import retrofit2.http.*

interface TodoApiService {

    // Get all todos
    @GET("/todos")
    suspend fun getTodos(): List<Todo>

    // Add a new todo
    @POST("/todos")
    suspend fun addTodo(@Body todo: Map<String, String>): Todo

    // Update a todo's completed status
    @PUT("/todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body status: Map<String, Boolean>
    ): Todo

    // Delete a todo
    @DELETE("/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Map<String, Boolean>
}
