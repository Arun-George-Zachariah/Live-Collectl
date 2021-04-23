#!/usr/bin/env bash

# Input Defaults.
MACHINES=conf/machine_list.txt
USER="arung"
KEY=~/.ssh/id_rsa

# Usage.
usage()
{
    echo "usage: cluster_setup.sh [--machines MACHINE_LIST] [--user USER] [--key PRIVATE_KEY] [-h | --help]"
}

# Read input parameters.
while [ "$1" != "" ]; do
    case $1 in
    	--machines)
        	shift
        	MACHINES=$1
        	;;
        --user)
        	shift
        	USER=$1
        	;;
        --key)
        	shift
        	KEY=$1
        	;;
        -h | --help )
        	usage
        	exit
        	;;
        * )
        	usage
          exit
    esac
    shift
done


# Setting profile across the nodes.
for machine in $(cat $MACHINES)
do
  # Copying the script to the node.
  scp -i $KEY -o "StrictHostKeyChecking no" node_setup.sh $USER@$machine:

  # Executing node setup script.
  ssh -i $KEY -o "StrictHostKeyChecking no" $USER@$machine "bash node_setup.sh" &> logs/setup-${machine}.log &

  # Adding the id to the list.
  p_list="${p_list} $!"
done

# Waiting for the setup to finish.
echo -e "Checking PID staus:"
finished=1
while [[ ${finished} -gt 0 ]]; do
	finished=${total}

  states=""
	for pid in ${p_list}; do
		state=$(ps -o state ${pid}  |tail -n +2)
		states="${states} ${state}"
		if [[ ${#state} -eq 0 ]]; then
			finished=$((finished-1))
		fi;
	done;

  echo "Remaining: "${finished}"/"${total}
	states=${states// /}
	if [[ ${#states} -gt 0 ]]; then
		sleep 30
	fi
done;
wait

echo -e "Script Executed Successfully."
exit 0