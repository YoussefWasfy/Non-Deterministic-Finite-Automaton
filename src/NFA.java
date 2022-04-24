//package csen1002.main.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

/**
 * Write your info here
 *
 * @name Youssef Wasfy
 * @id 43-3793/ 1000329
 * @labNumber 00
 */
public class NFA{
    /**
     * NFA constructor
     *
     * @param description is the string describing a NFA
     */
    String description;
    String currentState;
    String initialState;
    ArrayList<String> states ;
    ArrayList<String> acceptedStates;
    ArrayList<String> stateTransitions;
    Hashtable<String, String> zerosTable;
    Hashtable<String, String> onesTable;
    Hashtable<String, String> epsilonsTable;
    Hashtable<String, String> zeroStateTable;
    Hashtable<String, String> oneStateTable;
    String[][] stateDiagram;

    public NFA(String description) {
        // TODO Write Your Code Here
        this.description = description;
        this.states = new ArrayList<String>();
        this.zerosTable = new Hashtable<String, String>();
        this.onesTable = new Hashtable<String, String>();
        this.epsilonsTable = new Hashtable<String, String>();
        this.zeroStateTable = new Hashtable<String, String>();
        this.oneStateTable = new Hashtable<String, String>();
        decomposeDescription(this.description);
    }
    public void decomposeDescription(String description)
    {
        String zoef[] = description.split("#");
        String zerosTransitions[] = zoef[0].split(";");
        String onesTransitions[] = zoef[1].split(";");
        String epsilonsTransitions[] = zoef[2].split(";");
        this.acceptedStates = new ArrayList<>(Arrays.asList(zoef[3].split(",")));
        for (int i=0; i< zerosTransitions.length; i++)
        {
            if (zerosTable.containsKey(zerosTransitions[i].split(",")[0]))
            {
                this.zerosTable.put(zerosTransitions[i].split(",")[0], zerosTable.get(zerosTransitions[i].split(",")[0])  + zerosTransitions[i].split(",")[1]+":");
            }
            else
            {
                this.zerosTable.put(zerosTransitions[i].split(",")[0], zerosTransitions[i].split(",")[1]+":");
            }

        }
        for (int i=0; i< onesTransitions.length; i++)
        {
            if (onesTable.containsKey(onesTransitions[i].split(",")[0]))
            {
                this.onesTable.put(onesTransitions[i].split(",")[0], onesTable.get(onesTransitions[i].split(",")[0]) + onesTransitions[i].split(",")[1] + ":");
            }
            else
            {
                this.onesTable.put(onesTransitions[i].split(",")[0], onesTransitions[i].split(",")[1] +":");
            }

        }
        for (int i=0; i< epsilonsTransitions.length; i++)
        {
            if (epsilonsTable.containsKey(epsilonsTransitions[i].split(",")[0]))
            {
                this.epsilonsTable.put(epsilonsTransitions[i].split(",")[0], epsilonsTable.get(epsilonsTransitions[i].split(",")[0])+ epsilonsTransitions[i].split(",")[1]+":");
            }
            else
            {
                this.epsilonsTable.put(epsilonsTransitions[i].split(",")[0], epsilonsTransitions[i].split(",")[1]+":");
            }

        }

        Set<String> zeroSetOfKeys = zerosTable.keySet();
        Set<String> oneSetOfKeys = onesTable.keySet();
        Set<String> epsilonSetOfKeys = epsilonsTable.keySet();

        for (String key : zeroSetOfKeys)
        {
            if (!this.states.contains(key))
                this.states.add(key);
            String values[] = this.zerosTable.get(key).split(":");
            for (int i=0; i<values.length;i++)
            {
                if (!this.states.contains(values[i]))
                {
                    this.states.add(values[i]);
                }
            }
        }
        for (String key : oneSetOfKeys)
        {
            if (!this.states.contains(key))
                this.states.add(key);
            String values[] = this.onesTable.get(key).split(":");
            for (int i=0; i<values.length;i++)
            {
                if (!this.states.contains(values[i]))
                {
                    this.states.add(values[i]);
                }
            }
        }
        for (String key : epsilonSetOfKeys)
        {
            if (!this.states.contains(key))
                this.states.add(key);
            String values[] = this.epsilonsTable.get(key).split(":");
            for (int i=0; i<values.length;i++)
            {
                if (!this.states.contains(values[i]))
                {
                    this.states.add(values[i]);
                }
            }
        }
        for (int i =0; i< this.acceptedStates.size();i++)
        {
            if (!this.states.contains(this.acceptedStates.get(i)))
                this.states.add(this.acceptedStates.get(i));
        }
        this.stateDiagram = new String[this.states.size()][2];
        System.out.println(zerosTable);
        System.out.println(onesTable);
        System.out.println(epsilonsTable);
        System.out.println(this.states.size());
        for (int i=0; i<this.states.size();i++)
        {
            System.out.print(this.states.get(i)+",");
        }
        System.out.println();

        stateTransitions(zerosTable, onesTable, epsilonsTable);
    }
    public void stateTransitions(Hashtable<String,String> zeros, Hashtable<String,String> ones, Hashtable<String,String> epsilons)
    {
        for (int i=0; i< this.states.size();i++)
        {
            if (this.zerosTable.containsKey(this.states.get(i)))
            {
                String zTransitions = this.zerosTable.get(this.states.get(i));
                String zTransitionsSplit[] = zTransitions.split(":");
                for (int k = 0; k < zTransitionsSplit.length; k++)
                {
                    if (this.epsilonsTable.containsKey(zTransitionsSplit[k]))
                        zTransitions +=  this.epsilonsTable.get(zTransitionsSplit[k]);
                }
                this.stateDiagram[Integer.parseInt(this.states.get(i))][0] =  zTransitions;
            }
            else if (this.epsilonsTable.containsKey(this.states.get(i)))
            {
                String eTransitions = this.epsilonsTable.get(this.states.get(i));
                String eTransitionsSplit [] = eTransitions.split(":");
                for (int k=0; k<eTransitionsSplit.length; k++)
                {
                    if (this.epsilonsTable.containsKey(eTransitionsSplit[k]))
                    {
                        eTransitions +=  this.epsilonsTable.get(eTransitionsSplit[k]);
                    }
                }
                this.stateDiagram[Integer.parseInt(this.states.get(i))][0] = eTransitions;
            }
            if (this.onesTable.containsKey(this.states.get(i)))
            {
                String oTransitions = this.onesTable.get(this.states.get(i));
                String oTransitionsSplit[] = oTransitions.split(":");
                for (int k = 0; k < oTransitionsSplit.length; k++)
                {
                    if (this.epsilonsTable.containsKey(oTransitionsSplit[k]))
                        oTransitions += this.epsilonsTable.get(oTransitionsSplit[k]);
                }
                this.stateDiagram[Integer.parseInt(this.states.get(i))][1] = oTransitions;
            }
            else if (this.epsilonsTable.containsKey(this.states.get(i)))
            {
                String eTransitions = this.epsilonsTable.get(this.states.get(i));
                String eTransitionsSplit [] = eTransitions.split(":");
                for (int k=0; k< eTransitionsSplit.length; k++)
                {
                    if (this.epsilonsTable.containsKey(eTransitionsSplit[k]))
                    {
                        eTransitions +=  this.epsilonsTable.get(eTransitionsSplit[k]);
                    }
                }
                this.stateDiagram[Integer.parseInt(this.states.get(i))][1] = eTransitions;
            }
        }


        for (int i =0; i<this.states.size();i++)
        {
            for (int j=0; j<2; j++)
            {
                System.out.print(this.stateDiagram[i][j] + " ");
            }
            System.out.println();
        }

    }


    /**
     * Returns true if the string is accepted by the NFA and false otherwise.
     *
     * @param input is the string to check by the NFA.
     * @return if the string is accepted or not.
     */
    public boolean run(String input) {
        // TODO Write Your Code Here
        this.currentState = this.stateDiagram[0][0];
        for (int i=0; i< input.length(); i++)
        {
            String currentInput = Character.toString(input.charAt(i));
            if (currentInput.equals("0"))
            {
                String cStates [] = this.currentState.split(":");
                String nState = "";
                for (int j =0; j < cStates.length; j++)
                {
                    if (!cStates[j].equals(""))
                        if (this.stateDiagram[Integer.parseInt(cStates[j])][0] != null)
                            nState += this.stateDiagram[Integer.parseInt(cStates[j])][0];
                }
                this.currentState = nState;
            }
            else if (currentInput.equals("1"))
            {
                String cStates[] = this.currentState.split(":");
                String nState = "";
                for (int j =0; j < cStates.length; j++)
                {
                    if (this.stateDiagram[Integer.parseInt(cStates[j])][1] != null )
                        nState += this.stateDiagram[Integer.parseInt(cStates[j])][1];
                }
                this.currentState = nState;
            }
            System.out.println(this.currentState);
        }
        String finalState[] = this.currentState.split(":") ;
        for (int k = 0; k< finalState.length; k++)
        {
            if (this.acceptedStates.contains(finalState[k]))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        NFA nfa = new NFA("2,3#4,5;7,8#0,1;0,7;1,2;1,4;3,6;5,6;6,1;6,7#8");
        boolean result = nfa.run("10101010");
        System.out.println(result);

    }
}
