// เพิ่มโค้ดนี้ในไฟล์ TheWelcome.spec.js (สมมติว่าคุณใช้ Axios และเก็บข้อความในตัวแปร 'backendMessage')

// 1. นำเข้า Module ที่จำเป็น
import { describe, it, expect, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import TheWelcome from '../components/TheWelcome.vue'
import axios from 'axios'; // นำเข้า Axios เพื่อ Mock

// 2. Mocking Axios
vi.mock('axios')

describe('TheWelcome - API Integration', () => {
  it('displays message fetched from backend API', async () => {
    // กำหนดค่า Mock ให้ Axios.get ตอบกลับด้วยข้อความนี้
    axios.get.mockResolvedValue({ data: 'Hello from Spring Boot!' }) 
    
    const wrapper = mount(TheWelcome)
    
    // รอให้ Component ทำงาน onMounted และเรียก API เสร็จ (Promise resolved)
    await new Promise(process.nextTick) 

    // 3. คาดหวังว่า Component จะแสดงข้อความที่ถูก Mock
    expect(wrapper.text()).toContain('Hello from Spring Boot!')
  })
})