# 🚀 การตั้งค่า CD Pipeline สำหรับ Docker Hub

## ✅ สิ่งที่ได้ทำการอัปเดต

เพิ่ม Deploy Job ใน GitHub Actions workflows สำหรับ:
- **Backend CI/CD**: [.github/workflows/backend-ci.yml](.github/workflows/backend-ci.yml)
- **Frontend CI/CD**: [.github/workflows/frontend-ci.yml](.github/workflows/frontend-ci.yml)

## 📋 ขั้นตอนการตั้งค่า GitHub Secrets

### 1. สร้าง Docker Hub Access Token

1. เข้าสู่ระบบ [Docker Hub](https://hub.docker.com/)
2. คลิกที่ชื่อผู้ใช้ของคุณ (มุมขวาบน) → **Account Settings**
3. เลือก **Security** → **New Access Token**
4. ตั้งชื่อ Token เช่น `github-actions-ci-cd`
5. เลือก permissions: **Read, Write, Delete**
6. คลิก **Generate** และ **คัดลอก token ที่ได้** (จะแสดงครั้งเดียวเท่านั้น!)

### 2. เพิ่ม Secrets ใน GitHub Repository

1. ไปที่ GitHub Repository ของคุณ
2. คลิก **Settings** → **Secrets and variables** → **Actions**
3. คลิก **New repository secret** และเพิ่ม Secrets ต่อไปนี้:

#### Secret ที่ 1: DOCKER_USERNAME
- **Name**: `DOCKER_USERNAME`
- **Value**: ชื่อผู้ใช้ Docker Hub ของคุณ (เช่น `yourname`)
- คลิก **Add secret**

#### Secret ที่ 2: DOCKER_PASSWORD
- **Name**: `DOCKER_PASSWORD`
- **Value**: Access Token ที่คัดลอกไว้จากขั้นตอนที่ 1
- คลิก **Add secret**

### 3. ตรวจสอบ Secrets ที่เพิ่มแล้ว

หลังจากเพิ่มเสร็จ ควรเห็น Secrets 2 ตัว:
- ✅ `DOCKER_USERNAME`
- ✅ `DOCKER_PASSWORD`

## 🔄 การทำงานของ Pipeline

### Backend CI/CD Flow
```
1. Push code → branch "demo"
2. Job: build-and-test
   - Setup JDK 21
   - Run Maven tests
3. Job: deploy (รันหลัง test ผ่าน)
   - Build Docker image
   - Push to Docker Hub as:
     • yourname/my-ci-cd-backend:latest
     • yourname/my-ci-cd-backend:<commit-sha>
```

### Frontend CI/CD Flow
```
1. Push code → branch "demo"
2. Job: test-frontend
   - Setup Node.js 20
   - Install dependencies (pnpm)
   - Run Vitest tests
3. Job: deploy (รันหลัง test ผ่าน)
   - Build Docker image
   - Push to Docker Hub as:
     • yourname/my-ci-cd-frontend:latest
     • yourname/my-ci-cd-frontend:<commit-sha>
```

## 🎯 คุณสมบัติพิเศษ

- ✅ **Auto Deploy**: Deploy อัตโนมัติเมื่อ push ไป branch `demo`
- ✅ **Build Cache**: ใช้ Docker layer caching เพื่อเร่ง build time
- ✅ **Version Tagging**: มี 2 tags คือ `latest` และ `commit-sha`
- ✅ **Conditional Deploy**: Deploy เฉพาะเมื่อ test ผ่านทั้งหมด

## 🧪 ทดสอบการทำงาน

1. แก้ไขไฟล์ใน `my-ci-cd-backend/` หรือ `my-ci-cd-fontend/`
2. Commit และ push ไป branch `demo`:
   ```bash
   git add .
   git commit -m "test: trigger CI/CD pipeline"
   git push origin demo
   ```
3. ดูผลลัพธ์ที่ GitHub Actions tab ใน repository
4. หลังจาก pipeline เสร็จ ตรวจสอบที่ Docker Hub → Repositories

## 📦 การใช้งาน Docker Images

Pull และรัน backend:
```bash
docker pull <your-dockerhub-username>/my-ci-cd-backend:latest
docker run -p 8080:8080 <your-dockerhub-username>/my-ci-cd-backend:latest
```

Pull และรัน frontend:
```bash
docker pull <your-dockerhub-username>/my-ci-cd-frontend:latest
docker run -p 5173:5173 <your-dockerhub-username>/my-ci-cd-frontend:latest
```

หรือใช้ docker-compose (อัปเดต [docker-compose.yml](docker-compose.yml) ให้ชื่อ images):
```yaml
services:
  backend:
    image: <your-dockerhub-username>/my-ci-cd-backend:latest
  frontend:
    image: <your-dockerhub-username>/my-ci-cd-frontend:latest
```

## 🔐 Security Best Practices

- ✅ ใช้ Access Token แทน Password จริง
- ✅ เก็บ Secrets ใน GitHub Secrets (ไม่ hardcode ในโค้ด)
- ✅ Token ควรมี permissions เฉพาะที่จำเป็น
- ✅ Rotate token เป็นระยะเพื่อความปลอดภัย

## 🛠️ Troubleshooting

### ❌ Error: "denied: requested access to the resource is denied"
- ตรวจสอบว่า `DOCKER_USERNAME` และ `DOCKER_PASSWORD` ถูกต้อง
- ตรวจสอบว่า Docker Hub token ยังไม่หมดอายุ

### ❌ Error: "repository does not exist"
- Repository จะถูกสร้างอัตโนมัติในครั้งแรกที่ push
- ตรวจสอบว่าชื่อ image ตรงกับ username

### ⚠️ Build ช้า
- Pipeline ใช้ Docker layer caching แล้ว
- Build ครั้งแรกจะช้า แต่ครั้งถัดไปจะเร็วขึ้น

## 📚 เอกสารเพิ่มเติม

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Docker Hub Documentation](https://docs.docker.com/docker-hub/)
- [Docker Build Push Action](https://github.com/docker/build-push-action)
