import React, { useState } from 'react'
import {
  useCustomers,
  useCreateCustomer,
  useUpdateCustomer,
  useDeleteCustomer,
} from '../../hooks/customerApi'

const CustomerManager = () => {
  const { data: customers, isLoading, isError, error } = useCustomers()
  const createCustomer = useCreateCustomer()
  const updateCustomer = useUpdateCustomer()
  const deleteCustomer = useDeleteCustomer()

  const [form, setForm] = useState({
    id: '',
    customerName: '',
    mode: 'create', // or 'update'
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    const { id, customerName, mode } = form
    const customerData = { customerName }

    if (mode === 'create') {
      createCustomer.mutate(customerData)
    } else if (mode === 'update') {
      updateCustomer.mutate({ id, customerData })
    }

    setForm({ id: '', customerName: '', mode: 'create' })
  }

  const handleEdit = (customer) => {
    setForm({
      id: customer.id,
      customerName: customer.customerName,
      mode: 'update',
    })
  }

  const handleDelete = (id) => {
    deleteCustomer.mutate(id)
  }

  if (isLoading) return <p>Loading...</p>
  if (isError) return <p>Error: {error.message}</p>

  return (
    <div style={{ padding: '1rem' }}>
      <h2>Customer Manager</h2>

      {/* FORM */}
      <form onSubmit={handleSubmit} style={{ marginBottom: '1rem' }}>
        {form.mode === 'update' && (
          <p>Editing customer ID: <strong>{form.id}</strong></p>
        )}
        <input
          name="customerName"
          placeholder="Customer Name"
          value={form.customerName}
          onChange={handleChange}
          required
        />
        <button type="submit">
          {form.mode === 'create' ? 'Add Customer' : 'Update Customer'}
        </button>
        {form.mode === 'update' && (
          <button type="button" onClick={() => setForm({ id: '', customerName: '', mode: 'create' })}>
            Cancel
          </button>
        )}
      </form>

      {/* LIST */}
      <ul className="unstyled-list">
        {customers.map(customer => (
          <li key={customer.customerNumber}>
            {customer.customerName}{' '}
            <button onClick={() => handleEdit(customer)}>Edit</button>{' '}
            <button onClick={() => handleDelete(customer.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default CustomerManager
