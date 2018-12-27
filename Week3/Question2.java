import java.io.*;

class Question2
{
    // A finite-state automaton 
    static class FSA
    {
        int alphabet_size;
        int n_states;
        int delta[][];
        int initial_state;
        int final_states[];
    }


    public static void main(String[] args) throws IOException
    {
        FSA A=new FSA();

        A.alphabet_size=2;
        A.n_states=4;
        A.delta=new int[][]{
            {0, 0, 1},
            {0, 0, 3},
            {0, 1, 3},
            {1, 1, 2},
			{1, 1, 3},
			{2, 0, 3},
			{3, 0, 3}
        };
        A.initial_state=0;
        A.final_states=new int[]{3};

        print(A, "A2");
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
          System.out.print("\nPlease type a word: ");
          s = in.readLine();
          if (s.equals("can we stop please")) break;
          boolean acc = run_FSA(A, s, 0);
          if (acc) { System.out.println("Yes, "+s+" is accepted"); }
          else {System.out.println("No, "+s+" is not accepted"); }
        }
    }


    // Returns true if i is an accepting state in A
    static boolean is_final_state(FSA A, int i)
    {
        boolean is_accepting = false;
        for(int a=0; !is_accepting && a<A.final_states.length; a++)
            is_accepting = (A.final_states[a]==i);
        return is_accepting;
    }

    // Run the FSA A on input string s from state i
    static boolean run_FSA(FSA A, String s, int i)
    {
        System.out.println("-> now I am in state "+i+", scanning "+s);

        if (s.length() == 0) return is_final_state(A,i);
        int inp = Character.getNumericValue(s.charAt(0));
        
        for(int a=0; a<A.delta.length; a++) 
            if (A.delta[a][0] == i && A.delta[a][1] == inp) {
                System.out.println("--> moving to state "+A.delta[a][2]+", on "+A.delta[a][1]);
                if (run_FSA(A, s.substring(1), A.delta[a][2])) return true;
            }
        return false;
    }

    // Print the finite-state automaton as a five-tuple
    static void print(FSA A, String name)
    {

        System.out.print(name+" = (");

        // print the alphabet ...
        System.out.print("{");
        for(int i=0; i<A.alphabet_size; i++)
        {
            if(i!=0) System.out.print(", ");
            System.out.print(i);
        }
        System.out.print("}, ");

        // ... and the set of states ...
        System.out.print("{");
        for(int i=0; i<A.n_states; i++)
        {
            if(i!=0) System.out.print(", ");
            System.out.print("q"+i);
        }
        System.out.print("}, ");

        // ... and the transition relation ...
        System.out.print("{");
        for(int i=0; i<A.delta.length; i++)
        {
            if(i!=0) System.out.print(", ");
            System.out.print("(q"+A.delta[i][0]+", "+A.delta[i][1]+", q"+A.delta[i][2]+")");
        }
        System.out.print("}, ");

        // ... and the initial state ...
        System.out.print("q"+A.initial_state);
        System.out.print(", ");

        // ... and the set of accepting states
        System.out.print("{");
        for(int i=0; i<A.final_states.length; i++)
        {
            if(i!=0) System.out.print(", ");
            System.out.print("q"+A.final_states[i]);
        }
        System.out.print("}");

        System.out.println(")");
    }

}

