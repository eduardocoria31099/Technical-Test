# Technical Test ![Kotlin](https://img.shields.io/badge/Kotlin-4c8eb8?style=flat&logo=kotlin&logoColor=white)

![Gradle](https://img.shields.io/badge/Gradle-8.13-blue?logo=gradle&logoColor=white)
![JDK](https://img.shields.io/badge/JDK-21.0.6-green?logo=java&logoColor=white)
![Version](https://img.shields.io/badge/Version-1.0-blue.svg)
![License: Proprietary](https://img.shields.io/badge/License-Proprietary-red.svg)

# 🧪 Technical Test Android App

Una aplicación Android construida siguiendo los principios de **Arquitectura Limpia**, diseñada para demostrar buenas prácticas de desarrollo moderno en Android.

## 🚀 Características

- 🔁 **Retrofit + Paging 3**: Consumo de API paginada (Rick and Morty API).
- 💾 **Room**: Persistencia local de datos con base de datos SQLite.
- ⚙️ **Coroutines**: Manejo de asincronía con Kotlin Coroutines.
- 🔐 **Autenticación Biométrica**: Fingerprint / Face ID usando BiometricPrompt.
- 🧱 **Arquitectura Limpia**: Separación por capas (`data`, `domain`, `ui`).
- 🧠 **Hilt**: Inyección de dependencias con Hilt.
- 🛠️ **ViewModel + StateFlow**: Gestión reactiva del estado en la UI.

✅ **Summary Checklist:**

- [x] Scroll infinito (paginación).
- [x] Filtros por estado, especie y nombre.
- [x] Barra de búsqueda.
- [x] Mostrar nombre, especie, estado, y foto.
- [x] Pull to refresh.
- [ ] Mostrar indicadores de red / estado vacío / error.
- **--------------------------------**
- [x] Imagen completa, género, especie, estado, ubicación.
- [ ] Lista de episodios donde aparece (nombre y número).
- [x] Marcar como favorito.
- [ ] Marcar episodios como “vistos”.
- [x] Botón de “Ver en mapa” → Muestra su última ubicación (simulada) en Google
  Maps o MapKit.
- **--------------------------------**
- [x] Solo personajes guardados como favoritos.
- **--------------------------------**
- [x] Implementar acceso a favoritos mediante autenticación biométrica (Face ID /
  Huella).

