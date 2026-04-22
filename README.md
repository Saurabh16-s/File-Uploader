# 📸 Image Uploader Web Application

A full-stack web application that allows users to upload and manage images. Uploaded images are stored securely in an AWS S3 bucket. The application is built using a modern tech stack with a Spring Boot backend and a ReactJS frontend.

---

## Features

*  Upload images from frontend
* Store images in AWS S3 bucket
*  Generate accessible image URLs
*  REST API for image handling
*  Fast and responsive UI
*  Secure backend integration

---

##  Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* AWS SDK (S3 Integration)

### Frontend

* ReactJS
* Axios
* HTML/CSS

### Cloud & DevOps

* AWS S3 (Storage)
* AWS EC2 (Deployment)
* Docker (Containerization)
* Nginx (Frontend hosting)

---

##  AWS Services Used

* Amazon S3 → Store uploaded images
* Amazon EC2 → Host backend server

---

##  Backend Setup

### 1. Clone the repository

```bash
git clone https://github.com/your-username/image-uploader.git
cd image-uploader/backend
```

### 2. Configure environment variables

Create `.env` or set application properties:

```properties
aws.s3.bucket-name=your-bucket-name
aws.access-key=YOUR_ACCESS_KEY
aws.secret-key=YOUR_SECRET_KEY
server.port=8081
```

---

### 3. Run the application

```bash
mvn clean install
java -jar target/image-uploader.jar
```

---

##  Run with Docker

```bash
docker build -t image-uploader .
docker run -d -p 8081:8081 image-uploader
```

---

##Frontend Setup

```bash
cd frontend
npm install
npm start
```

---

## 🔗 API Endpoints

| Method | Endpoint               | Description          |
| ------ | ---------------------- | -------------------- |
| POST   | `/api/v1/upload`       | Upload image         |
| GET    | `/api/v1/user-profile` | Fetch uploaded image |

---

##  Deployment

* Backend deployed on AWS EC2 using Docker
* Frontend served via Nginx
* Images stored in AWS S3

---

## Security Notes

* Never expose AWS credentials in code
* Use environment variables or IAM roles
* Restrict S3 bucket access

---

##  How It Works

1. User uploads image from React UI
2. Request sent to Spring Boot backend
3. Backend uploads file to AWS S3
4. S3 returns image URL
5. URL is stored/returned to frontend

---

##  Future Improvements

* User authentication (JWT)
* Image compression
* Multiple image uploads
* CDN integration (CloudFront)
* Drag & drop upload UI

---

##  Author

Your Name
GitHub: https://github.com/Saurabh16-s

---

##  If you like this project

Give it a star ⭐ on GitHub!
