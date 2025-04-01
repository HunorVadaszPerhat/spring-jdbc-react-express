import React, { useEffect, useState } from 'react'
import { debounce } from 'lodash'

const DebouncedInput = ({ value: initialValue, onChange, delay = 300, ...props }) => {
  const [value, setValue] = useState(initialValue)

  useEffect(() => {
    setValue(initialValue)
  }, [initialValue])

  const debounced = debounce(val => onChange(val), delay)

  const handleChange = (e) => {
    const val = e.target.value
    setValue(val)
    debounced(val)
  }

  return (
    <input
      {...props}
      value={value}
      onChange={handleChange}
    />
  )
}

export default DebouncedInput
