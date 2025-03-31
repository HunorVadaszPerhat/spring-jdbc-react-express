import { useQuery } from '@tanstack/react-query'
import { api } from '../api/api'

const fetchTodos = async () => {
    const response = await api.get('/todos') // GET /todos
    return response.data
  }
  
  export const useTodos = () => {
    return useQuery({
      queryKey: ['todos'],
      queryFn: fetchTodos,
    })
  }