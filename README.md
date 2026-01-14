# CI/CD Demo

โปรเจกต์ตัวอย่างประกอบด้วย Spring Boot backend และ Vue 3 + Vite frontend พร้อม workflow สำหรับ build / test / deploy ด้วย Docker และ GitHub Actions

## โครงสร้าง

- [my-ci-cd-backend](my-ci-cd-backend) — Spring Boot 4.0.1-SNAPSHOT (Java 21), Maven, REST endpoints เบื้องต้น
- [my-ci-cd-fontend](my-ci-cd-fontend) — Vue 3 + Vite (Node 20), ตัวหน้าเว็บสื่อสารกับ backend ผ่าน HTTP
- [docker-compose.yml](docker-compose.yml) — รันทั้งสอง service ในเครือข่ายเดียวกัน

## เตรียมเครื่อง

- Java 21 (ใช้ `mvnw` / `mvnw.cmd` ที่แนบมาได้)
- Node 20+ และ npm หรือ pnpm
- Docker (ถ้าจะรันผ่าน container หรือ `docker-compose`)

## การรันแบบโลคัล

### Backend

```bash
cd my-ci-cd-backend
./mvnw test            # รัน unit test + JaCoCo (threshold 70%)
./mvnw spring-boot:run # เริ่มที่พอร์ต 8080
```

### Frontend

```bash
cd my-ci-cd-fontend
npm install            # หรือ pnpm install
npm run dev -- --host  # เริ่มที่พอร์ต 5173
```

ตั้งค่า `VITE_API_URL` (เช่น `http://localhost:8080`) ใน env หรือ `.env` หากต้องการ override

## การรันด้วย Docker Compose

```bash
docker-compose up --build
```

- Frontend: http://localhost:5173
- Backend: http://localhost:8080

## API หลัก (backend)

- `GET /api/hello` → ข้อความต้อนรับ
- `GET /api/hello/{name}` → ทักทายตามชื่อ
- `POST /api/echo` (raw string body) → ตอบกลับข้อความเดิม
- `PUT /api/reverse` (raw string body) → คืนข้อความกลับด้าน
- `GET /api/status` → `OK`
- `GET /api/random` → ตัวเลขสุ่ม 0–100

## การทดสอบ

- Backend: `./mvnw test` (รายงาน coverage ที่ `my-ci-cd-backend/target/site/jacoco/index.html`)
- Frontend: `npm run test:unit` หรือ `npm run coverage` ใน [my-ci-cd-fontend](my-ci-cd-fontend)

## CI/CD & Deploy

- Dockerfile backend: [my-ci-cd-backend/Dockerfile](my-ci-cd-backend/Dockerfile)
- Dockerfile frontend: [my-ci-cd-fontend/Dockerfile](my-ci-cd-fontend/Dockerfile)
- Workflow อธิบายการ deploy อัตโนมัติผ่าน Docker Hub + SSH ดู [my-ci-cd-backend/DEPLOY.md](my-ci-cd-backend/DEPLOY.md)

## หมายเหตุ

- CORS เปิดให้ต้นทาง `http://localhost:5173` ใน [CorsConfig](my-ci-cd-backend/src/main/java/com/example/my_ci_cd_backend/config/CorsConfig.java)
- หากเปลี่ยนพอร์ตหรือโดเมนปรับค่า `VITE_API_URL` และ CORS ตามต้องการ
