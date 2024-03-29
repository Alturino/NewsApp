# NewsApp

NewsApp is an application that show you news from the [NewsAPI](https://newsapi.org/docs/get-started).

## Table of Contents

- [App Preview](#App-Preview)
- [Getting Started](#Getting-Started)
  - [Prerequisites](#Prerequisites)
  - [Installation](#Installation)
  - [Architecture](#Architecture)
- [Contributing](#Contributing)
- [Bug Reporting](#Bug-Reporting)
- [Contact](#Contact)
- [License](#License)

## App Preview
https://github.com/onirutlA/NewsApp/assets/59439682/282a6584-b268-4770-a46e-2cb5e50080db

## Getting Started

Follow these steps to get the app up and running on your Android device or emulator.

### Prerequisites

- Android Studio: [Download](https://developer.android.com/studio) and install the latest version of
  Android Studio.
- Android Device or Emulator: Ensure you have a physical Android device connected or an emulator set
  up in Android Studio.

### Installation

1. **Clone the repository:** Clone this repository to your local machine using `git`.

   ```bash
   git clone https://github.com/onirutlA/NewsApp.git
   cd NewsApp
   ```

2. **Open in Android Studio:** Launch Android Studio and select "Open an existing Android Studio
   project." Navigate to the folder where you cloned the repository and open it.

3. **Build and Run:** Wait for the project to build, and then click the "Run" button in Android
   Studio. Select your connected Android device or choose an emulator to run the app.

## Library Used

- Kotlin based, Coroutines + Flow for asynchronous.
- Hilt for dependency injection.
- Jetpack
    - Compose - UI toolkit.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - Navigation Component - Navigate to another fragment
    - Paging3 - Pagination
- Architecture
    - MVI Architecture
    - Repository pattern
- [Retrofit2](https://github.com/square/retrofit) & OkHttp3 - construct the REST APIs.
- [kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization) - A modern JSON library
  for Kotlin and Java.
- [Coil](https://github.com/coil-kt/coil) - Image Loading Library.
- Material-Components - Material design components for building ripple animation, and CardView.

### Architecture

NewsApp architecture is based on Google Recommendation MVI and the Repository pattern.
![Architecture](./docs/assets/mvi.png)

## Contributing

Your contributions to this project are highly appreciated! To contribute, follow these steps:

1. Fork the repository and create a new branch.
2. Make your changes and ensure the code is well-tested.
3. Commit your changes and push them to your fork.
4. Submit a pull request, explaining the changes you've made.

## Bug Reporting

If you encounter any bugs, issues, or have suggestions for improvements, please open an issue on the
repository. Provide as much detail as possible to help us understand and resolve the problem
quickly.

## Contact

*Provide information about how users or other developers can get in touch with you. This can include
your email, social media profiles, or links to your personal website.*

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute
the app's code following the terms of this license.
