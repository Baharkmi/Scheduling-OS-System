
import javax.crypto.Mac;

public class Process extends Thread
{
    static Process current_running = null;
    static int running_idx = -1;

    String name;

    long arrival_time,
        burst_time,
        start_time,
        turnaround_time = 0,
        waiting_time = 0,
        elapsed_time = 0,
        prev_sus_time;

    long  partial_start_time;

    boolean running = false;
    boolean done = false;


    Process(String n, int arrt, int but)
    {
        this.name = n;
        this.arrival_time = arrt;
        this.burst_time = but;
    }

    public void run()
    {
        current_running = this;

//        idle = false;
        running = true;
        start_time = System.currentTimeMillis();
        partial_start_time = System.currentTimeMillis();

        // simulating a real process
        while(elapsed_time < burst_time) {
            elapsed_time = System.currentTimeMillis() - partial_start_time;
        }

        waiting_time = System.currentTimeMillis() - Machine.appst - burst_time - arrival_time;
        Machine.wait_avg += waiting_time;
        turnaround_time = System.currentTimeMillis() - Machine.appst - arrival_time;
        Machine.turn_avg += turnaround_time;
        // ended
        System.out.println("Process " + name + ":");
        System.out.println("arrival time: " + arrival_time);
        System.out.println("waiting time: " + waiting_time);
        System.out.println("turnaround time: " + turnaround_time);
        System.out.println("======================================");

        Schedule.ready_for_fork = true;
        Machine.jctr--;


        if(Machine.jctr <= 0)
        {
            System.out.println("Throughput: " + (Machine.jobsC / ((float)(System.currentTimeMillis() - Machine.appst))));
            System.out.println("Average waiting time: " + (Machine.wait_avg / Machine.jobsC));
            System.out.println("Average turnaround time: " + (Machine.turn_avg / Machine.jobsC));
            System.exit(0);
        }
//        Machine.jobsQ.remove(this);
        done = true;
        current_running = null;
        stop();
    }

    public void suspend_p()
    {
        running = false;
        prev_sus_time = System.currentTimeMillis();
        this.suspend();
    }

    public void resume_p()
    {
        running = true;
        partial_start_time = System.currentTimeMillis();
        this.resume();
    }


}
