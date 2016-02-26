
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jmirandilla
 */
public class JoinUp {

    private static List<Node> nodes = new ArrayList<>();
    private static HashMap<String, String> visited = new HashMap<>();
    private static List<String> nodePath = new ArrayList<>();
    private static String word1;
    private static String word2;
    private static String[] dictionary;

    public static void main(String[] args) {
        word1 = args[0];
        word2 = args[1];
        List<String> inputWords = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            inputWords.add(s.nextLine());
        }
        inputWords.add(word2);
        inputWords.remove("");
        if (inputWords.contains(word1)) {
            inputWords.remove(word1);
        }
        dictionary = new String[inputWords.size()];
        dictionary = inputWords.toArray(dictionary);

        Node w1 = new Node(word1);
        Node w2 = new Node(word2);
        w1.setSingly(getSinglyNeighbours(w1, dictionary));
        w1.setDoubly(getDoublyNeighbours(w1, dictionary));
        //System.out.println(w1 + ": " + w1.singly);
        //System.out.println(w1 + ": " + w1.doubly);
        for (String d : dictionary) {
            Node nD = new Node(d);
            nD.setSingly(getSinglyNeighbours(nD, dictionary));
            nD.setDoubly(getDoublyNeighbours(nD, dictionary));
            nodes.add(nD);
        }
        breadthFirstSearch(w1, word2);
        //System.out.println(visited.get("do"));
        readGraph(word2);
        Collections.reverse(nodePath);
        System.out.print(nodePath.size() + " ");
        for (String nP : nodePath) {
            System.out.print(nP + " ");
        }
        System.out.println();
        nodePath.clear();
        visited.clear();
        bfsDoubly(w1, word2);
        readGraph(word2);
        Collections.reverse(nodePath);
        System.out.print(nodePath.size() + " ");
        for (String nP : nodePath) {
            System.out.print(nP + " ");
        }
        System.out.println();

    }

    public static void readGraph(String w2) {
        if (w2.equals(word1)) {
            nodePath.add(w2);
            return;
        } else {
            nodePath.add(w2);
            if (visited.containsKey(w2)) {
                String word = visited.get(w2);
                visited.remove(w2);
                readGraph(word);
            } else {
                nodePath.remove(w2);
                return;
            }
        }
    }

    public static void breadthFirstSearch(Node w1, String w2) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(w1);
        visited.put(w1.word, null);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            List<String> nodeNeighbours = current.singly;
            //Collections.reverse(nodeNeighbours);
            for (String n : nodeNeighbours) {
                if (!visited.containsKey(n)) {
                    visited.put(n, current.word);
                    //search n in the dictionary
                    for (Node node : nodes) {
                        if (node.word.equals(n)) {
                            queue.add(node);
                        }
                    }
                }
            }
        }
    }

    public static void bfsDoubly(Node w1, String w2) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(w1);
        visited.put(w1.word, null);

        while (!queue.isEmpty()) {
            Node current = queue.remove();
            List<String> nodeNeighbours = current.doubly;
            //Collections.reverse(nodeNeighbours);
            for (String n : nodeNeighbours) {
                if (!visited.containsKey(n)) {
                    visited.put(n, current.word);
                    //search n in the dictionary
                    for (Node node : nodes) {
                        if (node.word.equals(n)) {
                            queue.add(node);
                        }
                    }
                }
            }
        }
    }

    public static List<String> getSinglyNeighbours(Node n, String[] d) {
        List<String> neighbours = new ArrayList<>();
        List<String> wordPrefix = prefixFinder(n.word);

        Collections.reverse(wordPrefix);

        for (String p : wordPrefix) {
            if (p.length() >= Math.round(n.word.length() / 2.0)) {
                if (!prefixMatcher(n.word, dictionary, p).isEmpty()) {
                    for (String pr : prefixMatcher(n.word, dictionary, p)) {
                        if (!n.word.equals(pr)) {
                            neighbours.add(pr);
                        }
                    }
                }
            } else {
                if (!prefixMatcher(n.word, dictionary, p).isEmpty()) {
                    for (String pr : prefixMatcher(n.word, dictionary, p)) {
                        if (pr.length() <= p.length() * 2) {
                            if (!n.word.equals(pr)) {
                                neighbours.add(pr);
                            }
                        }
                    }
                }
            }
        }
        return neighbours;
    }

    //IMPLEMENT DOUBLY JOINED METHOD HERE
    public static List<String> getDoublyNeighbours(Node n, String[] d) {
        List<String> neighbours = new ArrayList<>();
        List<String> wordPrefix = prefixFinder(n.word);
        Collections.reverse(wordPrefix);

        for (String p : wordPrefix) {
            if (p.length() >= Math.round(n.word.length() / 2.0)) {
                if (!prefixMatcher(n.word, dictionary, p).isEmpty()) {
                    for (String pr : prefixMatcher(n.word, dictionary, p)) {
                        if (pr.length() <= (p.length() * 2)) {
                            neighbours.add(pr);
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    public static List<String> prefixMatcher(final String currWord,
            final String[] dictionary, final String prefix) {
        List<String> pWords = new ArrayList<>();
        for (String d : dictionary) {
            if (d.startsWith(prefix)) {
                pWords.add(d);
            }
        }
        return pWords;
    }

    public static List<String> prefixFinder(String word) {
        List<String> suffix = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            suffix.add(word.substring(i, word.length()));
        }
        return suffix;
    }

    private static class Node {

        private List<String> singly;
        private List<String> doubly;
        private String word;

        public Node(String word) {
            this.word = word;
            this.singly = new ArrayList<>();
            this.doubly = new ArrayList<>();
        }

        public void setSingly(List<String> sNodes) {
            this.singly = sNodes;
        }

        public void setDoubly(List<String> sNodes) {
            this.doubly = sNodes;
        }

        public String toString() {
            return this.word;
        }
    }
}
