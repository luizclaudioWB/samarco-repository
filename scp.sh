#!/bin/bash

# Build do projeto
mvn clean install -Dmaven.test.skip=true

# Empacota a aplicação
cd target
zip -r samarco.zip quarkus-app

# Envia para o servidor miadev
scp samarco.zip mia:/tmp
