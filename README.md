# RentAWheel - Luxury Car Rental Application 🚗✨

RentAWheel is a premium, high-performance Android application built with **Jetpack Compose** that provides a seamless luxury car rental experience. It features an aesthetic UI, real-time catalog management, and an intuitive user journey from registration to booking.

---

## 📱 Key Features

### 1. 🔐 User Registration & Profile
*   **Secure Onboarding**: Aesthetic registration screen to create a personalized profile.
*   **User Profiles**: Manage your personal information (Name, Email, Phone) and session status.
*   **Session Management**: Quick logout functionality to switch accounts or secure your data.

### 2. 🏠 Home Screen (Car Catalog)
The heart of the application where users can discover their dream car.
*   **Premium UI**: Modern, card-based layout with high-quality car imagery from local drawables.
*   **Advanced Search**: Instantly filter cars by brand or model name.
*   **Category Chips**: Quick-access filtering for **Sedan, SUV, Electric, Sports, and Luxury** categories.
*   **Live Status**: Visual "BOOKED" overlays and disabled buttons for unavailable vehicles.

### 3. 📄 Car Details & Specs
A deep-dive into each vehicle's specifications.
*   **Hero Imagery**: Full-width, high-resolution car photos for an immersive experience.
*   **Technical Specifications**: Detailed view of Transmission (Auto/Manual), Fuel Type, and Seating Capacity.
*   **Interactive Booking**: Dedicated booking system with progress indicators and success notifications.
*   **Pricing**: Transparent daily rental rates prominently displayed in a floating action bar style.

### 4. 📅 My Journeys (My Bookings)
Manage your rentals and track your adventures.
*   **Active Booking List**: A clean, organized view of all your currently rented cars.
*   **Booking Cancellation**: Instantly cancel any booking to return the vehicle to the main catalog in real-time.
*   **Empty State Support**: Polished UI feedback when no active bookings are present.

### 5. ➕ Fleet Management (Add Car)
A dedicated interface for expanding the car catalog.
*   **Dynamic Entry**: Add new cars with custom Brand, Model, Price, Category, and Specs.
*   **Real-time Update**: New vehicles appear instantly in the catalog for all users to browse.

---

## 📸 Screenshots

| Registration | Home (Catalog) |
|:---:|:---:|
| <img src="screenshots/registration.png" width="280"> | <img src="screenshots/home.png" width="280"> |

| Car Details | My Journeys |
|:---:|:---:|
| <img src="screenshots/details.png" width="280"> | <img src="screenshots/bookings.png" width="280"> |

> **Note**: To view these images, create a `screenshots/` folder in your project root and save your captures as `registration.png`, `home.png`, `details.png`, and `bookings.png`.

---

## 🛠️ Tech Stack & Architecture
*   **UI**: Jetpack Compose (Material 3)
*   **Language**: Kotlin (100%)
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Navigation**: Type-safe Compose Navigation
*   **Reactive UI**: Kotlin StateFlow & SharedFlow
*   **Build System**: Gradle Kotlin DSL with Version Catalog (libs.versions.toml)

---

## 🚀 How to Run
1.  **Clone** this repository.
2.  **Open** in Android Studio (Ladybug or newer).
3.  **Sync** Gradle projects.
4.  **Run** the `:app` module on an emulator or physical device.
5.  **Register** your profile and start your premium journey!

---

Developed with ❤️ using Jetpack Compose.
