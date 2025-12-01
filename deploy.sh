#!/bin/bash

# Build do projeto
mvn clean install -Dmaven.test.skip=true

# Empacota a aplicação
cd target
zip -r app.zip quarkus-app

# Envia para o servidor miadev
scp app.zip miadev:/tmp
