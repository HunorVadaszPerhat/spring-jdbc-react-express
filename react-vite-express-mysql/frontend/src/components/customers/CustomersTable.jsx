import React, { useMemo, useState } from 'react'
import {
    useReactTable,
    getCoreRowModel,
    getSortedRowModel,
    getFilteredRowModel,
    flexRender,
  } from '@tanstack/react-table'
import { useCustomers } from '../../hooks/customerApi'
import SortableHeader from '../../common/SortableHeader'
import DebouncedInput from '../../common/DebouncedInput'

const CustomersTable = () => {
    const [pageIndex, setPageIndex] = useState(0)
    const pageSize = 3

    const { data : response, isLoading, isError, error } = useCustomers({ pageIndex, pageSize })
  const [sorting, setSorting] = useState([])
  const [columnFilters, setColumnFilters] = useState([])
  const data = useMemo(() => response?.data || [], [response])
    const total = response?.total || 0

/* 
// columns manually defined   
const columns = useMemo(
    () => [
      { accessorKey: 'customerNumber', header: 'Customer #' },
      { accessorKey: 'customerName', header: 'Name' },
      { accessorKey: 'contactName', header: 'Contact' },
      { accessorKey: 'phone', header: 'Phone' },
      { accessorKey: 'address', header: 'Address' },
      { accessorKey: 'city', header: 'City' },
      { accessorKey: 'state', header: 'State' },
      { accessorKey: 'country', header: 'Country' },
      { accessorKey: 'creditLimit', header: 'Credit Limit' },
    ],
    []
  ) */

  // Dynamic Column Generation
/*   const columns = useMemo(() => {
    if (!data || data.length === 0) return []

    return Object.keys(data[0])
      .filter(key => key !== '_links') // optionally skip unwanted fields
      .map(key => ({
        accessorKey: key,
        header: key.charAt(0).toUpperCase() + key.slice(1), // Capitalize header
      }))
  }, [data]) */

  // Dynamic Column Generation + Debug Template
  const columns = useMemo(() => {
    if (!data || data.length === 0) {
      console.log('[DEBUG] No data to generate columns.')
      return []
    }
  
    const keys = Object.keys(data[0])
    console.log('[DEBUG] Keys in first data object:', keys)
  
    const filteredKeys = keys.filter(key => key !== '_links')
    console.log('[DEBUG] Filtered keys:', filteredKeys)
  
    const columnDefs = filteredKeys.map(key => {
      const col = {
        accessorKey: key,
        header: key.charAt(0).toUpperCase() + key.slice(1),
        filterFn: (row, columnId, filterValue) => {
            const val = String(row.getValue(columnId)).toLowerCase()
            const input = String(filterValue).toLowerCase()
            return val.startsWith(input)
          },
      }
      console.log('[DEBUG] Column generated:', col)
      return col
    })
  
    return columnDefs
  }, [data])
  

  const table = useReactTable({
    data,
    columns,
    pageCount: Math.ceil(total / pageSize),
    state: {
        pagination: {
            pageIndex,
            pageSize,
    },
        sorting,
        columnFilters,
    },
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    manualPagination: true,
    onPaginationChange: updater => {
        const nextPage = typeof updater === 'function'
        ? updater({ pageIndex }).pageIndex
        : updater.pageIndex
    setPageIndex(nextPage)
  },
  })

    if (isLoading) return <div>Loading...</div>
    
    if (isError) {
    console.error('[Query Error]', error)
    return <p>Error loading data.</p>
    }

  return (
    <div>
      <h2>Customers</h2>
      <table border="1" cellPadding="5" style={{ width: '100%', borderCollapse: 'collapse' }}>
      <thead>
        {table.getHeaderGroups().map(headerGroup => (
            <tr key={headerGroup.id}>
            {headerGroup.headers.map(header => (
                <th key={header.id}>
                <SortableHeader header={header} />
                {header.column.getCanFilter() && (
                    <DebouncedInput
                        value={header.column.getFilterValue() ?? ''}
                        onChange={value => header.column.setFilterValue(value)}
                        placeholder="Filter..."
                        style={{ width: '100%', fontSize: '0.8rem', marginTop: '4px' }}
                    />
                )}
                </th>
            ))}
            </tr>
        ))}
        </thead>
        <tbody>
          {table.getRowModel().rows.map(row => (
            <tr key={row.id}>
              {row.getVisibleCells().map(cell => (
                <td key={cell.id}>
                  {flexRender(cell.column.columnDef.cell, cell.getContext())}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{ marginTop: '1rem' }}>
        <button onClick={() => setPageIndex(old => Math.max(old - 1, 0))} disabled={pageIndex === 0}>
            Previous
        </button>
        <span style={{ margin: '0 1rem' }}>
            Page {pageIndex + 1} of {Math.ceil(total / pageSize)}
        </span>
        <button
            onClick={() => setPageIndex(old => old + 1)}
            disabled={(pageIndex + 1) * pageSize >= total}
        >
            Next
        </button>
        </div>
    </div>
  )
}

export default CustomersTable
