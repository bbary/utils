#!/bin/bash

## Usage
# ws_tests_seq  <path_to_conf_file>  <valid_token>  <port>


if [ "$#" -eq 1 ] && ( [ "$1" == "-h" ] || [ "$1" == "--help" ] ) ; then
	echo "usage: ws_tests_seq  <path_to_conf_file>  <valid_token>  <port>"
	exit 0
fi

if [ "$#" -ne 3 ]; then
    echo "Illegal number of parameters"
    echo "usage: ws_tests_seq  <path_to_conf_file>  <valid_token>  <port>"
    exit 1
fi

file=$1

if [ -f "$file" ]
then
  echo "### parsing config file: $file"
  source $file
else
  echo "$file not found"
fi

truncate -s 0 result_parallel
truncate -s 0 shared_indexes

token=$2
port=$3
#random=`echo file_$RANDOM.txt`
cmds=(
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/OCs/${idOC_getOC}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/OMs/${idOM_getOM}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/LMs/${idLM}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/LCs/${idLC}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -d \"{\\\"type_filtre\\\":\\\"OC\\\",\\\"liste_type_filtre\\\":null,\\\"periode_date_debut\\\":null,\\\"periode_date_fin\\\":null,\\\"direction\\\":null}\" -D - http://localhost:${port}/ias2/graphLC/OM/${idOM_graphLC}?range=1,50  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -d \"{\\\"type_filtre\\\":\\\"OM\\\",\\\"liste_type_filtre\\\":null,\\\"periode_date_debut\\\":null,\\\"periode_date_fin\\\":null,\\\"direction\\\":null}\" -D - http://localhost:${port}/ias2/graphLM/${idOM_graphLM}?range=1,50  | grep HTTP/1.1 | cut -d ' ' -f 2"
	'curl -i -H "x-access-token: $token" -F name=`echo file_$RANDOM.txt` -F type=DS_PJ -F file=@${uploadFile} http://localhost:2332/ias2/pj  | grep HTTP/1.1 | cut -d " " -f 2 | tail -1'
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/pj/${resourceId}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	"curl -s -I -H \"Content-Type: application/json;\" -H \"x-access-token: $token\" -X GET http://localhost:${port}/ias2/getOMBs/${idOC_getOMB}/${idOM_getOMB}  | grep HTTP/1.1 | cut -d ' ' -f 2"
	# add commands here
	)

#cmds=(
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	#"curl -s -I -X GET http://google.fr  | grep HTTP/1.1 | cut -d ' ' -f 2"
	## add commands here
	#)

indexes=(0 1 2 3 4 5 6 7 8)

echo ${!indexes[@]} >> shared_indexes

#for i in `tail -1 shared_indexes`; do
#	echo $i
#done

declare -A map

map["0"]=getOC

map+=( ["1"]=getOM ["2"]=getLM ["3"]=getLC ["4"]=grapheLC ["5"]=grapheLM ["6"]=postPJ ["7"]=getPJ ["8"]=getOMBs)

max_ws_call=$((
	$getOC_max_ws_call+
	$getOM_max_ws_call+
	$getLM_max_ws_call+
	$getLC_max_ws_call+
	$grapheLC_max_ws_call+
	$grapheLM_max_ws_call+
	$postPJ_max_ws_call+
	$getPJ_max_ws_call+
	$getOMBs_max_ws_call
	))

echo "### Executing	$max_ws_call web service call in parallel"

function drop_item {
	to_delete=$1
	new_list=()
	for item in `tail -1 shared_indexes`
	do	
    	if [ "$item" != "$to_delete" ]
    	then
        	new_list+=("$item")
    	fi
	done
	echo ${new_list[@]} >> shared_indexes
	unset new_list
}

function execute_cmd {

	IFS=', ' read -r -a shared_list <<< `tail -1 shared_indexes`
	index=${shared_list[$1]}
	code=KO
	start=$(date +%s%N)
	case "$index" in
	0)
    if [ $getOC_max_ws_call -gt 0 ]
    then
    	((getOC_max_ws_call--))
    	echo "getOC_max_ws_call $getOC_max_ws_call"
    	if [ $getOC_max_ws_call == 0 ]
    	then
    		drop_item 0
    	fi	
    	
    	code=$(eval ${cmds[$index]})

	fi
    ;;
	1)
    if [ $getOM_max_ws_call -gt 0 ]
    then
    	((getOM_max_ws_call--))
    	if [ $getOM_max_ws_call == 0 ]
    	then
    		drop_item 1
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    2)
    if [ $getLM_max_ws_call -gt 0 ]
    then
    	((getLM_max_ws_call--))
    	if [ $getLM_max_ws_call == 0 ]
    	then
    		drop_item 2
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    3)
    if [ $getLC_max_ws_call -gt 0 ]
    then
    	((getLC_max_ws_call--))
    	if [ $getLC_max_ws_call == 0 ]
    	then
    		drop_item 3
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    4)
    if [ $grapheLC_max_ws_call -gt 0 ]
    then
    	((grapheLC_max_ws_call--))
    	if [ $grapheLC_max_ws_call == 0 ]
    	then
    		drop_item 4
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    5)
    if [ $grapheLM_max_ws_call -gt 0 ]
    then
    	((grapheLM_max_ws_call--))
    	if [ $grapheLM_max_ws_call == 0 ]
    	then
    		drop_item 5
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    6)
    if [ $postPJ_max_ws_call -gt 0 ]
    then
    	((postPJ_max_ws_call--))
    	if [ $postPJ_max_ws_call == 0 ]
    	then
    		drop_item 6
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    7)
    if [ $getPJ_max_ws_call -gt 0 ]
    then
    	((getPJ_max_ws_call--))
    	if [ $getPJ_max_ws_call == 0 ]
    	then
    		drop_item 7
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;
    8)
    if [ $getOMBs_max_ws_call -gt 0 ]
    then
    	((getOMBs_max_ws_call--))
    	if [ $getOMBs_max_ws_call == 0 ]
    	then
    		drop_item 8
    	fi
    	
    	code=$(eval ${cmds[$index]})
	fi
    ;;    
	*)
    echo "internal problem: $index not found in table"
    exit 1
    ;;
esac

	if [[ $code =~ ^[0-9]{3}$ ]]
	then
		vtime=$((($(date +%s%N) - $start)/1000000))
		echo "${map[${index}]}; $code; $vtime" >> result_parallel
	fi
		
}


loop=$((max_ws_call/parallel + 1))

for (( j = 1; j <= $loop; j++ ))
do 
 {
 	for (( i = 1; i <= $parallel; i++ ))
	do 
	{
		IFS=', ' read -r -a shared_list <<< `tail -1 shared_indexes`
		index=$(($RANDOM % ${#shared_list[@]}))
		execute_cmd $index
	} & 
	done
	wait
}
done
