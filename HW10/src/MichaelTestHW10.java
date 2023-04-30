import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/*
 * In the event that there is an error found in these tests or an
 * untested edge case is found, this test file may be updated. Check
 * back on the same gist later for updates.
 */

public class HambyTestHW10 {

    private static final int TIMEOUT = 200;

    private Graph<String> undirectedGraph;
    private Graph<String> directedGraph;
    private Graph<String> disconnectedGraph;

    @Before
    public void setUp() {
        createUndirectedGraph();
        createDirectedGraph();
        createDisconnectedGraph();
    }

    private void createUndirectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 77; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 2));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("E"), 9));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("M"), 3));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("A"), 2));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("D"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("B"), 9));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 3));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("H"), 10));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 3));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("H"), new Vertex<>("E"), 10));
        edges.add(new Edge<>(new Vertex<>("H"), new Vertex<>("I"), 1));
        edges.add(new Edge<>(new Vertex<>("I"), new Vertex<>("H"), 1));
        edges.add(new Edge<>(new Vertex<>("I"), new Vertex<>("J"), 7));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("C"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("F"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("I"), 7));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("K"), 2));
        edges.add(new Edge<>(new Vertex<>("J"), new Vertex<>("L"), 3));
        edges.add(new Edge<>(new Vertex<>("K"), new Vertex<>("J"), 2));
        edges.add(new Edge<>(new Vertex<>("L"), new Vertex<>("J"), 3));
        edges.add(new Edge<>(new Vertex<>("L"), new Vertex<>("M"), 7));
        edges.add(new Edge<>(new Vertex<>("M"), new Vertex<>("C"), 3));
        edges.add(new Edge<>(new Vertex<>("M"), new Vertex<>("L"), 7));

        undirectedGraph = new Graph<>(vertices, edges);
    }

    private void createDirectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 71; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 2));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 7));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 2));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("C"), 2));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("D"), 2));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("E"), 4));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("E"), 4));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("A"), 5));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 2));
        edges.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 2));

        directedGraph = new Graph<>(vertices, edges);
    }

    private void createDisconnectedGraph() {
        Set<Vertex<String>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>("" + (char) i));
        }

        Set<Edge<String>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("C"), 1));
        edges.add(new Edge<>(new Vertex<>("B"), new Vertex<>("D"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        edges.add(new Edge<>(new Vertex<>("C"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("D"), new Vertex<>("B"), 1));
        edges.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 1));
        edges.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 1));

        disconnectedGraph = new Graph<>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testBfsUndirected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), undirectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("J"));
        desired.add(new Vertex<>("M"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("H"));
        desired.add(new Vertex<>("I"));
        desired.add(new Vertex<>("K"));
        desired.add(new Vertex<>("L"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT)
    public void testBfsDirected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), directedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT)
    public void testBfsDisconnected() {
        List<Vertex<String>> bfs =
                GraphAlgorithms.bfs(new Vertex<>("A"), disconnectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, bfs);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullStart() {
        GraphAlgorithms.bfs(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsNullGraph() {
        GraphAlgorithms.bfs(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBfsStartNotPresent() {
        GraphAlgorithms.bfs(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsUndirected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), undirectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));
        desired.add(new Vertex<>("J"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("M"));
        desired.add(new Vertex<>("L"));
        desired.add(new Vertex<>("I"));
        desired.add(new Vertex<>("H"));
        desired.add(new Vertex<>("K"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, dfs);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsDirected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), directedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));
        desired.add(new Vertex<>("E"));
        desired.add(new Vertex<>("F"));
        desired.add(new Vertex<>("G"));

        assertEquals(desired, dfs);
    }

    @Test(timeout = TIMEOUT)
    public void testDfsDisconnected() {
        List<Vertex<String>> dfs =
                GraphAlgorithms.dfs(new Vertex<>("A"), disconnectedGraph);

        List<Vertex<String>> desired = new ArrayList<>();
        desired.add(new Vertex<>("A"));
        desired.add(new Vertex<>("B"));
        desired.add(new Vertex<>("C"));
        desired.add(new Vertex<>("D"));

        assertEquals(desired, dfs);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullStart() {
        GraphAlgorithms.dfs(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsNullGraph() {
        GraphAlgorithms.dfs(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDfsStartNotPresent() {
        GraphAlgorithms.dfs(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasUndirected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        undirectedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 1);
        desired.put(new Vertex<>("C"), 1);
        desired.put(new Vertex<>("D"), 2);
        desired.put(new Vertex<>("E"), 8);
        desired.put(new Vertex<>("F"), 5);
        desired.put(new Vertex<>("G"), 6);
        desired.put(new Vertex<>("H"), 11);
        desired.put(new Vertex<>("I"), 10);
        desired.put(new Vertex<>("J"), 3);
        desired.put(new Vertex<>("K"), 5);
        desired.put(new Vertex<>("L"), 6);
        desired.put(new Vertex<>("M"), 4);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDirected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        directedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 2);
        desired.put(new Vertex<>("C"), 4);
        desired.put(new Vertex<>("D"), 6);
        desired.put(new Vertex<>("E"), 10);
        desired.put(new Vertex<>("F"), 7);
        desired.put(new Vertex<>("G"), 9);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstrasDisconnected() {
        Map<Vertex<String>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>("A"),
                        disconnectedGraph);

        Map<Vertex<String>, Integer> desired = new HashMap<>();
        desired.put(new Vertex<>("A"), 0);
        desired.put(new Vertex<>("B"), 1);
        desired.put(new Vertex<>("C"), 1);
        desired.put(new Vertex<>("D"), 2);
        desired.put(new Vertex<>("E"), Integer.MAX_VALUE);
        desired.put(new Vertex<>("F"), Integer.MAX_VALUE);

        assertEquals(desired, dijkstras);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullStart() {
        GraphAlgorithms.dijkstras(null, undirectedGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasNullGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>("A"), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testDijkstrasStartNotPresent() {
        GraphAlgorithms.dijkstras(new Vertex<>("Z"), undirectedGraph);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsUndirected() {
        Set<Edge<String>> kruskals = GraphAlgorithms.kruskals(undirectedGraph);

        Set<Edge<String>> desired = new HashSet<>();
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("C"), 1));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("A"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("D"), 2));
        desired.add(new Edge<>(new Vertex<>("D"), new Vertex<>("A"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("C"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("M"), 3));
        desired.add(new Edge<>(new Vertex<>("M"), new Vertex<>("C"), 3));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("K"), 2));
        desired.add(new Edge<>(new Vertex<>("K"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("L"), 3));
        desired.add(new Edge<>(new Vertex<>("L"), new Vertex<>("J"), 3));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("F"), 2));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("J"), 2));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 1));
        desired.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 1));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("E"), 3));
        desired.add(new Edge<>(new Vertex<>("E"), new Vertex<>("F"), 3));
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 1));
        desired.add(new Edge<>(new Vertex<>("J"), new Vertex<>("I"), 7));
        desired.add(new Edge<>(new Vertex<>("I"), new Vertex<>("J"), 7));
        desired.add(new Edge<>(new Vertex<>("H"), new Vertex<>("I"), 1));
        desired.add(new Edge<>(new Vertex<>("I"), new Vertex<>("H"), 1));

        assertEquals(desired, kruskals);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDirected() {
        Set<Edge<String>> kruskals = GraphAlgorithms.kruskals(directedGraph);

        Set<Edge<String>> desired = new HashSet<>();
        desired.add(new Edge<>(new Vertex<>("A"), new Vertex<>("B"), 2));
        desired.add(new Edge<>(new Vertex<>("B"), new Vertex<>("A"), 2));
        desired.add(new Edge<>(new Vertex<>("B"), new Vertex<>("C"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("B"), 2));
        desired.add(new Edge<>(new Vertex<>("C"), new Vertex<>("D"), 2));
        desired.add(new Edge<>(new Vertex<>("D"), new Vertex<>("C"), 2));
        desired.add(new Edge<>(new Vertex<>("D"), new Vertex<>("E"), 4));
        desired.add(new Edge<>(new Vertex<>("E"), new Vertex<>("D"), 4));
        desired.add(new Edge<>(new Vertex<>("D"), new Vertex<>("F"), 1));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("D"), 1));
        desired.add(new Edge<>(new Vertex<>("F"), new Vertex<>("G"), 2));
        desired.add(new Edge<>(new Vertex<>("G"), new Vertex<>("F"), 2));

        assertEquals(desired, kruskals);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnected() {
        assertNull(GraphAlgorithms.kruskals(disconnectedGraph));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKruskalsNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

}