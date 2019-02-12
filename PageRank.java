import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PageRank {
	
	public Digraph myDi;
	public Digraph revDi;
	public double[] oldPRs;
	public double[] newPRs; 
	
	//This method scans in a file and populates a Digraph
	public void scanFileToDG(String fileName) {
		try {
			File current_file = new File(fileName);
			Scanner my_scanner = new Scanner(current_file);
			int countcol = 0;
			String line = my_scanner.nextLine();
			String[] tokens = line.split("\\s+");		
			for(int i1=0;i1<tokens.length;i1++) {
				countcol++;
			}
			int numOfRows = 1;
			myDi = new Digraph(countcol);
			for(int i2=0;i2<tokens.length;i2++) {
				if(tokens[i2].equals("1")) { 
					myDi.addEdge(0, i2);
				}
			}
			while(my_scanner.hasNextLine()) {
				line = my_scanner.nextLine();
				tokens = line.split("\\s+");		
				for(int i3=0;i3<tokens.length;i3++) {
					if(tokens[i3].equals("1")) { 
						myDi.addEdge(numOfRows, i3);
					}
				}	
				numOfRows++;
			}
			revDi=myDi.reverse();
		}
		 catch (FileNotFoundException e) {
             System.out.println("ERROR: " + e);
             System.exit(1);
         }
		
	}
	//gets the size of the Digraph
	public int getLength() {
		return myDi.V();
	}
	
	//calculates the Page Rank Value for each node 
	public double RP(int x) {		
		double dampening = 0.85;
		double incomingsum= 0.0;
		for (int tox:revDi.adj(x)) {
			int outoftox= myDi.outdegree(tox);
			incomingsum += oldPRs[tox]/outoftox;
		}
		return (1 - dampening) / myDi.V() + dampening * incomingsum;
	}
	
	//initializes each Page Rank value at 1/N
	public void initializePR() {
		newPRs = new double[myDi.V()];
		for (int i4=0;i4<myDi.V();i4++) {
			newPRs[i4]=1/myDi.V();
		}
		
	}
	//This method converges all page rank values	
	public void RankPage() {
		oldPRs  = new double[myDi.V()];
		double curr=0;
		double old=1;
		int count=0;//count the steps of convergence
		initializePR();
		while(Math.abs(curr-old)>=0.00000001) {
			oldPRs = newPRs.clone();
			for (int i4=0;i4<myDi.V();i4++) {
				curr=RP(i4);
				newPRs[i4] = curr;
				old=oldPRs[i4];
				count++;
			}
		}System.out.println(count);
	}
	
	//returns the array
	public double[] getArray() {
		return newPRs;
	}
	
	public static void main(String args[]) {
		PageRank tester = new PageRank();
		tester.scanFileToDG("big_graph.txt");
		tester.RankPage();
		double sum = 0;//sums all the page rank values together to see if it is close to 1
		for(int i=0;i<tester.getLength();i++) {
			sum += tester.getArray()[i];
			System.out.println(i + " Page Rank: " + tester.getArray()[i]);
		}
		System.out.println("sum: " + sum);
		
	}

}
