package com.gabortrajtler.whattodo

import androidx.lifecycle.LiveData
import com.gabortrajtler.whattodo.database.WhatTodo
import com.gabortrajtler.whattodo.database.WhatTodoDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WhatTodoRepository(private val database: WhatTodoDatabaseDao) {

    lateinit var todo: WhatTodo

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTodos: LiveData<List<WhatTodo>> = database.getAllTodos()

    suspend fun insert(whatTodo: WhatTodo) {
        withContext(Dispatchers.IO) {
            database.insert(whatTodo)
        }
    }

    suspend fun completeTodo(todoId: Int) {
        withContext(Dispatchers.IO) {
            todo = database.getTodoWithId(todoId) ?: return@withContext
            todo.isCompleted = true
            database.update(todo)
        }
    }

    suspend fun deleteTodo(todoId: Int) {
        withContext(Dispatchers.IO) {
            database.delete(todoId)
        }
    }
}