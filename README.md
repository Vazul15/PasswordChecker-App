# PasswordChecker-App
Pass-Checker App is a **password security and lookup tool** designed to quickly check for strings within a mounted file.  

### How It Works  
  - The user **mounts a file**.  
  - The application **reads from the mounted file** and performs fast lookups.  
  - The results are displayed instantly to the user.  


## Getting Started  

### Prerequisites  

#### Environment Variables  
Create an `.env` file **in the root directory of the project** with the following key-value pairs:  

```bash
DATASOURCE_USERNAME=your_datasource_username
DATASOURCE_PASSWORD=your_datasource_password
DATASOURCE_URL=your_datasource_url
DIRECTORY_FOR_CHECK=path_for_your_directory_you_want_to_mount
```

## Installation  

### 🔹 Clone the repository  
```bash
git clone git@github.com:Vazul15/PasswordChecker-App.git
```

### 🔹 Build and Run the application
```bash
make build
```


## Demo Screenshots / USAGE

### Directory-Based Password Lookup  
Simply specify the directory name within the mounted directory, and the application will search for the given password within that location.  
![PassChecker Screenshot](Readme-pics/passchecker1.png)  

### History View  
Get detailed insights into backend access, including user activity, originating IP addresses, and access history logs.  
![PassChecker Screenshot](Readme-pics/passchecker2.png)  

### API Documentation  
Comprehensive documentation is available for backend API calls, ensuring seamless integration and usage.  
![PassChecker Screenshot](Readme-pics/passchecker3.png)  


---

##  Used Technologies  

### **Frontend**  
- [![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)](https://reactjs.org/)  
- [![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)  
- [![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/)  

### **Backend**  
- [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)  
- [![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)  
- [![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)](https://swagger.io/)  

### **Database**  
- [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)  

### **Containerization**  
- [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)  


---
