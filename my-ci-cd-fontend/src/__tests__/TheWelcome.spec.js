// src/components/__tests__/TheWelcome.spec.js

import { describe, it, expect, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import TheWelcome from '../components/TheWelcome.vue'
import axios from 'axios';


// 1. Mock Axios ทั้ง Module
vi.mock('axios')

describe('TheWelcome - API Integration', () => {
  it('displays message fetched from backend API after component mounts', async () => {
    // 2. กำหนดค่า Mock ให้ axios.get ตอบกลับด้วยข้อความที่คาดหวังจาก Backend
    // (ข้อความที่ตรงกับ HelloController.java: "Hello from Spring Boot!")
    axios.get.mockResolvedValue({ data: 'Hello from Spring Boot!' }) 
    
    // 3. Mount Component
    const wrapper = mount(TheWelcome)
    
    // 4. ตรวจสอบสถานะ Loading ข้อความเริ่มต้น
    expect(wrapper.text()).toContain('Loading message from Backend...')
    
    // 5. รอให้ Promises (การเรียก API) resolved และ Vue อัปเดต DOM
    await new Promise(process.nextTick) 

    // 6. คาดหวังว่า Component จะแสดงข้อความที่ถูก Mock
    expect(wrapper.text()).toContain('Hello from Spring Boot!')
    
    // 7. ยืนยันว่า Axios ถูกเรียกไปยัง Endpoint ที่ถูกต้อง
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/api/hello')
  })

  it('displays error message if API call fails', async () => {
    // 8. กำหนดค่า Mock ให้ Axios.get ตอบกลับด้วย Error
    axios.get.mockRejectedValue(new Error('Network Error')) 
    
    const wrapper = mount(TheWelcome)
    
    // รอให้ Promises resolved
    await new Promise(process.nextTick) 

    // 9. คาดหวังว่า Component จะแสดงข้อความ Error ที่เรากำหนดไว้ใน Catch Block
    expect(wrapper.text()).toContain('Error connecting to Spring Boot Backend!')
  })
})