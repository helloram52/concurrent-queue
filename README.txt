Notes:
------
- We used ThreadPool to take care of thread creation/deletion as well as to assign list operations to all the threads.
- The run parameters are configured in com/multicore/RunParameters.java which needs to be edited for a different run configuration.
- The program logs to run.log in the current directory.

Run parameters:
---------------
To modify any of the below run parameters, please modify com/multicore/RunParameters.java
- NUMBER_OF_RUNS
    - Number of times to run each experiment. Each run of the program executes Lockbased and Lockfree queue algorithms on 2 modes(ENQ, DEQ only & ENQ, DEQ, isEmpty) for 1 to n threads(n = 2 * number of processors in the system/allocated to JVM).
    - Note: We vary the threads exponentially i.e. from 1, 2, 4,..
- TARGET_OPERATION_COUNT_FOR_EACH_MODE
    - Number of operations that are to be performed in each of the 2 run modes.
- MAX_KEY_SIZE
    - Range in which the enqueue key should be selected randomly from a uniform distribution.

Run:
---
> cd SRC_DIRECTORY
> javac com/multicore/Main.java
> java com.multicore.Main

To run the program in verification mode, run it as below.
> java com.multicore.Main 100 1

where 100 is the TARGET_OPERATION_COUNT_FOR_EACH_MODE parameter specified above.

Running in TACC:
----------------
- Please use the below script.

#!/bin/bash
#SBATCH -J Project3_large      # job name
#SBATCH -o Project3_large.o%j  # output and error file name (%j=jobID)
#SBATCH -n 32           # total number of cpus requested
#SBATCH -p largemem  # queue -- normal, development, etc.
#SBATCH -t 01:00:00     # run time (hh:mm:ss) - 1.5 hours
#SBATCH --mail-user=vadivel.selvaraj@utdallas.edu
#SBATCH --mail-type=begin  # email me when the job starts
#SBATCH --mail-type=end    # email me when the job finishes
cd $HOME/ConcurrentQueue
rm -Rf com/multicore/*.class

javac com/multicore/Main.java -Xlint:unchecked
java com.multicore.Main 1000000

