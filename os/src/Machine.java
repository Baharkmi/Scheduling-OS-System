
import java.util.*;

public class Machine
{

    static long appst;
    static ArrayList<Process> jobs = new ArrayList<>();
    static LinkedList<Process> jobsQ = new LinkedList<>();
    static Timer timer = new Timer();
    static int jobsC, jctr;
    static long wait_avg = 0, turn_avg = 0;

    static boolean processing = true;

    public static void main(String args[])
    {
        jobs.add(new Process("A", 0, 2000));
        jobs.add(new Process("B", 150, 400));
        jobs.add(new Process("C", 400, 750));
        jobs.add(new Process("D", 0, 620));
        jobs.add(new Process("E", 70, 1200));

        appst = System.currentTimeMillis();
        jobsC = jobs.size();
        jctr = jobsC;
        FCFS();
//        SJF();
//        SRT();

    }

    static void FCFS()
    {
        Schedule sched = new Schedule(Schedule::FCFS);

        sched.start();

        while(true)
        {
            long pnow = System.currentTimeMillis() - appst;
            for(int i = 0; i < jobs.size(); i++)
            {
                if(jobs.get(i).arrival_time <= pnow)
                {
                    jobsQ.addLast(jobs.get(i));
                    jobs.remove(i);
                }
            }
        }
    }

    static class burst_time_cmp implements Comparator<Process> {
        @Override
        public int compare(Process a, Process b) {
            return a.burst_time < b.burst_time ? -1 : a.burst_time == b.burst_time ? 0 : 1;
        }
    }

    static void SJF()
    {
        Schedule sched = new Schedule(Schedule::SJF);

        sched.start();

        while(true)
        {
            long pnow = System.currentTimeMillis() - appst;
            for(int i = 0; i < jobs.size(); i++)
            {
                if(jobs.get(i).arrival_time <= pnow)
                {
                    jobsQ.addLast(jobs.get(i));
                    jobsQ.sort(new burst_time_cmp());
                    jobs.remove(i);
                }
            }
        }
    }

    static void SRT()
    {
        Schedule sched = new Schedule(Schedule::SRT);

        sched.start();

        while(true)
        {
            long pnow = System.currentTimeMillis() - appst;
            for(int i = 0; i < jobs.size(); i++)
            {
                if(jobs.get(i).arrival_time <= pnow)
                {
                    jobsQ.addLast(jobs.get(i));
                    jobs.remove(i);
                }
            }
        }
    }


}
