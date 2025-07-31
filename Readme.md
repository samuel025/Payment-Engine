# Payment Engine API

A comprehensive payment processing and management system built with Spring Boot, featuring JWT authentication, role-based access control, and payment transaction processing.

## 🚀 Features

- **Multi-user Authentication**: Support for Merchants, Customers, and Admin roles
- **JWT Security**: Secure token-based authentication
- **Payment Processing**: Create and manage payment requests
- **Transaction Management**: Process transactions with success/failure simulation
- **Wallet System**: Merchant wallet management with automatic crediting
- **RESTful API**: Clean REST endpoints with Swagger documentation
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **Role-based Access Control**: Different permissions for different user types

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.3, Java 21
- **Security**: Spring Security 6.5.1, JWT (jsonwebtoken 0.12.6)
- **Database**: PostgreSQL, Spring Data JPA
- **Documentation**: Swagger/OpenAPI 3
- **Mapping**: ModelMapper 3.0.0
- **Build Tool**: Maven
- **Containerization**: Docker Compose




## 🔐 Authentication & Authorization

### User Roles
- **ADMIN**: Full system access
- **MERCHANT**: Can manage payments and view wallet
- **CUSTOMER**: Can create payment requests and view transactions

### JWT Authentication
Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## 🌐 API Endpoints

### Authentication
```http
POST /register          # Register new user (merchant/customer)
POST /login             # User login
```

### Payment Management
```http
POST /payment           # Create payment request
GET /payment            # Get user's payment requests
GET /payment/{reference} # Get payment by reference
GET /payment/transactions/{id} # Get transactions for payment
```

### Transaction Processing
```http
POST /transaction/{reference} # Process transaction
GET /transaction/{reference}  # Get transaction details
GET /transactions            # Get all transactions (paginated)
```

### Merchant Operations
```http
POST /merchant              # Create merchant
GET /merchant/wallet/{id}   # Get wallet balance
```

## 💳 Usage Examples

### 1. Register a Merchant
```json
POST /register
{
  "email": "merchant@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "MERCHANT",
  "businessName": "John's Store",
  "callbackUrl": "https://example.com/callback"
}
```

### 2. Register a Customer
```json
POST /register
{
  "email": "customer@example.com",
  "password": "password123",
  "firstName": "Jane",
  "lastName": "Smith",
  "phoneNumber": "+1234567890",
  "role": "CUSTOMER",
  "address": "123 Main St, City, Country"
}
```

### 3. Login
```json
POST /login
{
  "email": "merchant@example.com",
  "password": "password123"
}
```

### 4. Create Payment Request
```json
POST /payment
{
  "merchant": {
    "id": 1
  },
  "amount": 100.00,
  "description": "Product purchase",
  "customerEmail": "customer@example.com",
  "customerName": "Jane Smith",
  "customerPhone": "+1234567890"
}
```

### 5. Process Transaction
```json
POST /transaction/{payment-reference}
{
  "channel": "CARD"
}
```

## 🏗️ Project Structure

```
src/main/java/com/samwellstore/paymentengine/
├── config/                 # Configuration classes
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   └── mapperConfig.java
├── controllers/            # REST controllers
│   ├── UserController.java
│   ├── PaymentRequestController.java
│   ├── TransactionController.java
│   └── MerchantController.java
├── dto/                    # Data Transfer Objects
├── entities/               # JPA entities
│   ├── BaseUser.java
│   ├── Customer.java
│   ├── Merchant.java
│   ├── PaymentRequest.java
│   └── Transaction.java
├── enums/                  # Enumerations
├── repositories/           # Data repositories
├── security/               # Security components
│   ├── JWTService.java
│   ├── JWTFilter.java
│   └── UserPrincipal.java
├── services/               # Business logic
└── utils/mapper/           # Object mapping utilities
```

## 🗄️ Database Schema

### Key Entities

**BaseUser** (Abstract)
- Common fields for all users
- Email, password, names, phone, timestamps

**Merchant** extends BaseUser
- Business name, callback URL
- Wallet balance
- Verification status

**Customer** extends BaseUser
- Address, date of birth

**PaymentRequest**
- Links merchant and customer
- Amount, reference, status
- Customer details for anonymous payments

**Transaction**
- Links to payment request
- Channel, reference, status
- Processing timestamp

## 🔄 Payment Flow

1. **Payment Creation**: Customer/Anonymous user creates payment request
2. **Payment Processing**: Transaction initiated with payment reference
3. **Status Update**: Payment status updated based on transaction result
4. **Wallet Credit**: Merchant wallet credited on successful payment
5. **Failure Handling**: Payment marked as failed after 3 failed attempts









