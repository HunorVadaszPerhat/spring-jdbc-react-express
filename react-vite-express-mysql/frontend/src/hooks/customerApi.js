import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { springapi } from '../api/endpoints'
import toast from 'react-hot-toast'

// 🟢 GET /customers
/* const fetchCustomers = async () => {
  const res = await springapi.get('/customers')
  console.log('API Response:', res.data)
  console.log('res.data._embedded.customerDTOList:', res.data._embedded.customerDTOList)
  return res.data._embedded.customerDTOList
}  */

const fetchCustomers = async ({ pageIndex, pageSize }) => {
  const res = await springapi.get('/customers', {
    params: {
      page: pageIndex,
      size: pageSize,
    },
  })

  return {
    data: res.data._embedded.customerDTOList,
    total: res.data.page.totalElements,
  }
}

export const useCustomers = ({ pageIndex, pageSize }) => {
  return useQuery({
    queryKey: ['customers', pageIndex, pageSize],
    queryFn: () => fetchCustomers({ pageIndex, pageSize }),
    keepPreviousData: true, // helps avoid flashing when paging
  })
}

// 🟡 POST /customers
const createCustomer = async (customerData) => {
  const res = await springapi.post('/customers', customerData)
  return res.data
}
export const useCreateCustomer = () => {
  const queryClient = useQueryClient()

  return useMutation({
      mutationFn: createCustomer,
      onSuccess: () => {
          queryClient.invalidateQueries(['customers'])
      },
  })
}

// 🔁 Optimistic UPDATE
export const useUpdateCustomer = () => {
  const queryClient = useQueryClient()
  return useMutation({
    mutationFn: ({ id, customerData }) =>
      springapi.put(`/customers/${id}`, customerData),
    onMutate: async ({ id, customerData }) => {
      await queryClient.cancelQueries(['customers'])
      const previousData = queryClient.getQueryData(['customers'])

      queryClient.setQueryData(['customers'], (old) => {
        if (!old?.customers) return old
        const updated = old.customers.map((c) =>
          c.id === id ? { ...c, ...customerData } : c
        )
        return { ...old, customers: updated }
      })

      return { previousData }
    },
    onError: (err, variables, context) => {
      if (context?.previousData) {
        queryClient.setQueryData(['customers'], context.previousData)
      }
      toast.error('Failed to update customer')
    },
    onSuccess: () => {
      toast.success('Customer updated successfully')
    },
    onSettled: () => {
      queryClient.invalidateQueries(['customers'])
    },
  })
}

// 🔴 DELETE /customers/{id}
// 🗑️ Optimistic DELETE
export const useDeleteCustomer = () => {
  const queryClient = useQueryClient()
  return useMutation({
    mutationFn: (id) => springapi.delete(`/customers/${id}`),
    onMutate: async (id) => {
      await queryClient.cancelQueries(['customers'])
      const previousData = queryClient.getQueryData(['customers'])

      queryClient.setQueryData(['customers'], (old) => {
        if (!old?.customers) return old
        const filtered = old.customers.filter((c) => c.id !== id)
        return { ...old, customers: filtered }
      })

      return { previousData }
    },
    onError: (err, variables, context) => {
      if (context?.previousData) {
        queryClient.setQueryData(['customers'], context.previousData)
      }
      toast.error('Failed to delete customer')
    },
    onSuccess: () => {
      toast.success('Customer deleted successfully')
    },
    onSettled: () => {
      queryClient.invalidateQueries(['customers'])
    },
  })
}