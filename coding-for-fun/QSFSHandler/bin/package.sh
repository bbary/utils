#!/usr/bin/env bash

# clean and prepare
echo "### cleaning ###"
working_dir="$( cd "$(dirname $0)" ; pwd -P )"/..
rm -rf $working_dir/classes/*
rm -rf $working_dir/target/*

# build
echo "### building ###"
echo "executing: scalac -cp $working_dir/lib/QuickServer.jar -d $working_dir/classes $working_dir/src/main/scala/main/* "
scalac -cp $working_dir/lib/QuickServer.jar -d $working_dir/classes $working_dir/src/main/scala/main/*

# package
echo "### packaging ###"
echo "executing: jar cvf $working_dir/target/quickServerFS.jar -C $working_dir/classes ."
jar cvf $working_dir/target/quickServerFS.jar -C $working_dir/classes .

# pack the jar with scala-library into one jar
echo "### one-jar ###"
mkdir -p tmp
cd tmp
jar -xf $working_dir/lib/scala-library.jar
jar -xf $working_dir/target/quickServerFS.jar
cd ../
jar cvf $working_dir/target/quickServerFS.jar -C tmp . 1>/dev/null
rm -rf tmp
