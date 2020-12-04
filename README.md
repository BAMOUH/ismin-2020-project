# ismin-2020-project

## STATION FUNDER

Android Application + Web Server Development

![TypeScript](https://badges.frapsoft.com/typescript/love/typescript.svg?v=101)

by EL BACHIR BAMOUH and FAHD LYOUSFI

Engineering Students of Mines Saint Etienne, ISMIN 2020 - Major: Computer Science.

[![Mines Saint Etienne](./logo.png)](https://www.mines-stetienne.fr/)

---

### Project

This application allows you to list and display all Velib Stations and availibility of Ebike or Machanical bike in Vélib' Métropole stations.

Database of Stations from [https://data.opendatasoft.com/](https://data.opendatasoft.com/explore/dataset/velib-disponibilite-en-temps-reel%40parisdata/information/?disjunctive.name&disjunctive.is_installed&disjunctive.is_renting&disjunctive.is_returning&disjunctive.nom_arrondissement_communes&dataChart=eyJxdWVyaWVzIjpbeyJjaGFydHMiOlt7InR5cGUiOiJ0cmVlbWFwIiwiZnVuYyI6IlNVTSIsInlBeGlzIjoiY2FwYWNpdHkiLCJzY2llbnRpZmljRGlzcGxheSI6dHJ1ZSwiY29sb3IiOiJyYW5nZS1EYXJrMiJ9XSwieEF4aXMiOiJuYW1lIiwibWF4cG9pbnRzIjo1MCwidGltZXNjYWxlIjoiIiwic29ydCI6IiIsInNlcmllc0JyZWFrZG93biI6IiIsInNlcmllc0JyZWFrZG93blRpbWVzY2FsZSI6IiIsImNvbmZpZyI6eyJkYXRhc2V0IjoidmVsaWItZGlzcG9uaWJpbGl0ZS1lbi10ZW1wcy1yZWVsQHBhcmlzZGF0YSIsIm9wdGlvbnMiOnsiZGlzanVuY3RpdmUubmFtZSI6dHJ1ZSwiZGlzanVuY3RpdmUuaXNfaW5zdGFsbGVkIjp0cnVlLCJkaXNqdW5jdGl2ZS5pc19yZW50aW5nIjp0cnVlLCJkaXNqdW5jdGl2ZS5pc19yZXR1cm5pbmciOnRydWUsImRpc2p1bmN0aXZlLm5vbV9hcnJvbmRpc3NlbWVudF9jb21tdW5lcyI6dHJ1ZX19fV0sImRpc3BsYXlMZWdlbmQiOnRydWUsImFsaWduTW9udGgiOnRydWUsInRpbWVzY2FsZSI6IiJ9), downloaded on a [clevercloud server](http://app-5b336f60-7eb3-47be-aad4-06682834c6a6.cleverapps.io).

### Features

# - Vélib' Métropole stations ✔️

- Showing all stations ✔️
- Check detailed station information ✔️
- Refresh data base from server ✔️
- Adding and removing some stations to/from favorites ✔️
- Storage of data coming from API in local SQLITE database ✔️
- Storage of favorite stations in the same database ✔️

### Get started !

- Run the server "Bookshelf-App-BER" in clever-cloud, if it isn't already runing. or you can deploy the API (look directory /api) in your own server.
- Start Android Studio after downloading the project
- Select `Open an existing Android Studio project` and pick this directory

### Technical information:

- The App is connected to a remote REST API server in clever-cloud plateforme. Data are stored in a MongoDB database.
- The App contains two fragments (Home and database Favorites).
- The App contains two activities (main activity and detail activity)
- It is possible to add/remove a station as a favorite. The information is stored is the SQLITE database of the Android OS.
- we do a pagination with Endless RecyclerView Scroll.
- We show the local data before getting the ones from the API if we got any stations stored, to enhance UX.
- The API have got a cron to periodically grab data from the Velib's API and refresh the ones in our database.
- The app contains a refresh button to manually refresh the data and get real time data when needed by the user.

---

### License

MIT
