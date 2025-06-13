const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors());
app.use(express.json());

let todos = [];
let nextId = 1;

// Get all todos
app.get('/todos', (req, res) => {
    res.json(todos);
});

// Add new todo
app.post('/todos', (req, res) => {
    const { title } = req.body;
    if (!title) return res.status(400).json({ error: "Title required" });
    const todo = { id: nextId++, title, completed: false };
    todos.push(todo);
    res.json(todo);
});

// Update completed
app.put('/todos/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const { completed } = req.body;
    const todo = todos.find(t => t.id === id);
    if (!todo) return res.status(404).json({ error: "Not found" });
    todo.completed = !!completed;
    res.json(todo);
});

// Delete todo
app.delete('/todos/:id', (req, res) => {
    const id = parseInt(req.params.id);
    todos = todos.filter(t => t.id !== id);
    res.json({ success: true });
});

const PORT = 3000;
app.listen(PORT, () => console.log(`Backend running on port ${PORT}`));
