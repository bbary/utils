#!/bin/bash

echo "execute auth ws call $1 time in parallel"
truncate -s 0 result
CMDS=(
	"echo hello 1" 
	"echo hello 2" 
	"echo hello 3"
	)
for (( i = 1; i <= $1; i++ ))
do 
{
	start=$(date +%s%N)
	message=${CMDS[RANDOM % ${#CMDS[@]}]}
	#code=`curl -s -I -X GET http://google.fr | grep HTTP/1.1 | awk {'print $2'}`
	code=`curl -s -I   -H "Content-Type: application/json;" -H "x-access-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDYwOTYyMzQsInVzZXIiOiJpb2RhIiwidGltZXN0YW1wIjoxNTA2MDA5ODM0OTExfQ.3c4ol7nffrRvqez58eyvCps0msYGObLM9R48hpCNjw8" -X GET http://localhost:2332/ias2/OCs/SOC1704UTStreet1 | grep HTTP/1.1 | awk {'print $2'}`
	vtime=$((($(date +%s%N) - $start)/1000000))
	echo "getOX; process $i; $code; $message; $vtime" >> result_getOX
} &
done