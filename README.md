# Assyrian-Words-Android

An Android app that allows easy and simple access to Assyrian (Surit/Surayt) wordlists.

This project is currently in an early phase.

## Architecture

Uses the MVVM (Model-View-ViewModel) pattern. So the goal is to use Fragments within a single Activity as often as possible – in order to stay within the same lifecycle. This is in favor of sharing data resources across multiple screens – by using the same ViewModel instance.

## Backend

The backend is designed to update the frontend as data is updated; however, no mechanism has yet been implemented to notify the app when datasets change on remote.

Database: Room <BR>
Network: Retrofit 2, Picasso (not yet used) <BR>
Threading: Kotlin Coroutines <BR>
DI: Dagger 2 (not yet used) <BR>
Testing: JUnit, Espresso <BR>
Debugging: Stetho <BR>

## Frontend

The current UI is not ideal and it has been developed only for testing (during the backend development). Any additions/changes to the UI should take advantage of the backend's ability to provide updated data instantly.

## Planned Enhancements

- An attractive UI/UX.
- Word search.
- Word filters.
- Switching between Surit (Eastern dialect) and Surayt (Western dialect).
- Marking words as favorites (or rather saving them into a "Saved" list).

## Contributions

All are welcome to contribute to this open source effort.
