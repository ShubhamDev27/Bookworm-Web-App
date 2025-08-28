# 📚 BookWorm – Full Stack Web App

A modern full-stack web application for discovering, purchasing, and renting books online.
BookWorm provides an intuitive platform where users can register, browse books by author or genre, add them to a cart, and complete checkout.

It’s built with **two backend options** – **ASP.NET Core (.NET 8)** or **Spring Boot (Java)**, **React (frontend)**, **MySQL (database)**, and secured using **JWT Authentication**.

---

## 🚀 Features

* **🔐 Authentication & Authorization** – Secure login & registration with JWT-based auth.
* **📖 Book Catalog** – Browse and search books by **title, author, or genre**.
* **🛒 Cart & Checkout** – Add books to cart and buy/rent with quantity controls.
* **📂 Personal Shelf** – Users can track purchased or rented books.
* **👩‍💻 Admin Controls** – Manage books, categories, and user data (future enhancement).
* **⚡ Modern UI** – Built with React + Bootstrap for a clean and responsive experience.

---

## 🛠 Tech Stack

---

| Layer        | Technology                                                    |
| ------------ | ------------------------------------------------------------- |
| **Frontend** | React.js (Bootstrap, Axios)                                   |
| **Backend**  | ASP.NET Core (.NET 8) or Spring Boot (Java)                   |
| **Database** | MySQL                                                         |
| **ORM**      | Entity Framework Core (for .NET) / Spring Data JPA (for Java) |
| **Auth**     | JWT (JSON Web Token)                                          |

---

---

## 📦 Getting Started

### ✅ Prerequisites

Make sure you have installed:

* [.NET 8 SDK](https://dotnet.microsoft.com/download/dotnet/8.0) (for .NET backend)
* [Java 17+ & Maven](https://www.oracle.com/java/technologies/downloads/) (for Spring Boot backend)
* [Node.js (LTS)](https://nodejs.org/)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)
* [Git](https://git-scm.com/)

### 🔧 Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/bookworm.git
   cd bookworm
   ```

2. **Backend Setup**

   **Option A: ASP.NET Core API**

   * Navigate to backend folder (e.g., `Bookworm.Api`).
   * Update your MySQL connection string in `appsettings.Development.json`:

     ```json
     "ConnectionStrings": {
       "DefaultConnection": "Server=localhost;Port=3306;Database=bookworm_db;Uid=your_user;Pwd=your_password;"
     }
     ```
   * Install dependencies & apply migrations:

     ```bash
     dotnet restore
     dotnet ef database update
     dotnet run
     ```
   * API will run at `https://localhost:5143`

   **Option B: Spring Boot (Java) API**

   * Navigate to Java backend folder (e.g., `bookworm-java`).
   * Update your MySQL connection in `application.properties` or `application-dev.properties`:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bookworm_db
     spring.datasource.username=your_user
     spring.datasource.password=your_password
     ```
   * Build & run:

     ```bash
     mvn clean install
     mvn spring-boot:run
     ```
   * API will run at `http://localhost:8080` (default for Spring Boot)

3. **Frontend Setup (React App)**

   * Navigate to frontend folder (e.g., `client`).
   * Create `.env.local` with backend API URL:

     ```env
     REACT_APP_API_BASE_URL=https://localhost:5143   # for .NET backend
     # or
     REACT_APP_API_BASE_URL=http://localhost:8080    # for Java backend
     ```
   * Install dependencies & start:

     ```bash
     npm install
     npm start
     ```
   * App will be available at `http://localhost:7162`

---

## 🐳 Docker (Optional)

Run the entire stack with one command:

```bash
docker-compose up --build -d
```

* **Frontend** → `http://localhost:5143`
* **Backend API (.NET)** → `https://localhost:5143`
* **Backend API (Java)** → `http://localhost:8080`

Stop with:

```bash
docker-compose down
```

---

## 📌 Roadmap

* [ ] Add role-based access (Admin/User)
* [ ] Implement payment integration
* [ ] Add book reviews & ratings
* [ ] Add AI ChatBot

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repo
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit changes (`git commit -m "Add YourFeature"`)
4. Push branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

```
```
