คู่มือ Deploy อัตโนมัติ (GitHub Actions + Docker Hub + SSH)

ภาพรวม
- Workflow ที่สร้างไว้: `.github/workflows/deploy.yml`
- Dockerfile อยู่ที่: `Dockerfile`
- หลักการ: เมื่อ push ไป `main` จะ build JAR → สร้าง Docker image → push ไป Docker Hub → SSH ไปเครื่องปลายทางเพื่อ `docker pull` + `docker run` ใหม่

สิ่งที่ต้องเตรียม (local)
1. สร้าง Docker Hub repository (ตัวอย่าง): `youruser/my-ci-cd-backend`
2. สร้าง SSH key สำหรับ GitHub Actions (บนเครื่องของคุณ):

```bash
ssh-keygen -t rsa -b 4096 -C "deploy-key" -f ~/.ssh/my_ci_cd_deploy -N ""
```
- เก็บไฟล์ `~/.ssh/my_ci_cd_deploy` (private) และ `~/.ssh/my_ci_cd_deploy.pub` (public)

3. ติดตั้ง public key บนเซิร์ฟเวอร์ปลายทาง (ตัวอย่าง user: `ubuntu`):

```bash
# บนเครื่อง local
cat ~/.ssh/my_ci_cd_deploy.pub
# คัดลอกค่าและบน remote server ทำ
mkdir -p ~/.ssh
echo "<public-key-contents>" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
```

4. ติดตั้ง Docker บน remote server (ถ้ายังไม่มี):

```bash
# ตัวอย่าง Ubuntu
sudo apt update
sudo apt install -y docker.io
sudo usermod -aG docker $USER
# ออกจากระบบแล้ว login ใหม่ (หรือ reboot) เพื่อให้ group membership มีผล
```

ตั้งค่า Secrets ใน GitHub
- เปิด GitHub → Repository → Settings → Secrets and variables → Actions → New repository secret
- โดยทั่วไปต้องมี:
  - `DOCKERHUB_USERNAME` — Docker Hub username
  - `DOCKERHUB_TOKEN` — Docker Hub access token หรือรหัสผ่าน
  - `IMAGE_NAME` — เช่น `youruser/my-ci-cd-backend`
  - `SSH_PRIVATE_KEY` — เนื้อหาของ `~/.ssh/my_ci_cd_deploy` (private key)
  - `SSH_HOST` — IP หรือ hostname ของเซิร์ฟเวอร์
  - `SSH_USER` — ชื่อผู้ใช้บนเซิร์ฟเวอร์ (เช่น `ubuntu`)

- ตัวอย่างใช้ `gh` CLI เพื่อเซ็ต secret:

```bash
gh secret set DOCKERHUB_USERNAME --body "youruser"
gh secret set DOCKERHUB_TOKEN --body "<token>"
gh secret set IMAGE_NAME --body "youruser/my-ci-cd-backend"
gh secret set SSH_PRIVATE_KEY --body "$(cat ~/.ssh/my_ci_cd_deploy)"
gh secret set SSH_HOST --body "1.2.3.4"
gh secret set SSH_USER --body "ubuntu"
```

การทดสอบ workflow
1. Push โค้ดและไฟล์ Dockerfile + workflow ไปยัง branch `main` (หรือสร้าง PR แล้ว merge)
2. ไปที่แท็บ Actions ใน GitHub แล้วดู job logs
3. เมื่อสำเร็จ ตรวจสอบบน remote server ว่ามี container รัน:

```bash
ssh -i ~/.ssh/my_ci_cd_deploy ubuntu@1.2.3.4
docker ps | grep my-ci-cd-backend
```

คำแนะนำ / ข้อควรระวัง
- ตรวจสอบว่า `Dockerfile` ใช้ค่า `ARG JAR_FILE` ตรงกับชื่อ JAR ที่สร้าง (pom.xml และ artifactId/version)
- Secrets ต้องเก็บเป็นความลับเสมอ ห้าม commit private key หรือ token ลงใน repo
- ถาต้องการ port อื่น ปรับ `docker run -p` ใน workflow script
- หากต้องการ rollback: บน remote server ให้หยุดคอนเทนเนอร์ปัจจุบันแล้ว `docker pull` เวอร์ชันก่อนหน้า หรือใช้ tag ต่างหาก

ต้องการให้ผมช่วยเพิ่มเติม:
- สร้าง README/ขั้นตอนแบบย่อใน `README.md` ด้วยไหม
- ปรับ workflow ให้ใช้ `workflow_dispatch` เพื่อรันแมนนวล
- ทำให้ workflow push image ไป GitHub Container Registry แทน Docker Hub

ไฟล์ที่ผมเพิ่มให้:
- [Dockerfile](Dockerfile)
- [.dockerignore](.dockerignore)
- [.github/workflows/deploy.yml](.github/workflows/deploy.yml)

