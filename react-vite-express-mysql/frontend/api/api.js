import axios from 'axios'

export const api = axios.create({
  baseURL: 'https://jsonplaceholder.typicode.com', // example API
})

export const springapi = axios.create({
    baseURL: 'http://localhost:1111/api', // example API
    // 👇 optional if using cookies or auth headers
    withCredentials: true,  
})

