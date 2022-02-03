abstract sig Status {}

one sig Visited, Unvisited extends Status {}


var sig Init in Node {}
var sig Curr in Node {}

sig Node {
	edge : set Edge,
}

sig Edge {
	dest : one Node,
	var status : one Status
}

fact InitStatic {
	// No self loops
	all e : Edge | edge.e != e.dest
	
	// Uma aresta tem de possuir um nodo inicial
	all e : Edge | one edge.e

	// Um par de vertices só pertence a uma aresta
	all e1 , e2 : Edge | (e1.dest = e2.dest and e1.~edge = e2.~edge) implies e1=e2

	// Todos os nodos tem grau par
	all n : Node | rem[#n.edge ,2] = 0
	

}

fact InitDinamic {

	// Estado inicial das arestas
	Edge.status = Unvisited

	// Nao ha nodo inicial
	no Init
	
	// Nao ha nodo atual
	no Curr
}


pred nop {
	status' = status
	Curr' = Curr
	Init' = Init
}

pred start [n: Node] {
	// guards
	no Init
	no Curr
	Edge.status = Unvisited
	
	// effects
	Init' = n
	Curr' = n

	 // frame conditions
	status' = status

}


pred next [e: Edge] {
	// guards 
	one Curr
	one Init
	e in Curr.edge
	e.status = Unvisited
	

	// effects
	
	(e.status)' = Visited
	all ed : Edge-e | (ed.dest = edge.e and edge.ed = e.dest) implies ed.status' = Visited
	Curr' = e.dest
	 
	// frame conditions
	all ed : Edge-e | (ed.dest != edge.e or edge.ed != e.dest) implies ed.status' = ed.status
	Init' = Init
}



fact Traces {
	always (nop or 
			(some n : Node | start[n])  or
			(some e : Edge | next[e]))
}


run ex {
	// Grafo completo (um nodo tem aresta para os outros todos)
	all n1 : Node, n2 : Node-n1 | n2 in n1.edge.dest
} for  exactly 5 Node, exactly 20 Edge, 20 steps
