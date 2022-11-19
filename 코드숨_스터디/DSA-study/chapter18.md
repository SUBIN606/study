# 18장. 그래프로 뭐든지 연결하기

## 18.1 그래프

- 그래프란?
  - 그래프는 관계에 특화된 자료 구조로, 데이터가 서로 어떻게 연결되는지 쉽게 이해할 수 있다.
- 그래프와 트리의 차이점은?
  - 트리는 그래프의 한 종류다. 그래프와 트리 모두 서로 연결되는 노드로 구성된다는 공통점이 있다.
  - 모든 트리는 그래프이지만, 그래프가 모두 트리는 아니다.
  - 트리로 규정되는 그래프에는 사이클(cycle)이 있을 수 없으며 모든 노드가 서로 연결되어야 한다. 간접적으로라도 다른 노드와 연결된다. 그래프는 완전히 연결되지 않을 수 있다.
  - 그래프에는 사이클을 형성하는 노드, 즉 서로 순환적으로 참조하는 노드가 있을 수 있다.
  - **그래프에서는 각 노드를 정점(vertex)이라 부른다. 정점을 잇는 선은 간선(edge)이라 부르며, 연결된 정점은 서로 인접한다(adjacent)고 말한다. 인접한 정점을 이웃(neighbor)이라고도 부른다.**
- 연결 그래프란?
  - 모든 정점이 어떻게든 서로 연결된 그래프다.

> 기초 해시 테이블로 가장 기본적인 그래프를 표현할 수 있다. <br>
> 해시 테이블의 모든 키 값은 한 단계로 찾을 수 있으므로 그래프를 사용하면 `Alice`의 친구들을 `O(1)`에 찾을 수 있다. <br>
> 예) `friends["Alice"]` -> 앨리스의 모든 친구가 담긴 배열을 반환한다.

```ruby
friends = {
  "Alice" => ["Bob", "Diana", "Fred"],
  "Bob" => ["Alice", "Cynthia", "Diana"],
  "Cynthia" => ["Bob"],
  "Diana" => ["Alice", "Bob", "Fred"],
  "Elise" => ["Fred"],
  "Fred" => ["Alice", "Diana", "Elise"]
}
```

## 18.2 방향 그래프

- 방향 그래프란?
  - 관계가 상호적이지 않은 그래프. 모든 방향이 상호적인 그래프는 무방향 그래프라 부른다.

## 18.3 객체 지향 그래프 구현

> 책 내용을 참고하여 java로 직접 구현해본 코드

```java
public class Graph {

    private Map<Vertex<Object>, List<Vertex>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    // 정점(vertex)을 추가한다
    public void addVertex(Object value) {
        this.graph.putIfAbsent(new Vertex<>(value), new ArrayList<>());
    }

    // 정점을 제거한다
    public void removeVertex(Object value) {
        Vertex<Object> vertexToRemove = new Vertex<>(value);
        this.graph.remove(vertexToRemove);
        this.graph.values().forEach(entry -> entry.remove(vertexToRemove));
    }

    // 간선(edge)를 추가한다 == 정점과 정점을 연결한다
    public void addEdge(Object value1, Object value2) {
        Vertex<Object> vertex1 = new Vertex<>(value1);
        Vertex<Object> vertex2 = new Vertex<>(value2);

        this.graph.get(vertex1).add(vertex2);
        this.graph.get(vertex2).add(vertex1);
    }

    // 간선을 제거한다 == 정점의 정점의 연결을 해제한다
    public void removeEdge(Object value1, Object value2) {
        Vertex<Object> vertex1 = new Vertex<>(value1);
        Vertex<Object> vertex2 = new Vertex<>(value2);

        this.graph.get(vertex1).remove(vertex2);
        this.graph.get(vertex2).remove(vertex1);
    }

    // 정점과 연결된 정점들을 반환한다
    public List<Vertex> getNeighbors(Object value) {
        return this.graph.get(new Vertex<>(value));
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjacentVertexes=" + graph +
                '}';
    }

    // 정점
    public static class Vertex<T extends Object> {
        Object value;

        public Vertex(Object value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

    }
}
```

## 18.4 그래프 탐색

- 그래프에서 "탐색"이란?
  - 가장 단순한 의미에서 그래프 탐색은 그래프 어딘가에 있는 특정 정점을 찾는 것이다.
  - 그래프에서 탐색은 보통 그래프 내 한 정점에 접근할 수 있을 때 그 정점과 어떻게든 연결된 또 다른 특정 정점을 찾는 것을 의미한다.
    - 연결 그래프에서는 임의의 정점 하나만 접근할 수 있으면 탐색으로 모든 그래프 내 정점을 찾을 수 있다.
  - 두 정점이 연결되어 있는지 알아내는 것.
  - 그래프 순회.
- 그래프의 경로(path)란?
  - 그래프의 한 정점에서 다른 정점으로 가는 간선(edge)들의 순열을 뜻한다.

> 그래프 탐색에는 깊이 우선 탐색과 너비 우선 탐색, 두 방식이 널리 쓰인다.

## 18.5 깊이 우선 탐색(Depth-First Search, DFS)

- 깊이 우선 탐색 알고리즘은 어떻게 동작하는가?

1. 그래프 내 임의의 정점에서 시작한다.
2. 현재 정점을 해시 테이블에 추가해 방문했던 정점임을 표시한다.
3. 현재 정점의 인접 정점을 순회한다.
4. 방문했던 인접 정점이면 무시한다.
5. 방문하지 않았던 인접 정점이면 그 정점에 대해 재귀적으로 깊이 우선 탐색을 수행한다.

> 책을 참고해 java로 구현한 DFS

```java
private void dfsTraversal(Object value, Set<Vertex> visited) {
    Vertex<Object> currentVertex = new Vertex<>(value);
    visited.add(currentVertex);

    if (getNeighbors(value) == null || getNeighbors(value).isEmpty()) {
        return;
    }

    getNeighbors(value).forEach(vertex -> {
        if (!visited.contains(vertex)) {
            visited.add(vertex);
            dfsTraversal(vertex.value, visited);
        }
    });
}

// 1. 그래프 내 임의의 정점에서 시작한다.
// 2. 현재 정점을 해시 테이블에 추가해 방문했던 정점임을 표시한다.
// 3. 현재 정점의 인접 정점을 순회한다.
// 4. 방문했던 인접 정점이면 무시한다.
// 5. 방문하지 않았던 인접 정점이면 그 정점에 대해 재귀적으로 깊이 우선 탐색을 수행한다.
public List<Vertex> depthFirstTraversal(Object value) {
    Set<Vertex> visited = new LinkedHashSet<>();
    dfsTraversal(value, visited);
    return new ArrayList<>(visited);
}
```

## 18.6 너비 우선 탐색(Breadth-First Search, BFS)

- 너비 우선 탐색과 깊이 우선 탐색의 차이점?
  - 너비 우선 탐색은 재귀를 쓰지 않고 큐를 사용해 문제를 해결한다.
- 너비 우선 탐색 알고리즘은 어떻게 동작하는가?

1. 그래프 내 아무 정점에서나 시작한다. 이 정점을 시작 정점이라 부른다.
2. 시작 정점을 해시 테이블에 추가해 방문했다고 표시한다.
3. 시작 정점을 큐에 추가한다.
4. 큐가 빌 때까지 실행하는 루프를 시작한다.
5. 루프 안에서 큐의 첫 번째 정점을 삭제한다. 이 정점을 "현재 정점"이라 부른다.
6. 현재 정점의 인접 정점을 순회한다.
7. 이미 방문한 인접 정점이면 무시한다.
8. 아직 방문하지 않은 인접 정점이면 해시 테이블에 추가해 방문했다고 표시한 후 큐에 추가한다.
9. 큐가 빌 때까지 루프를 반복한다.

> 그래프를 탐색하는 동안 시작 정점 가까이 있고 싶으면 너비 우선 탐색이 좋고, 빨리 멀어져야 한다면 깊이 우선 탐색이 이상적이다.

> 책을 참고해 java로 구현한 BFS

```java
// 1. 그래프 내 아무 정점에서나 시작한다. 이 정점을 시작 정점이라 부른다.
// 2. 시작 정점을 해시 테이블에 추가해 방문했다고 표시한다.
// 3. 시작 정점을 큐에 추가한다.
// 4. 큐가 빌 때까지 실행하는 루프를 시작한다.
// 5. 루프 안에서 큐의 첫 번째 정점을 삭제한다. 이 정점을 "현재 정점"이라 부른다.
// 6. 현재 정점의 인접 정점을 순회한다.
// 7. 이미 방문한 인접 정점이면 무시한다.
// 8. 아직 방문하지 않은 인접 정점이면 해시 테이블에 추가해 방문했다고 표시한 후 큐에 추가한다.
// 9. 큐가 빌 떄까지 루프를 반복한다.
public List<Vertex> breadthFirstTraversal(Object value) {
    Set<Vertex> visited = new LinkedHashSet<>();
    Queue<Vertex> queue = new LinkedList<>();

    Vertex<Object> startVertex = new Vertex<>(value);
    visited.add(startVertex);
    queue.add(startVertex);

    while (!queue.isEmpty()) {
        Vertex currentVertex = queue.poll();
        getNeighbors(currentVertex.value).forEach(vertex -> {
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                queue.add(vertex);
            }
        });
    }
    return new ArrayList<>(visited);
}
```

## 18.7 그래프 탐색의 효율성

- 그래프 탐색의 효율성을 빅 오 표기법으로 표현하면 어떻게 되는가? 그리고 그 이유는?
  - O(V + E)
  - V는 정점(vertex)을 뜻하며 그래프 내 정점의 개수를 나타낸다.
  - E는 간선(edge)을 뜻하며 그래프 내 간선의 개수를 나타낸다.
  - 그래프 내 정점 수에 그래프 내 간선 수를 더한 값이 곧 단계 수가 된다.
  - 그래프 내 정점을 모두 순회해야 하고, 정점의 인접 정점까지 모두 순회하기 때문이다.

## 18.8 가중 그래프

- 가중 그래프란?
  - 간선에 수치를 부여한 그래프?
  - 그래프 간선에 정보를 추가하는 그래프.

## 18.9 데이크스트라의 알고리즘
