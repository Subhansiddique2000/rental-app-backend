# 🏠 RentalHub Backend API

This is the backend API for **RentalHub**, a property management platform for landlords and tenants. It is built using **Spring Boot 3.5.4** and **Kotlin**, and provides secure endpoints for managing properties, tenants, leases, and rent payments.

---

## ✅ MVP Feature Breakdown

### 🔐 Authentication

- Landlord signup & login
- Tenant login
- JWT token generation & validation

---

### 🏘 Property & Unit Management

- Landlord creates/updates properties
- Each property can have multiple units
- View list of properties and units

---

### 👥 Tenant & Lease Management

- Landlord can invite/add tenants
- Assign tenant to unit via a lease
- Lease has rent amount, dates, etc.
- Tenant can view their lease info

---

### 💳 Rent Payments

- Landlord sees payment history for units
- Tenant makes rent payment
- View rent history & rent due

---

## 🌐 API Endpoint Design

### 🔐 AuthController

| Method | Endpoint       | Role     | Description             |
|--------|----------------|----------|-------------------------|
| POST   | `/auth/signup` | Landlord | Register a new landlord |
| POST   | `/auth/login`  | All      | Login & get JWT         |

---

### 👤 LandlordController

| Method | Endpoint       | Role     | Description               |
|--------|----------------|----------|---------------------------|
| GET    | `/landlord/me` | Landlord | Get current landlord info |

---

### 🏘 PropertyController

| Method | Endpoint           | Role     | Description              |
|--------|--------------------|----------|--------------------------|
| GET    | `/properties`      | Landlord | List landlord properties |
| POST   | `/properties`      | Landlord | Add a new property       |
| PUT    | `/properties/{id}` | Landlord | Edit property            |
| DELETE | `/properties/{id}` | Landlord | Delete property          |

---

### 🏢 UnitController

| Method | Endpoint                 | Role     | Description              |
|--------|--------------------------|----------|--------------------------|
| GET    | `/properties/{id}/units` | Landlord | List units in a property |
| POST   | `/properties/{id}/units` | Landlord | Add unit to a property   |

---

### 👥 TenantController

| Method | Endpoint        | Role     | Description                    |
|--------|-----------------|----------|--------------------------------|
| GET    | `/tenants`      | Landlord | List all tenants (global view) |
| GET    | `/tenants/{id}` | Landlord | View tenant details            |

---

### 📃 LeaseController

| Method | Endpoint     | Role     | Description                      |
|--------|--------------|----------|----------------------------------|
| GET    | `/leases`    | Landlord | List all leases                  |
| POST   | `/leases`    | Landlord | Create a lease for a unit+tenant |
| GET    | `/leases/my` | Tenant   | Tenant’s lease info              |

---

### 💸 PaymentController

| Method | Endpoint       | Role     | Description              |
|--------|----------------|----------|--------------------------|
| GET    | `/payments`    | Landlord | All rent payment records |
| GET    | `/payments/my` | Tenant   | Tenant’s payment history |
| POST   | `/payments`    | Tenant   | Submit a payment         |

---

## 🛠️ Project Roadmap

### ✅ Step 1: Entity Creation

We will define the following entities as Kotlin data classes using JPA annotations:

- `Landlord`
- `Tenant`
- `Property`
- `Unit`
- `Lease`
- `Payment`

---

### 🔐 Step 2: JWT Auth Setup

- Implement Spring Security config
- JWT token filter
- Login endpoint
- Custom `UserDetailsService`

---

### 🚀 Step 3: Controller Scaffolding

Once auth and entities are in place, we will scaffold:

- `PropertyController`
- `UnitController`
- `LeaseController`
- `PaymentController`

---

## 📦 Tech Stack

- **Kotlin**
- **Spring Boot 3.5.4**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT Authentication**
