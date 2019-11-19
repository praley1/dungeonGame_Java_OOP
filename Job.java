/** Patrick Raley
 *  CMSC335 OOP
 *  Project Final Sorcerers Cave
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Job extends CaveElement implements Runnable {
    static Random rn = new Random ();
    JPanel parent;
    Creature worker = null;
    int jobIndex;
    long jobTime;
    String jobName = "";
    JProgressBar pm = new JProgressBar ();
    boolean goFlag = true, noKillFlag = true;
    JButton jbGo   = new JButton ("Stop");
    JButton jbKill = new JButton ("Cancel");
    Status status = Status.SUSPENDED;
    String artifactType;
    int wandsNeeded = 0;
    int potionsNeeded = 0;
    int stonesNeeded = 0;
    int weaponsNeeded = 0;
    int numNeeded = 0;

    enum Status {RUNNING, SUSPENDED, WAITING, DONE};

    public Job(HashMap<Integer, Creature> hc, JPanel cv, Scanner sc) {
        parent = cv;
        sc.next (); // dump first field, j
        jobIndex = sc.nextInt ();
        jobName = sc.next ();
        int target = sc.nextInt ();
        worker = hc.get (target);
        jobTime = (long) (sc.nextDouble());

        while (sc.hasNext()) {
            artifactType = sc.next();
            numNeeded = sc.nextInt();

            if (artifactType.endsWith("s"))
                artifactType = artifactType.substring(0, artifactType.length()-1);

           if(artifactType.compareToIgnoreCase("wand") == 0) {
                wandsNeeded = numNeeded;
            }
            if(artifactType.compareToIgnoreCase("potion") == 0){
                potionsNeeded = numNeeded;
            }
            if(artifactType.compareToIgnoreCase("stone") == 0) {
                stonesNeeded = numNeeded;
            }
            if(artifactType.compareToIgnoreCase("weapon") == 0) {
                weaponsNeeded = numNeeded;
            }

        }




//        pm = new JProgressBar ();
        pm.setStringPainted (true);
        parent.add (pm);
        parent.add (new JLabel (worker.name, SwingConstants.CENTER));
        parent.add (new JLabel (jobName    , SwingConstants.CENTER));
        (new Thread (this, worker.name + " " + jobName)).start();

        parent.add (jbGo);
        parent.add (jbKill);


        jbGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleGoFlag();
            }
        });
        jbKill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setKillFlag();
            }
        });

    } // end constructor

//     JLabel jln = new JLabel (worker.name);
    // following is text alignment relative to icon
//     jln.setHorizontalTextPosition (SwingConstants.CENTER);
//     jln.setHorizontalAlignment (SwingConstants.CENTER);
//     parent.jrun.add (jln);

    public void toggleGoFlag () {
        goFlag = !goFlag; // ND; should be synced, and notify waiting sync in running loop
    } // end method toggleRunFlag

    public void setKillFlag () {
        noKillFlag = false;
        jbKill.setBackground (Color.red);
    } // end setKillFlag

    void showStatus (Status st) {
        System.out.println(st + " : " + toString());
        status = st;
        switch (status) {
            case RUNNING:
                jbGo.setBackground (Color.green);
                jbGo.setText ("Running");
                break;
            case SUSPENDED:
                jbGo.setBackground (Color.yellow);
                jbGo.setText ("Suspended");
                break;
            case WAITING:
                jbGo.setBackground (Color.orange);
                jbGo.setText ("Waiting turn");
                break;
            case DONE:
                jbGo.setBackground (Color.red);
                jbGo.setText ("Done");
                break;
        } // end switch on status
    } // end showStatus

    public boolean getResources(){
        return worker.party.pool.getResources(wandsNeeded, potionsNeeded, stonesNeeded, weaponsNeeded);
    }

    public void run () {
        long time = System.currentTimeMillis();
        long startTime = time;
        long stopTime = time + 1000 * jobTime;
        double duration = stopTime - time;

        synchronized (worker.party) {
            boolean hasResources = getResources();
            while (worker.busyFlag || !hasResources) {
                showStatus (Status.WAITING);
                if (hasResources)
                    worker.party.pool.returnResources(wandsNeeded, potionsNeeded, stonesNeeded, weaponsNeeded);
                try {
                    worker.party.wait();
                }
                catch (InterruptedException e) {
                } // end try/catch block
                hasResources = getResources();
            } // end while waiting for worker to be free
            worker.busyFlag = true;
        } // end sychronized on worker

        //System.out.println("busy, stopTime = " + stopTime + ", time = " + time);
        while (time < stopTime && noKillFlag) {
            try {
                Thread.sleep (100);
            } catch (InterruptedException e) {}
            if (goFlag) {
                showStatus (Status.RUNNING);
                time += 100;
                pm.setValue ((int)(((time - startTime) / duration) * 100));
            } else {
                showStatus (Status.SUSPENDED); // should wait here, not busy looop
            } // end if stepping
        } // end runninig

        //System.out.println("finished");
        pm.setValue (100);
        showStatus (Status.DONE);
        synchronized (worker.party) {
            worker.busyFlag = false;
            worker.party.pool.returnResources(wandsNeeded, potionsNeeded, stonesNeeded, weaponsNeeded);
            worker.party.notifyAll ();
        }

    } // end method run - implements runnable



    public String toString () {
        String sr = String.format ("j:%7d:%15s:%7d:%5d", jobIndex, jobName, worker.index, jobTime);
        return sr;
    } //end method toString

} // end class Job
