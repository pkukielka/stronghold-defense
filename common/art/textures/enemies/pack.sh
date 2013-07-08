#!/bin/bash

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find . -name "*_move.png" -type f -printf "convert +repage %p -crop 128x128 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../../lib/gdx.jar:../../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../../src/main/resources/data/textures/enemies enemies_move

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find . -name "*_die.png" -type f -printf "convert +repage %p -crop 128x128 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../../lib/gdx.jar:../../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../../src/main/resources/data/textures/enemies enemies_die

rm -Rf ./out