#include <mpi.h>
#include <vector>
#include <cmath>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <queue>

using namespace std;

const int REQUEST_TAG = 100;
const int TASK_TAG = 101;
const int EXIT_TAG = 102;

void server(int rank, int size, int num_of_tasks) {
    queue<int> tasks;

	// Init tasks
	for (int i = 0; i < num_of_tasks; i++) {
		tasks.push(i);
	}

    double start_time = MPI_Wtime();
	// Distribute tasks
	int remaining_workers = size - 1;
	while (remaining_workers > 0) {
		MPI_Status stat;
		// Wait for incoming message from a worker
		MPI_Probe(MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &stat); 
		const int source_rank = stat.MPI_SOURCE;
		const int tag = stat.MPI_TAG;
        if (tag == REQUEST_TAG) { // message is a task request
            // Receive task request
		    MPI_Recv(nullptr, 0, MPI_INT, source_rank, tag, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		    if (!tasks.empty()) {
		        // We have a task - send it
			    MPI_Send(&tasks.front(), 1, MPI_INT, source_rank, TASK_TAG, MPI_COMM_WORLD);
			    tasks.pop();
		    } else {
		        // There are no more tasks - tell worker to exit
			    MPI_Send(nullptr, 0, MPI_INT, source_rank, EXIT_TAG, MPI_COMM_WORLD);
			    remaining_workers--;
		    }
        } else {
            printf("Unknown tag: %d\n", tag);
        }
	}
    double end_time = MPI_Wtime();
    printf("Server is done, time = %.5f\n", end_time - start_time);
}

void exec_task(int worker_rank, int task) {
    printf("%d: received task %d\n", worker_rank, task);
}

void worker(int rank, int size, int server_rank) {
	bool working = true;
	while (working) {
		// Send a request to the server
		MPI_Send(nullptr, 0, MPI_INT, server_rank, REQUEST_TAG, MPI_COMM_WORLD);
		// Wait for a response
		MPI_Status stat;
		MPI_Probe(server_rank, MPI_ANY_TAG, MPI_COMM_WORLD, &stat);
		const int tag = stat.MPI_TAG;
		if (tag == TASK_TAG) { // response is a task
			int task;
			MPI_Recv(&task, 1, MPI_INT, server_rank, tag, MPI_COMM_WORLD, MPI_STATUS_IGNORE);			
            // Executing the task
			exec_task(rank, task);
		} else if (tag == EXIT_TAG) { // response is a signal to exit
			MPI_Recv(nullptr, 0, MPI_INT, server_rank, tag, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			working = false;
		} else {
            printf("Unknown tag: %d\n", tag);
		}
	}
}

int main(int argc, char **argv) {

	MPI_Init(&argc, &argv);

	int num_of_tasks = (argc > 1 ? stoi(argv[1]) : 1000); 

	int rank, size;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

    const int SERVER_RANK = 0;	

	if (rank == SERVER_RANK) {
        // This is a server process
        server(rank, size, num_of_tasks);
	} else {
        // This is a worker process
	    worker(rank, size, SERVER_RANK);
	}

	MPI_Finalize();

	return 0;
}
