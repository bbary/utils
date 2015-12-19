#!/bin/bash
set -e

source global_functions.sh

pull_first_occurence_from_list "3 1 2 3 3 2" "2" _v
echo $_v

sort_floats "1 3 5 9 0 3 4 2 0" v
echo "sorted list is $v"

