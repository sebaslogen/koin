#!/bin/sh

cd ./core
./install.sh
cd ..

cd ./android
./install.sh
cd ..

cd ./compose
./install.sh
cd ..

cd ./ktor
./install.sh
cd ..

cd ./plugins
./install.sh
cd ..

cd ./bom
./install.sh
cd ..
