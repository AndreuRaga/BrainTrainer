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
| Inicio de sesión | Lista de juegos | Estadísticas |
|------------------|-----------------|--------------|
| ![](docs/login.png) | ![](docs/games.png) | ![](docs/stats.png) |

| Instrucciones | Fin de partida | Configuración |
|---------------|---------------|---------------|
| ![](docs/instructions.png) | ![](docs/gameover.png) | ![](docs/settings.png) |

*(Las imágenes son ejemplos; sustituye por capturas reales del proyecto)*

---

## 📊 Resultados y validación
- ✅ **Pruebas de usabilidad** con 10 participantes, evaluando facilidad de uso, diseño y funcionalidad.
- ✅ Cumplimiento de **estándares de diseño Android**.
- ✅ **Rendimiento fluido** (60 FPS) y estabilidad sin ANR.
- ✅ Gestión segura de datos con cifrado y mínima recolección de permisos.

---

## 🚀 Futuras mejoras
- Sistema de logros y desafíos.
- Notificaciones y recordatorios personalizados.
- Tema oscuro y soporte para dispositivos plegables.
- Expansión del catálogo de juegos.

---

## 📄 Licencia
Este proyecto está licenciado bajo la licencia [MIT](LICENSE).

---

## 👤 Autor
**Andreu [Tu Apellido]**  
Graduado en Ingeniería Informática – Especialidad en Ingeniería del Software  
[LinkedIn](https://www.linkedin.com/in/tu-perfil) | [GitHub](https://github.com/tu-usuario)

---
