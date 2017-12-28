#!/usr/bin/env bash

working_dir="$( cd "$(dirname $0)" ; pwd -P )"/..
java -jar $working_dir/lib/QuickServer.jar -load $working_dir/conf/conf.xml