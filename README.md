 🏟️ CodingArena

An online coding challenge platform built with **Spring Boot** — where users can browse, attempt, and submit coding problems in a competitive programming style.

🚀 Features

- 📋 Browse coding challenges by difficulty (Easy / Medium / Hard)
- ✍️ Submit solutions and get instant feedback
- 👤 User registration and login
- 🏆 Leaderboard to track top performers
- 📊 Track your solved problems and progress
- 🎨 Clean and responsive UI

 🛠️ Tech Stack

| Layer      | Technology              |
|------------|-------------------------|
| Backend    | Java, Spring Boot       |
| Frontend   | HTML, CSS, JavaScript   |
| Build Tool | Gradle                  |
| Database   | MySQL / H2 (local)      |
| Template   | Thymeleaf               |

📸 Screenshots
 
⚙️ Getting Started (Run Locally)
  Prerequisites

- Java 17+
- MySQL (or use H2 in-memory for quick start)
- Git

Steps

bash
# 1. Clone the repository
git clone https://github.com/keerthisreem/CodingArena.git
cd CodingArena

# 2. Configure your database
# Edit src/main/resources/application.properties:
# spring.datasource.url=jdbc:mysql://localhost:3306/codingarena
# spring.datasource.username=your_username
# spring.datasource.password=your_password

# 3. Build and run
./gradlew bootRun


Then open your browser at: **http://localhost:8080**


## 📁 Project Structure


CodingArena/
├── src/
│   ├── main/
│   │   ├── java/        # Spring Boot controllers, services, models
│   │   └── resources/   # Templates, static files, application.properties
│   └── test/
├── build.gradle
└── settings.gradle


🙋‍♀️ Author

**Keerthisree M**
- GitHub: [@keerthisreem](https://github.com/keerthisreem)



📄 License

This Project is for academic purpose.
