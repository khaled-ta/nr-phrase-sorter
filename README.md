# NEW RELIC Assignment

## Requirements
1. Java 11
2. Gradle 7.5.1

## NOTES
```text
* purposely did not use any frameworks
* Future use of DI frameworks from Spring or Micronaut 
```

### commands
```shell

# build app locally with tests
./gradlew clean build -x test

# build app excluding tests
./gradlew clean build -x test
# OR
./gradlew clean shadowJar

# run tests
./gradlew test

# run and build app locally using samples
./scripts/buid-and-run-samples.sh

# to run own files
java -jar build/libs/nr-phrase-sorter-all.jar <your-files-here>
```

### Dockerization
```shell
# build docker img
docker build -t nr-phrase-sorter:latest .

# when running docker image, must mount files that we will need to  
docker run -it --mount type=bind,src=<absolute-path>,dst=<absolute-path> image-id

```

### unfinished todos: 
write alias scripts that would accept user source directory or file
write a lot more tests :)
add more file validations
test for additional punctuation and non-alphanumeric characters 

### interesting bits
#### used multi-threads to process files concurrently
#### used ConcurrentHashMap#compute to ensure thread safety
#### return amount of time it takes to process each file
#### fault-tolerant: if processing of 1 file fails, no affect on others
#### checks for empty files and incorrect filenames
