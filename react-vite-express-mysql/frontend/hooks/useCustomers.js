import { useQuery } from '@tanstack/react-query'
import { springapi } from '../api/api'

const fetchCustomers = async () => {
    const res = await springapi.get('/customers') // GET /customers
    console.log('API Response:', res.data) // helpful log
    return res.data._embedded.customerDTOList
  }
  
  export const useCustomers = () => {
    return useQuery({
      queryKey: ['customers'],
      queryFn: fetchCustomers,
    })
  }