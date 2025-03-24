# transaction-classifier-service

## Overview
This microservice provides an API to classify monetary transactions in a savings account. Users can view classified transactions and modify classifications as needed.

## Features
- Retrieve classified transactions for a user's savings account.
- Filter transactions by a specified date range.
- Simulated integration with external services for transaction data and classification.
- Allow users to update the classification of transactions.

## Classification Categories
Transactions are classified into the following categories:
- **Household**
- **Transport**
- **Food**
- **Entertainment**
- **Unknown** (if automatic classification is inconclusive)

## API Endpoints
### 1. Get Classified Transactions
**Endpoint:** `GET /v1/classifiedTransaction`

**Query Parameters:**
- `startDate` (YYYY-MM-DD) - Required
- `endDate` (YYYY-MM-DD) - Required

**Response:**
```json
[
    { "id": "241918475", "amount": -50.0, "date": "2024-03-10", "category": "UNKNOWN" },
    { "id": "373685732", "amount": -200.0, "date": "2024-03-11", "category": "HOUSEHOLD" }
]
```

### 2. Update Transaction Classification
**Endpoint:** `POST /v1/classifiedTransaction/recipient`

### 2.1. Update Transaction Classification
**Request Body:**
```json
{
	"recipientId" : "343018971",
	"category" : "Kista-designers"
}
```

**Response:**
```json
{ "message": "Recipient classification updated for recipientId: 343018971 with category: KISTA-DESIGNERS." }
```
### 2.2 Bad Request for non existent recipient
**Request Body:**
```json
{
    "recipientId" : "34301897111",
    "category" : "Kista-designers"
}
```

**Response:**
```json
{ "message": "Recipient classification not found for recipientId: 34301897111" }
```

## Technical Stack
- **Java 21**
- **Spring Boot**
- **Swagger** for API documentation
- **Records** for reducing boilerplate code
- **In-memory storage** (no database required)

## Setup & Run
1. Clone the repository
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Access API documentation at `http://localhost:8083/swagger-ui.html`

## Assumptions & Notes
- The service fetches transaction data and classifications from provided JSONs.
- Classification is based on heuristics and may not always be accurate.
- Users can manually override classifications using a POST call.
  - only one recipient can be updated at a time.

## Future Enhancements
- Integrate with a real transaction and classification service.
- Messaging queues would be an option for classification update.
- Implement a database for storing transaction data and classifications.
- Implement a caching mechanism for transaction data.
- Add authentication and authorization for API endpoints.
- Allow multiple recipients to be updated at once.

---
**Author:** Farrukh Mahmood

