#!/bin/bash
set -e

# pulls the first occurence of a char from a list of chars
#Params: 
# 1: list of chars
# 2: the char to pull
# 3: the result variable
# Returns a list of sorted floats in the second parameter
function pull_first_occurence_from_list()
{

	result=""
	found="false"
	for i in $1; do
		if [ $i != $2 -o $found = "true" ] ; then
			result="$result $i"		
		else
			found="true"
		fi			
	done
eval "$3=\$result"
}

# Sorts floats 
#Params: 
# 1: list of floats
# 2: sorted floats
# Returns a list of sorted floats in the  parameter 2
function sort_floats()
{
list="$1"
sorted_list=""

#echo $list
for i in $list; do
	#echo $list
	min=`echo $list | cut -d ' ' -f 1`
	for j in $list; do
		var=$(awk 'BEGIN{ print "'$j'"<="'$min'" }')    
		if [ $var -eq 1 ]; then
			min=$j
		fi
	done		
	pull_first_occurence_from_list "$list" $min list
	sorted_list="$sorted_list $min"
	#echo $list
done
eval "$2=\$sorted_list"
}

