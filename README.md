# Flight Delay Calculator

An API for calculating the probability of flight delays based on user-provided airport and time information. By leveraging a combination of historical airport statistics, real-time weather data sourced from the [OpenMeteo API](#Acknowledgements), and current traffic levels at European airports, my calculator generates accurate and insightful delay probabilities for specific times and locations.

I use this API in my web application [DelayFlight.pl](https://www.delayflight.pl)

## Features
  ### Delay probability calculator
  You can input the specific date, time, and airport for your flight, and algorithms analyze historical airport statistics and current weather conditions to generate a probability of delay for your chosen time and location. This enables you to make more informed decisions about your travel plans.
  
  ### Airport statistic data
  You can effortlessly check airport statistics and obtain the most important information needed to ensure punctuality and enables you to make more informed decisions about choice of airport.
  
  ### Airport weather forecast
  You can check the weather at your chosen airport and receive forecasted conditions to determine if they are within the limits for a timely arrival, on chosen date or for the upcoming five days.

## Requests
  ### Weather Requests
  * Get report of weather at precision hour `POST/api/v1/weather/hour`
  
    #### Example of body 
    ```
      {
        "airportIdent": "IcaoCode",
        "date": "yyyy-MM-dd'T'HH:mm",
        "phase": "ARRIVAL/DEPARTURE"
      }
    ```
* Get report of weather at upcoming days on 6 hour periods `POST/api/v1/weather/periods?days={numberOfDays}`

  #### Example of body
    ```
      {
        "airportIdent": "IcaoCode",
        "phase": "ARRIVAL/DEPARTURE"
      }
    ```

  #### Response example

  ```
    "CROSSWIND": {
        "id": "CROSSWIND",
        "title": "Crosswind",
        "value": 3,
        "unit_name": "knots",
        "unit_symbol": "kt",
        "influence_on_delay": "LOW"
    }
  ```

  ### Statistics Request
  * Get airport statistics `GET/api/v1/statistics/{airportIcaoCode}`

  #### Response example
  
  ```
   "AVERAGE_MONTHLY_TRAFFIC": {
        "id": "AVERAGE_MONTHLY_TRAFFIC",
        "name": "Average monthly traffic",
        "unit_symbol": "num",
        "flight_phase": "DEPARTURE_AND_ARRIVAL",
        "factor_type": "AVERAGE",
        "status": "COMPLETE",
        "value": 35361.0
    }
  ```

## How it works
  ### Weather feature
  The calculator retrieves weather forecast for a specific hour or upcoming days and compares each factor against the established limits for each factor, including crosswind, tailwind, visibility, rainfall, and cloud height. The limits are different for takeoff and landing, so I need information about the flight phase when determining the factors. In addition to the limit, each factor is also compared to the Instrument Landing System (ILS) category set for that airport, considering the weather conditions, time of day, and the available ILS category at the airport.

  I used three formulas in calculations:
  * crosswind `windSpeed * sin(runwayHeadingDegrees - windDirection)`
  * tailwind `windSpeed * cos(runwayHeadingDegrees - windDirection)`
  * cloud base `(temperature - dew point) / 10 × 1247 + airportElevation`

  I get the ingredients for these calculations and the rest of the factors from the [weather API](#Acknowledgements) and from [here](#The-Data)


  The final decision on whether to take off, land, or go around under the given weather conditions is up to the captain, which means that it is impossible to clearly state what delay will occur. As a result, the delay is a percentage and is based on how many limits have been exceeded.

  The limits that have been set for each phase are based on the limits of the [Boeing 737](#Bibliography) and the [Airbus 320](#Bibliography) as a model of a passenger aircraft.
  Additionally, based on the document [Weather phenomena affecting air traffic management operations](#Bibliography) I take into account the ways in which air traffic control adds additional time in the take-off or landing [phases](#Flight-Phases) in unfavorable weather.


  ### Statistic feature
  #### The Data
  Static data on airports, runways and regions come from the [OurAirports](#Acknowledgements), which I convert and filter the shared CSV files to JSON format using a Python script written by me to optimize the database to only the largest European airports, excluding private, military airports, etc.<br/><br/>
  The statistics that I use come from the [Eurocontrol](#Acknowledgements) website. I convert and filter the shared CSV files to JSON format using a Python script written by me.
  
  #### General
   I use many statistics on delays in specific takeoff and landing [phases](#Flight-Phases). I calculate the average monthly delays in each phase, the most delayed month, and other more precise values, such as the most common cause of delays and the average times of the most common delays. <br /> I create such factors, which then serve me to calculate the probability of a delay that constantly occurs at a given airport and the probability of a longer (over 15 minutes) delays. I calculate the average and probability which I display to the user, as the probability of delay and as the expected time if a longer delay occurs.

  ## Flight Phases:
  * **Taxi-out:**
    * Pre-departure queue
    * Off-blocks
    * Stand
  * **Departure:**
    * take-off queue
    * Terminal airspace exit
  * **Enroute**
  * **Arrival:**
    * Arrival queue
    * Terminal airspace entry
    * Landing
    * On-blocks
  <br/> <br/>
  #### Details
  [ATC Pre-departure Delay](#Bibliography) <br /> 2.2 Concept of runway optimisation [Weather phenomena affecting air traffic management operations](#Bibliography) 

## Tech Stack
* **Backend**
  * Java
  * Spring Boot
* **Database**
  * Hibernate
  * MySQL
* **Development**
  * Maven
  * Lombok
* **Data**
  * JSON
  * CSV
* **Serialization**
  * Jackson
* **Testing**
  * Mockito
  * JUnit
* **Deployment**
  * Heroku.com

## Acknowledgements
I would like to express my sincere appreciation to 
* [Eurocontrol](https://www.eurocontrol.int) - for providing statistical data
* [OurAirports](https://www.ourairports.com) - for providing static airports data
* [OpenMeteo](www.open-meteo.com) - for providing weather api

## Bibliography
* [Boeing 737 Limitations](http://www.b737.org.uk/limitations.htm)
* [Airbus 320 Limitations](https://wiki.ivao.aero/en/home/training/mediawiki/pending/Airbus_320_Limitations)
* [Weather phenomena affecting air traffic management operations](https://ansperformance.eu/library/ATXIT_indicator_documentation_mar23.pdf)
* [ATC Pre-departure Delay](https://ansperformance.eu/definition/atc-pre-departure-delay/)

## Contact
Paweł Gawrych - [LinkedIn](www.linkedin.com/in/Gawrych)

Email - pawelgawrych203@gmail.com

Web Application - [DelayFlight.pl](https://www.delayflight.pl/)
