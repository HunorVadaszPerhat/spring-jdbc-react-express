import React from 'react'
import { useCustomers } from '../../hooks/useCustomers'

const CustomersList = () => {
  const { data, isLoading, isError, error } = useCustomers()

  if (isLoading) return <p>Loading...</p>
  if (isError) return <p>Error: {error.message}</p>

  return (
    <ul>
      {data.map(customer => (
        <li key={customer.customerNumber}>{customer.customerName}</li>
      ))}
    </ul>
  )
}

export default CustomersList