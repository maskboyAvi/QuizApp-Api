# Quiz App - Spring Boot API

## Overview
This is a Quiz App API built using Spring Boot. The application provides endpoints to:
1. Start a new quiz session.
2. Get a random multiple-choice question from a predefined dataset.
3. Submit an answer for a question.
4. Retrieve a summary of the quiz session, including total questions answered and the number of correct and incorrect submissions.

The application uses an in-memory H2 database to store quiz questions and session data. Seed data is automatically added to the database at startup.

## Setup Instructions

### Prerequisites
1. Java 17 or higher.
2. Maven 3.6 or higher.
3. A REST client like ThunderClient, Postman, or cURL for testing the APIs.

### Steps to Set Up and Run
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/maskboyAvi/QuizApp-Api.git
   cd QuizApp-Api
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   By default, the application runs on `http://localhost:8080`.

4. **Access H2 Console (Optional):**
   The H2 in-memory database console is available at `http://localhost:8080/h2-console`.
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **Username:** `sa`
   - **Password:** Leave empty

---

## API Documentation

### Base URL
All endpoints are prefixed with `/api/quiz`.

### 1. Start New Quiz Session
**Endpoint:**
```
POST /api/quiz/start
```
**Description:**
Starts a new quiz session for a user.

**Request Parameters:**
- `username` (String, required): The username for the quiz session.

**Example Request:**
```bash
curl -X POST "http://localhost:8080/api/quiz/start?username=aviral_katiyar"
```

**Response:**
- Status: `201 Created`
- Body:
```json
{
  "sessionId": 1,
  "username": "aviral_katiyar",
  "questionsAnswered": 0,
  "correctAnswers": 0
}
```

---

### 2. Get Random Question
**Endpoint:**
```
GET /api/quiz/question
```
**Description:**
Fetches a random question for a given quiz session.

**Request Parameters:**
- `sessionId` (Long, required): The ID of the quiz session.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/api/quiz/question?sessionId=1"
```

**Response:**
- Status: `200 OK`
- Body:
```json
{
  "questionId": 101,
  "questionText": "What is the capital of France?",
  "options": ["Paris", "Berlin", "Rome", "Madrid"]
}
```
- Status: `204 No Content` if no more questions are available.

---

### 3. Submit Answer
**Endpoint:**
```
POST /api/quiz/answer
```
**Description:**
Submits an answer for a question in the quiz session.

**Request Parameters:**
- `sessionId` (Long, required): The ID of the quiz session.
- `questionId` (Long, required): The ID of the question being answered.
- `answer` (String, required): The selected answer.

**Example Request:**
```bash
curl -X POST "http://localhost:8080/api/quiz/answer?sessionId=1&questionId=101&answer=Paris"
```

**Response:**
- Status: `200 OK`
- Body:
```json
{
  "isCorrect": true
}
```

---

### 4. Get Quiz Session Summary
**Endpoint:**
```
GET /api/quiz/summary
```
**Description:**
Retrieves the quiz session summary, including the number of questions answered, and details on correct and incorrect submissions.

**Request Parameters:**
- `sessionId` (Long, required): The ID of the quiz session.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/api/quiz/summary?sessionId=1"
```

**Response:**
- Status: `200 OK`
- Body:
```json
{
  "sessionId": 1,
  "username": "aviral_katiyar",
  "questionsAnswered": 5,
  "correctAnswers": 3,
  "incorrectAnswers": 2
}
```

---

## Assumptions
1. The dataset contains multiple-choice questions with exactly four options per question.
2. The application supports a single user per session.
3. Once all questions are attempted, the API will return a `204 No Content` status for fetching new questions.

---

## Dataset
The application seeds the following questions into the database on startup:

| Question ID | Question Text                      | Option 1 | Option 2 | Option 3 | Option 4 | Correct Answer |
|-------------|------------------------------------|----------|----------|----------|----------|----------------|
| 101         | What is the capital of France?    | Paris    | Berlin   | Rome     | Madrid   | Paris          |
| 102         | What is 2 + 2?                    | 3        | 4        | 5        | 6        | 4              |
| 103         | Which planet is known as the Red Planet? | Earth | Mars     | Jupiter  | Saturn   | Mars           |
| 104         | Who wrote 'Hamlet'?               | Dickens  | Chaucer  | Shakespeare | Orwell | Shakespeare   |
| 105         | What is the largest ocean?        | Atlantic | Pacific  | Indian   | Arctic   | Pacific        |

---

## Testing the APIs
You can test the APIs using any REST client like ThunderClient, Postman, or cURL.

### Example Workflow:
1. Start a new quiz session:
   ```bash
   POST http://localhost:8080/api/quiz/start?username=aviral_katiyar
   ```

2. Get a random question:
   ```bash
   GET http://localhost:8080/api/quiz/question?sessionId=1
   ```

3. Submit an answer:
   ```bash
   POST http://localhost:8080/api/quiz/answer?sessionId=1&questionId=101&answer=Paris
   ```

4. Get the quiz summary:
   ```bash
   GET http://localhost:8080/api/quiz/summary?sessionId=1
   ```

