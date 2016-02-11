#!/bin/bash

branch=qualification
dir=src/test/java/com/sevendragons/hashcode2016/qualification
sha=$(git rev-parse HEAD)
sha=${sha:0:8}
tag=$sha-$(date +%Y%m%d-%H%M)

cd $(dirname "$0")

zip=submissions/$branch/source-$tag.zip
mkdir -p $(dirname "$zip")

git archive $branch -o $zip -- $dir

echo $zip
