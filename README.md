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

## การรันด้วย Docker Compose (ดึง image จาก Docker Hub)

```bash
docker-compose up -d
```

- Frontend: http://localhost:5173
- Backend: http://localhost:8080

ต้องการ build เองแทนดึงจาก Docker Hub? ปรับ compose ให้ใช้ block `build:` แล้วรัน `docker-compose up --build` ได้เช่นกัน

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

- Workflows: [.github/workflows/backend-ci.yml](.github/workflows/backend-ci.yml) และ [.github/workflows/frontend-ci.yml](.github/workflows/frontend-ci.yml) (trigger เมื่อ push ไป branch `demo` ตาม path filter)
- Secrets ที่ต้องมีใน GitHub Actions: `DOCKER_USERNAME`, `DOCKER_PASSWORD`
- Dockerfile backend: [my-ci-cd-backend/Dockerfile](my-ci-cd-backend/Dockerfile)
- Dockerfile frontend: [my-ci-cd-fontend/Dockerfile](my-ci-cd-fontend/Dockerfile)
- Images ที่ถูก push: `obeone67/my-ci-cd-backend` และ `obeone67/my-ci-cd-frontend` (tags: `latest` และ `<commit-sha>`)
- รายละเอียดการตั้งค่า Secrets และ flow ดูที่ [DEPLOYMENT_SETUP.md](DEPLOYMENT_SETUP.md)

### ดึงและรันจาก Docker Hub โดยตรง

```bash
docker pull obeone67/my-ci-cd-backend:latest
docker pull obeone67/my-ci-cd-frontend:latest
docker-compose up -d
```

หรือรันแยกเป็น service:

```bash
docker run -p 8080:8080 obeone67/my-ci-cd-backend:latest
docker run -p 5173:5173 obeone67/my-ci-cd-frontend:latest
```

## หมายเหตุ

- CORS เปิดให้ต้นทาง `http://localhost:5173` ใน [CorsConfig](my-ci-cd-backend/src/main/java/com/example/my_ci_cd_backend/config/CorsConfig.java)
- หากเปลี่ยนพอร์ตหรือโดเมนปรับค่า `VITE_API_URL` และ CORS ตามต้องการ
