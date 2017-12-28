#!/usr/bin/env bash

working_dir="$( cd "$(dirname $0)" ; pwd -P )"/..
scala -cp $working_dir/target/quickServerFS.jar:$working_dir/lib/QuickServer.jar main.QuickServerFSClient 127.0.0.1 8030