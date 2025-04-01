import './App.css'
import CustomersTable from './components/customers/CustomersTable'
import { Toaster } from 'react-hot-toast'

function App() {
  return (
    <>
      <Toaster position="top-right" />
      <CustomersTable/>
      
    </>
  )
}

export default App
