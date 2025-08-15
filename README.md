# 🧠 Brain Trainer – Aplicación de entrenamiento cognitivo
![Brain Trainer Icon](app/src/main/res/drawable/app_icon.png)

## 📌 Descripción
Brain Trainer es una aplicación Android cuyo objetivo es mejorar las **habilidades cognitivas** como la memoria, la atención, la velocidad de procesamiento y la flexibilidad mental a través de **minijuegos** divertidos y accesibles.  
A diferencia de muchas apps del sector, Brain Trainer **no bloquea funcionalidades esenciales** tras un muro de pago: el modelo de negocio es no intrusivo y accesible para todos.

---

## 🎯 Características principales
- 🎮 **Catálogo de juegos cognitivos** clasificados por categorías.
- 📊 **Seguimiento de progreso y estadísticas** (generales y por juego).
- 🔐 **Inicio de sesión seguro con Google** mediante *Credential Manager*.
- 🎨 **Interfaz moderna y responsiva** creada con Jetpack Compose.
- ☁️ **Datos en la nube** con Firebase Firestore.
- 🏗️ **Arquitectura escalable y mantenible** basada en la *Clean Architecture*.
- 📱 **Acceso completo** a todas las funcionalidades desde el principio.

---

## 🛠️ Tecnologías y herramientas
- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Arquitectura:** Clean Architecture
- **Inyección de dependencias:** [Hilt](https://dagger.dev/hilt/)
- **Backend as a Service:** [Firebase](https://firebase.google.com/)  
  - Authentication  
  - Firestore Database  
  - Crashlytics
- **Integración Google:** Credential Manager
- **IDE:** Android Studio
- **Control de versiones:** GitHub

---

## 📐 Arquitectura del proyecto
Brain Trainer sigue los principios de **Clean Architecture** para garantizar mantenibilidad y escalabilidad:

- **Capa de presentación (Screens + ViewModels + UI States):** Responsable de la interacción con el usuario, mostrando la información y gestionando las acciones del usuario.
- **Capa de datos (Repositorios + Fuentes de datos + Modelos de datos):** Se encarga de la gestión de los datos de la aplicación, incluyendo el acceso a las fuentes de datos y la abstracción de la lógica de acceso a datos. Se comunica con Firebase.

---

## 📷 Capturas de pantalla
| Inicio de sesión |
| :--------------: |
| <img width="397" height="698" alt="image" src="https://github.com/user-attachments/assets/a063b6c7-823e-45cb-80f4-a4c501284375" /> <img width="400" height="699" alt="image" src="https://github.com/user-attachments/assets/e8f9e576-2643-414e-8eac-8c6748c43bfe" /> |

| Lista de juegos |
| :-------------: |
| <img width="402" height="708" alt="image" src="https://github.com/user-attachments/assets/3b12e429-762f-43e0-a66c-cc0513f69acc" /> |

| Instrucciones |
| :-----------: |
| <img width="400" height="705" alt="image" src="https://github.com/user-attachments/assets/bd8ed2de-7374-42d6-a8f7-6011c9553fec" /> |

| Ejemplo de juego |
| :--------------: |
| <img width="400" height="702" alt="image" src="https://github.com/user-attachments/assets/742010b9-21d2-4615-87e1-0402a89ba18c" /> |

| Fin de partida |
| :------------: |
| <img width="401" height="710" alt="image" src="https://github.com/user-attachments/assets/fc6f4da3-22f5-4e86-b3dd-80a9a2f7ac54" />  <img width="404" height="709" alt="image" src="https://github.com/user-attachments/assets/48f2599d-a13a-4c1f-a1e6-fce848fc2593" /> |

| Estadísticas |
| :----------: |
| <img width="401" height="702" alt="image" src="https://github.com/user-attachments/assets/c5acaac9-bc42-4ce6-8b39-8754e38547f3" /> <img width="404" height="710" alt="image" src="https://github.com/user-attachments/assets/10e6c4a8-c13b-435f-9ac4-00f2ce12c9e6" /> |

| Configuración |
| :-----------: |
| <img width="399" height="705" alt="image" src="https://github.com/user-attachments/assets/995eff03-03d9-4108-84bb-06cb0f664d4f" /> |
