package com.example.todoapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.viewmodel.TodoViewModel
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.model.Todo
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete


@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    // State for the new todo text input
    var newTodoText by remember { mutableStateOf("") }

    // Observe the todo list from the ViewModel
    val todoList by viewModel.todoList.collectAsState()

    // Observe loading state (optional)
    val isLoading by viewModel.isLoading.collectAsState()

    // Call fetchTodos() only ONCE when screen appears
    LaunchedEffect(true) { viewModel.fetchTodos() }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Row for input field and Add button
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTodoText,
                onValueChange = { newTodoText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter a task") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTodoText.isNotBlank()) {
                        viewModel.addTodo(newTodoText)
                        newTodoText = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(todoList) { todo ->
                    TodoItem(
                        todo = todo,
                        onCheckedChange = { checked ->
                            viewModel.updateTodoCompleted(todo.id, checked)
                        },
                        onDeleteClick = {
                            viewModel.deleteTodo(todo.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    // Row for each todo item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = { checked -> onCheckedChange(checked) }
        )
        Text(
            text = todo.title,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }
    }
}
