package com.multicore;

import java.io.IOException;
import java.io.InterruptedIOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Utils.setLogger();

        int totalOperationsToPerform = RunParameters.TARGET_OPERATION_COUNT_FOR_EACH_MODE.value;
        int performCorrectnessTesting = 0;
        if (args.length == 1) {
            totalOperationsToPerform = Integer.valueOf(args[0]);
        }
        else if(args.length == 2) {
            totalOperationsToPerform = Integer.valueOf(args[0]);
            performCorrectnessTesting = Integer.valueOf(args[1]);
            if (performCorrectnessTesting == 1) {
                Utils.setTestingFlag();
            }
        }

        int processors = Runtime.getRuntime().availableProcessors();
        int numberOfThreads = processors * 2;
        Utils.logInfo("Number of available CPU cores #" + processors);

        for(int i = 1;i <= RunParameters.NUMBER_OF_RUNS.value;i++) {
            Utils.logInfo("Run  #" + i + ":");

            for (int j = 1;j <= numberOfThreads;j *= 2) {
                Utils.logInfo("Number of threads: " + j);
                new QueueRunner().run(j, totalOperationsToPerform, i);
            }
        }
    }
}
