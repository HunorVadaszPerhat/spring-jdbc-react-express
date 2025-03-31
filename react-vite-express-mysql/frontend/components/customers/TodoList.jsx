import React from 'react'
import { useTodos } from '../../hooks/useTodos'

const TodoList = () => {
  const { data, isLoading, isError, error } = useTodos()

  if (isLoading) return <p>Loading...</p>
  if (isError) return <p>Error: {error.message}</p>

  return (
    <ul>
      {data.map(todo => (
        <li key={todo.id}>{todo.title}</li>
      ))}
    </ul>
  )
}

export default TodoList