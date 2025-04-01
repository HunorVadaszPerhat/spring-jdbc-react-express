import React from 'react'

const SortableHeader = ({ header }) => {
  const column = header.column
  const sortState = column.getIsSorted() // 'asc', 'desc', or false

  return (
    <span style={{ userSelect: 'none', display: 'inline-flex', alignItems: 'center' }}>
      {header.column.columnDef.header}
      <span style={{ fontSize: '0.8rem', marginLeft: '5px' }}>
        <span
          onClick={(e) => {
            e.stopPropagation()
            if (sortState !== 'asc') {
              column.toggleSorting(false) // sort ascending
            }
          }}
          style={{
            opacity: sortState === 'asc' ? 1 : 0.3,
            cursor: 'pointer',
            marginRight: '2px',
          }}
        >
          🔼
        </span>
        <span
          onClick={(e) => {
            e.stopPropagation()
            if (sortState !== 'desc') {
              column.toggleSorting(true) // sort descending
            }
          }}
          style={{
            opacity: sortState === 'desc' ? 1 : 0.3,
            cursor: 'pointer',
          }}
        >
          🔽
        </span>
      </span>
    </span>
  )
}

export default SortableHeader
