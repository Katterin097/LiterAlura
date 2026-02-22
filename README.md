# 💱 Convertidor de Monedas

### 💱 🔎 Funcionamiento General
Este proyecto es un convertidor de monedas que funciona desde la consola. El programa muestra un menú con varias opciones de conversión entre dólar, peso argentino, real brasileño y peso colombiano.

### 🖥️ 📋 Menú de Opciones
El programa muestra un menú con varias opciones de conversión:

<img src="./img-readme/Menu.png" alt="Vista previa" width="600">

---
### ✍️ 💵 Ingreso de Valores
El usuario debe seleccionar una opción e ingresar el valor que desea convertir. Luego, el sistema realiza el cálculo y muestra:

- La moneda base seleccionada.
- La tasa de cambio utilizada.
- El valor final convertido.

<img src="./img-readme/Ejecucion.png" alt="Vista previa" width="600">

---

### 🔁 ⏹️ Ejecución Continua

El programa se ejecuta de manera continua, permitiendo realizar varias conversiones, y solo finaliza cuando el usuario selecciona la opción 7 (Salir).

<img src="./img-readme/Salir.png" alt="Vista previa" width="600">

---

## 📂 Estructura del Proyecto

```plaintext
Convertidor Moneda/
│
├── .idea/                 → Archivos de configuración del entorno (IDE).
│
├── img-readme/            → Imágenes utilizadas en el README.
│   ├── Ejecucion.png
│   └── Menu.png
│
├── out/                   → Archivos compilados automáticamente.
│
├── src/                   → Código fuente del proyecto.
│   ├── Main.java          → Contiene el menú principal, la lógica de interacción
│   │                         con el usuario y la consulta a la API.
│   │
│   ├── Moneda.java        → Clase que representa una moneda (id y código).
│   │
│   └── ExchangeResponse.java
│                           → Clase utilizada para mapear la respuesta
│                             recibida desde la API de tasas de cambio.
│
├── .gitignore             → Archivos que no se incluyen en el repositorio.
│
├── Convertidor Moneda.iml → Archivo de configuración del proyecto.
│
└── README.md              → Documento de descripción del proyecto.
````
