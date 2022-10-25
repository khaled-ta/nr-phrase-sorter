#!/bin/bash

./gradlew clean shadowJar
java -jar build/libs/nr-phrase-sorter-all.jar files/moby.txt files/brothers-karamazov.txt files/two-words.txt README.md adsf files/empty.txt