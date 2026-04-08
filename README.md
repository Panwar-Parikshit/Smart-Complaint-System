# 🚀 Smart Complaint System (DAA-Enhanced)

## 📌 Overview

The **Smart Complaint System** is an intelligent complaint management platform designed using **Data Structures + Design and Analysis of Algorithms (DAA)** concepts.

It enables:

* 📥 Complaint submission
* ⚡ Smart prioritization
* 🤖 Auto-classification
* 🔗 Related complaint detection
* 🧠 Efficient processing using algorithms
* 📊 **Explainable priority system (NEW ⭐)**

---

## 🎯 Objective

To build a **real-world scalable system** that:

* Reduces manual effort in complaint handling
* Optimizes complaint processing using **DAA techniques**
* Demonstrates practical use of algorithms in software systems
* Provides **transparent & explainable decision-making**

---

## 🧠 DAA Concepts Used

| Algorithm            | Use Case                            |
| -------------------- | ----------------------------------- |
| **Greedy Algorithm** | Priority-based complaint scheduling |
| **Graph (BFS)**      | Detect related/duplicate complaints |
| **String Matching**  | Auto category detection             |
| **Binary Search**    | Fast complaint lookup               |
| **Hashing**          | O(1) complaint retrieval            |

---

## 🔥 Key Features (UPDATED)

### ✅ 1. Smart Priority Scheduling (Greedy)

* Complaints are processed based on priority
* Highest priority handled first
* Admin can view **priority order list**

---

### 🤖 2. Auto Category Detection (String Matching)

* System detects category from description automatically
* Example:

  * “server down” → IT
  * “electric issue” → Maintenance

---

### 🔗 3. Related Complaint Detection (Graph + BFS)

* Detects similar complaints
* Helps reduce duplication
* Shows related complaint IDs in Admin panel

---

### 📊 4. Explainable Priority System (NEW ⭐)

* Shows **why a complaint is prioritized**
* Example output:

```
Severity (9) × 0.5
Urgency (8) × 0.3
Impact (7) × 0.2

Final Priority = 8.2
```

👉 Makes system **transparent & user-friendly**

---

### ⚡ 5. Fast Search (Binary + Hashing)

* Instant lookup using HashMap
* Optimized searching

---

### 📊 6. Admin Intelligence Panel (UPDATED)

* Process complaints (Greedy)
* View related complaints (BFS)
* View full priority order list
* See **priority explanation**

---

### 👥 7. Role-Based System

* 👤 User → Submit complaints
* 🧑‍💼 Team → Manage complaints
* 🛠️ Admin → Smart processing

---

## 🏗️ Project Architecture

```
UI Layer (Swing)
   ↓
Service Layer (DAA Logic)
   ↓
DAO Layer (Database + DAA Integration)
   ↓
MySQL Database
```

---

## 🔄 Data Flow (NEW)

```
User submits complaint
   ↓
Stored in Database (DAO)
   ↓
Also added to:
   → Priority Queue (Greedy)
   → Graph (BFS)
   → HashMap (Fast Access)
```

---

## 📁 Project Structure

```
src/
 ├── dao/
 │   ├── ComplaintDAO.java   ← DB + Service Integration ⭐
 │   ├── UserDAO.java
 │   └── DBConnection.java
 │
 ├── model/
 │   ├── Complaint.java
 │   └── User.java
 │
 ├── service/
 │   └── ComplaintService.java ← ALL DAA LOGIC ⭐
 │
 ├── ui/
 │   ├── LoginUI.java
 │   ├── UserUI.java   ← Auto Category Detection ⭐
 │   ├── TeamUI.java
 │   └── AdminUI.java  ← Greedy + BFS + Explainable ⭐
 │
 └── util/
     └── AppContext.java
```

---

## ⚙️ Tech Stack

* **Language:** Java
* **UI:** Java Swing
* **Database:** MySQL
* **Concepts:** DSA + DAA

---

## 🛠️ How to Run

### 1️⃣ Install Requirements

* Java JDK (17+)
* MySQL Server
* VS Code / IntelliJ

---

### 2️⃣ Setup Database

```sql
CREATE DATABASE complaint_system;

USE complaint_system;

CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    severity INT,
    urgency INT,
    impact INT,
    priority DOUBLE,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100),
    role VARCHAR(50)
);
```

---

### 3️⃣ Add Sample Users

```sql
INSERT INTO users VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO users VALUES ('user1', 'user123', 'USER');
INSERT INTO users VALUES ('it_team', 'team123', 'IT');
```

---

### 4️⃣ Update DB Credentials

Edit:

```
src/dao/DBConnection.java
```

---

### 5️⃣ Run Project

👉 Run:

```
LoginUI.java
```

---

## 🔑 Login Credentials

| Role  | Username | Password |
| ----- | -------- | -------- |
| Admin | admin    | admin123 |
| User  | user1    | user123  |
| Team  | it_team  | team123  |

---

## 🚀 How It Works

### 👤 User Flow

1. Login
2. Submit complaint
3. Auto category detection
4. Priority calculated

---

### 🛠️ Admin Flow (UPDATED ⭐)

1. Click **Process Complaint**
2. Highest priority selected (Greedy)
3. Related complaints shown (BFS)
4. Priority explanation displayed

---

### 👨‍💼 Team Flow

1. View assigned complaints
2. Update status
3. Search complaints

---

## 🌟 Unique Points (MENTOR CATCHY 🔥)

* Real-world use of **DAA algorithms**
* Graph-based complaint linking
* Intelligent auto classification
* ⚡ **Explainable priority system (RARE FEATURE)**
* UI + Algorithm integration
* Scalable architecture

---

## 📈 Future Enhancements

* 📱 Web / Mobile version
* 🤖 NLP-based classification
* 📊 Analytics dashboard
* ☁️ Cloud deployment

---

## 🧠 Learning Outcomes

* Practical use of **DAA concepts**
* Algorithm integration in real systems
* Layered architecture design
* Problem-solving using optimization techniques

---

## 📌 Conclusion

This project demonstrates how **Design and Analysis of Algorithms** can be applied to build:

* Efficient
* Scalable
* Intelligent
* Explainable systems

---

## 👨‍💻 Authors

* **Shubham Kumar**
* **Parikshit Singh**
* **Avni Negi**
* **Jahnvi Sharma**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!

