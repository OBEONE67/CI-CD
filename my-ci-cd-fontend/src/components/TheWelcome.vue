<template>
  <div class="welcome-container">
    <div class="welcome-header">
      <h1>🚀 Frontend Applicationww</h1>
      <p class="subtitle">Connected to Spring Boot Backend</p>
    </div>
    
    <div class="welcome-content">
      <div class="message-box" :class="{ 'error': isError, 'success': !isError && !isLoading }">
        <div class="message-icon">{{ messageIcon }}</div>
        <div class="message-text">
          <h2>Backend Message</h2>
          <p>{{ backendMessage }}</p>
        </div>
      </div>

      <div class="info-section">
        <h3>📋 Application Info</h3>
        <ul>
          <li>Backend URL: <code>http://localhost:8080</code></li>
          <li>Frontend URL: <code>http://localhost:5173</code></li>
          <li>Status: <span :class="connectionStatus">{{ connectionStatus }}</span></li>
        </ul>
      </div>

      <div class="action-section">
        <button @click="retryConnection" class="btn-primary">🔄 Retry Connection</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const backendMessage = ref('Loading message from Backend...');
const isLoading = ref(true);
const isError = ref(false);

const messageIcon = computed(() => {
  if (isLoading.value) return '⏳';
  if (isError.value) return '❌';
  return '✅';
});

const connectionStatus = computed(() => {
  if (isLoading.value) return 'loading';
  if (isError.value) return 'disconnected';
  return 'connected';
});

const fetchBackendMessage = async () => {
  isLoading.value = true;
  isError.value = false;
  try {
    const response = await axios.get('http://localhost:8080/api/hello');
    backendMessage.value = response.data;
  } catch (error) {
    isError.value = true;
    backendMessage.value = 'Error connecting to Spring Boot Backend!';
    console.error("API Error:", error);
  } finally {
    isLoading.value = false;
  }
};

const retryConnection = () => {
  fetchBackendMessage();
};

onMounted(() => {
  fetchBackendMessage();
});
</script>


<style scoped>
* {
 margin: 0;
 padding: 0;
 box-sizing: border-box;
}

/* ------------------------------------------- */
/* 1. โครงสร้างหลัก (Default: Mobile-First) */
/* ------------------------------------------- */
.welcome-container {
 min-height: 100vh;
 background: #ffffff;
 display: flex; 
 align-items: center;
 justify-content: center;
 padding: 40px 20px;
 font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
 color: #111827; 
}

.main-wrapper {
    display: grid;
    grid-template-areas: 
        "header"
        "content";
    grid-gap: 30px; 
    max-width: 820px; /* ใช้ max-width ที่นี่แทน welcome-content */
    width: 100%;
}

.welcome-header {
  grid-area: header;
  text-align: center;
  margin-bottom: 0; 
  animation: slideDown 0.6s ease-out;
}

.welcome-header h1 {
   font-size: 2.2em;
   color: #0f172a; 
   margin-bottom: 6px;
   font-weight: 700;
   margin-right: 10px; /* ลบ margin-right เดิม */
}

.subtitle {
 font-size: 1em;
 color: #6b7280; 
}

.welcome-content {
    grid-area: content;
   background: #ffffff;
   border-radius: 12px;
   padding: 28px;
   box-shadow: 0 8px 30px rgba(15, 23, 42, 0.06);
   width: 50%;
   animation: slideUp 0.45s ease-out;
   border: 1px solid rgba(15,24,40,0.04);
}

/* ------------------------------------------- */
/* 2. องค์ประกอบภายใน (ใช้โค้ดเดิม) */
/* ------------------------------------------- */

.message-box {
 display: flex;
 gap: 18px;
 padding: 18px;
 border-radius: 12px;
 margin-bottom: 26px;
 border-left: 4px solid rgba(99,102,241,0.9);
 background: #fbfdff;
 transition: all 0.22s ease;
}

.message-box.error {
 border-left-color: #ef4444;
 background: #fff5f5;
}

.message-box.success {
 border-left-color: #16a34a;
 background: #f0fdf4;
}

.message-icon {
 font-size: 2.5em;
 flex-shrink: 0;
}

.message-text h2 {
 font-size: 1.2em;
 margin-bottom: 10px;
 color: #333;
}

.message-text p {
 font-size: 1em;
 color: #555;
 line-height: 1.6;
 word-break: break-word;
}

.info-section {
 background: #f5f5f5;
 padding: 20px;
 border-radius: 15px;
 margin-bottom: 20px;
}

.info-section h3 {
 color: #333;
 margin-bottom: 15px;
 font-size: 1.1em;
}

.info-section ul {
 list-style: none;
}

.info-section li {
 padding: 8px 0;
 color: #555;
 display: flex;
 align-items: center;
 gap: 10px;
}

.info-section code {
 background: #e0e0e0;
 padding: 3px 8px;
 border-radius: 4px;
 font-family: 'Courier New', monospace;
 font-size: 0.9em;
}

.info-section .connected {
 color: #51cf66;
 font-weight: bold;
}

.info-section .disconnected {
 color: #ff6b6b;
 font-weight: bold;
}

.info-section .loading {
 color: #ffa500;
 font-weight: bold;
}

.action-section {
 text-align: center;
}

.btn-primary {
 background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
 color: white;
 border: none;
 padding: 12px 30px;
 border-radius: 25px;
 font-size: 1em;
 cursor: pointer;
 transition: all 0.3s ease;
 box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
 font-weight: 600;
}

.btn-primary:hover {
 transform: translateY(-2px);
 box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.btn-primary:active {
 transform: translateY(0);
}

@keyframes slideDown {
 from {
  opacity: 0;
  transform: translateY(-30px);
 }
 to {
  opacity: 1;
  transform: translateY(0);
 }
}

@keyframes slideUp {
 from {
  opacity: 0;
  transform: translateY(30px);
 }
 to {
  opacity: 1;
  transform: translateY(0);
 }
}



/* ------------------------------------------- */
/* 4. Media Query สำหรับ Mobile (<= 600px) */
/* ------------------------------------------- */
@media (max-width: 600px) {
  .welcome-container {
    /* ใช้ flex เพื่อจัดกึ่งกลาง main-wrapper ในหน้าจอใหญ่ */
   display: block;
  }

 .welcome-header h1 {
  font-size: 2em;
 }

 .welcome-content {
  padding: inherit;
  width: 100%;
 }

 .message-box {
  flex-direction: column;
  gap: 10px;
 }

 .message-icon {
  font-size: 2em;
 }
}
</style>