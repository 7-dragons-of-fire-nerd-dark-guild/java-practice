#!/bin/bash

branch=qualification
dir=src/test/java/com/sevendragons/hashcode2016/qualification
sha=$(git rev-parse HEAD)

cd $(dirname "$0")

zip=submissions/$branch/source-$sha.zip
mkdir -p $(dirname "$zip")

git archive $branch -o $zip -- $dir

echo $zip
