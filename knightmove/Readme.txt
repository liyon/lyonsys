1  This solution is based on Java 8
2. To build the applicaion in windows:
   - start command line and cd to the knightmove directory
   - run "gradlew clean build"
   - run "java -cp build\classes\main com.lyonsys.knightmove.Main 10" (replace 10 with other number if testing other numbers)
3  There is also a test at src\test\java\com\lyonsys\knightmove\KnightMoveTest.java.
4. I have tested locally on number 10 and 16, but was not able to get result for number 32 due to long time taken. To test number 32, you probably need bump the
   heap size, e.g "java -Xmx4g -cp build\classes\main com.lyonsys.knightmove.Main 32"
