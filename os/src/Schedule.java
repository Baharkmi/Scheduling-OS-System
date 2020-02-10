
public class Schedule extends Thread
{
    static boolean ready_for_fork = true;
    static boolean done = false;
    Runnable method;

    Schedule(Runnable r)
    {
        this.method = r;
    }

    static void FCFS()
    {
        while(true)
        {
            System.out.print("");
            if(ready_for_fork && !Machine.jobsQ.isEmpty())
            {
                ready_for_fork = false;
                Process p = Machine.jobsQ.pollFirst();
                if(p != null)
                    p.start();
            }
        }
    }

    static void SJF()
    {
        while(true)
        {
            System.out.print("");
            if(ready_for_fork && !Machine.jobsQ.isEmpty())
            {
                ready_for_fork = false;
                Process p = Machine.jobsQ.pollFirst();
                if(p != null)
                    p.start();
            }
        }
    }

    static void SRT()
    {
        while(true)
        {
            System.out.print("");
            if(!Machine.jobsQ.isEmpty())
            {
                long minrem = 99999;
                int idx = 0;
                for(int i = 0; i < Machine.jobsQ.size(); i++)
                {
                    if(Machine.jobsQ.get(i).done)
                    {
//                        Machine.jobsQ.remove(i);
                        continue;
                    }
                    if(Machine.jobsQ.get(i).elapsed_time < minrem)
                    {
                        minrem = Machine.jobsQ.get(i).elapsed_time;
                        idx = i;
                    }
                }

                if(Process.running_idx == -1)
                {
                    try
                    {
                        Machine.jobsQ.get(idx).start();
                    }
                    catch (Exception e)
                    {
                        Machine.jobsQ.get(idx).resume_p();
                    }
                    Process.running_idx = idx;
                    System.out.print(Machine.jobsQ.get(idx).name + ", ");
                }
                else
                {
                    if(!Machine.jobsQ.get(Process.running_idx).name.equals(Machine.jobsQ.get(idx).name))
                    {
                        Machine.jobsQ.get(Process.running_idx).suspend_p();
                        try
                        {
                            Machine.jobsQ.get(idx).start();
                        }
                        catch (Exception e)
                        {
                            Machine.jobsQ.get(idx).resume_p();
                        }
                        System.out.print(Machine.jobsQ.get(idx).name + ", ");
                    }
                }
            }
        }
    }

    static void RR()
    {

    }

    public void run()
    {
        method.run();
    }

}
