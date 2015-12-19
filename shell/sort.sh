#!/bin/bash
set -e

source global_functions.sh
file_to_sort=file_to_sort
sorted_file=sorted_file

truncate -s 0 $sorted_file

list=$( sed "s/ /;/g" $file_to_sort )
sorted_list=""
min_line=""
#echo $list
for i in $list; do
	#echo $list
	min=`echo $list | cut -d ' ' -f 1 | cut  -d ';' -f 1`
	for j in $list; do
		float=`echo $j | cut  -d ';' -f 1`
		var=$(awk 'BEGIN{ print "'$float'"<="'$min'" }')    
		if [ $var -eq 1 ]; then
			min=$float
			min_line=$j
		fi
	done		
	pull_first_occurence_from_list "$list" $min_line list
	min_line=$( echo $min_line | sed -e "s/;/ /g")
	echo "$min_line" >> $sorted_file
	#sorted_list="$sorted_list $min"
	#echo $list
done

# 	result=""
# 	found="false"
# 	for i in $1; do
# 		if [ $i != $2 -o $found = "true" ] ; then
# 			result="$result $i"		
# 		else
# 			found="true"
# 		fi			
# 	done
# eval "$3=\$result"




