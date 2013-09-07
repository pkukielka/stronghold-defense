#!/bin/bash

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find . -name "*_move.png" -type f -printf "convert +repage %p -crop 128x128 -resize 96x96 -quality 0 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../lib/gdx.jar:../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../src/main/resources/data/textures/enemies enemies_move

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find . -name "*_die.png" -type f -printf "convert +repage %p -crop 128x128 -resize 96x96 -quality 0 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../lib/gdx.jar:../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../src/main/resources/data/textures/enemies enemies_die

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find ./effects/spells -name "*.png" -type f -printf "convert +repage %p -crop 192x192 -resize 96x96 -quality 0 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../lib/gdx.jar:../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../src/main/resources/data/textures/spells spells

rm -Rf ./out
mkdir -p ./out
cp pack.json ./out
find ./effects/auras -name "*.png" -type f -printf "convert +repage %p -crop 192x192 -resize 96x96 -quality 0 ./out/%f\n" | bash
find ./effects/glyphs -name "*.png" -type f -printf "convert +repage %p -crop 192x192 -resize 96x96 -quality 0 ./out/%f\n" | bash
find ./effects/explosions -name "*.png" -type f -printf "convert +repage %p -crop 192x192 -resize 96x96 -quality 0 ./out/%f\n" | bash
rename -f 's/-/_/g' ./out/*-*.png
java -classpath "../../lib/gdx.jar:../../lib/gdx-tools.jar" com.badlogic.gdx.tools.imagepacker.TexturePacker2 ./out ../../src/main/resources/data/textures/spells misc

rm -Rf ./out