#!/bin/bash

mvn clean package

rm App.tar.gz

tar -czf App.tar.gz App/target

cp App.tar.gz ~/dropbox/shared
