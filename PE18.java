package pe18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PE18 {
    public static void main(String args[]) {

        try {
			Node head = createGraphFromFile("./pe18_test.txt");
			Node largestPathEndNode = findPath(head);
	        printPath(largestPathEndNode);
		} catch (Exception e) {
			System.out.println("File pe18_test.txt not found");
		}
        
        try {
			Node head = createGraphFromFile("./pe18.txt");
			Node largestPathEndNode = findPath(head);
	        printPath(largestPathEndNode);
		} catch (Exception e) {
			System.out.println("File pe18.txt not found");
		}
    }
    
    public static Node findPath(Node head) {
        LinkedList<Node> queue = new LinkedList<Node>();
    	int largestSum = 0;
        Node largestPathEndNode = new Node();
		queue.add(head);
        
    	while(!queue.isEmpty()) {
            Node temp = queue.remove();
            
            if(temp.getSum() > largestSum) {
            	largestSum = temp.getSum();
            	largestPathEndNode = temp;
            }
            
            ArrayList<Node> edges = temp.getEdges();
            for(int i = 0; i < edges.size(); i++) {
        		queue.add(edges.get(i));
        		if(temp.getSum() + edges.get(i).getValue() > edges.get(i).getSum()) {
        			edges.get(i).setSum(temp.getSum() + edges.get(i).getValue());
        			edges.get(i).setParent(temp);
        		}
            }
        }
    	
    	return largestPathEndNode;
    }
    
    public static void printPath(Node n) {
    	Node temp = n;
    	Stack<Node> stack = new Stack<Node>();
        System.out.println("The maximum total from top to bottom is " + temp.getSum());
        while(temp.hasParent()) {
    		stack.push(temp);
    		temp = temp.getParent();
    	}
        stack.push(temp);
        
        while(!stack.isEmpty()) {
    		System.out.print(stack.pop().getValue());
    		if(stack.size() > 0) {
    			System.out.print("->");
    		}
    	}
        System.out.println("\n");
    }
    
    public static Node createGraphFromFile(String file) throws Exception {
    	Node head = new Node();
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	
    	try {
    	    String line = br.readLine();
    	    ArrayList<Node> previousRow = new ArrayList<Node>();

    	    while (line != null) {
    	    	String[] items = line.split(" ");
    	    	ArrayList<Node> currentRow = new ArrayList<Node>();
    	    	
    	    	for(int i = 0; i < items.length; i++) {
    	    		currentRow.add(new Node(items[i]));
    	    	}
    	    	if(previousRow.size() != 0) {
    	    		for(int i = 0; i < previousRow.size(); i++) {
    	    			previousRow.get(i).connect(currentRow.get(i));
    	    			previousRow.get(i).connect(currentRow.get(i + 1));
    	    		}
    	    	}
    	    	else {
    	    		head = currentRow.get(0);
    	    	}
    	    	previousRow = currentRow;
    	        line = br.readLine();
    	    }
    	} finally {
    	    br.close();
    	}
    	
    	return head;
    }
}

class Node {
	private ArrayList<Node> edges;
    private int value;
    private int sum;
    private Node parent;

    public Node() {
        this("0");
        this.sum = this.getValue();
    }

    public Node(String value) {
    	this.edges = new ArrayList<Node>();
        this.value = Integer.parseInt(value);
        this.sum = this.getValue();
    }

    public Node connect(Node n) {
        this.edges.add(n);
        return this;
    }

    public ArrayList<Node> getEdges() {
        return this.edges;
    }

    public int getValue() {
        return this.value;
    }
    
    public Node setValue(int value) {
    	this.value = value;
    	return this;
    }
    
    public Node getParent() {
    	return this.parent;
    }
    
    public Node setParent(Node n) {
    	this.parent = n;
    	return this;
    }
    
    public boolean hasParent() {
    	if(this.parent == null) {
    		return false;
    	}
    	return true;
    }
    
    public int getSum() {
    	return this.sum;
    }
    
    public Node setSum(int sum) {
    	this.sum = sum;
    	return this;
    }
}
