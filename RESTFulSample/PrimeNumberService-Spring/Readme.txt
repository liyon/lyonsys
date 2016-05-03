1. This implementation require Java 8.
2. To start the service, run
     mvn clean package
     mvn spring-boot:run
3. To test the service, start a browser and type the following uri:

   - To test lastPrimeNumber use uri:
     http://localhost:8080/lastPrimeNumber/<number>
     example:  http://localhost:8080/lastPrimeNumber/13
   
    - To test isPrime method use uri:
     http://localhost:8080/isPrime/<number>
     example:  http://localhost:8080/isPrime/5
