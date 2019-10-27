
[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/claboran/opencoinfolium-webapi/blob/develop/LICENSE.md)
[![CircleCI](https://circleci.com/gh/claboran/opencoinfolium-webapi/tree/develop.svg?style=svg)](https://circleci.com/gh/claboran/opencoinfolium-webapi/tree/develop)
<br />
<p align="center">

  <h3 align="center">OpenCoinfolium Backend</h3>
  <p align="center">
    Open Source Backend Crypto Portfolio Tracker
    <br />
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)



<!-- ABOUT THE PROJECT -->
## About The Project

The project has a long history. Coinfolium was launched as a freely accessible Crypto Portfolio Tracker and was 
revised in 2019. The focus was on a usage without registration and completely anonymous. 
The sources were not under any open source license at that time. The source for the market data at this time was the
[CoinMarketCap](https://coinmarketcap.com/) API.

After the withdrawal of some project participants, the idea for a reorientation was born. The backend is to be newly
developed under an open source license. Still committed to the idea of free use.

A new provider for market data has been taken into account: [Alternative.me](https://alternative.me/crypto/api/).
Although no historical data is going to be provided, the free nature of the API seems to make it best suited.

Following goals are going to be addressed:
* Provide a stable platform for import and storage of market data
* Provide a REST API for the various clients (development will start soon based on the existing codebase, 
still open if this will be under Open Source too)
* Provide Authentication, Authorization, Registration and user basic data management in a safe manner 


### Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Kotlin](https://kotlinlang.org/)


<!-- GETTING STARTED -->
## Getting Started


### Prerequisites



### Installation

1. Clone the repo
```sh
git clone https://github.com/claboran/opencoinfolium-webapi.git
```
2. Install with maven
```sh
mvn clean install
```



<!-- USAGE EXAMPLES -->
## Usage




<!-- ROADMAP -->
## Roadmap




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the Apache 2.0 License. See `LICENSE.md` for more information.



<!-- CONTACT -->
## Contact


Project Link: [https://github.com/claboran/opencoinfolium-webapi](https://github.com/claboran/opencoinfolium-webapi)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Kotlin](https://kotlinlang.org/)
* [Maven](https://maven.apache.org/)
* [Alternative Me](https://alternative.me/crypto/api/)