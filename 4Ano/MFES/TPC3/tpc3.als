// Link: http://alloy4fun.inesctec.pt/jYQPSfiCtkJ896yTf

sig Hash {}
abstract sig Object {
	hash : one Hash
}

sig Blob extends Object {}

sig Name {}
sig Tree extends Object {
	objects : Hash -> Name
}

sig Commit extends Object {
	tree : one Hash,
	parent : set Hash
}

pred Invs {
	// Specify the properties of the git object model inside this predicate

	// The number of points you will get is proportional to the number of correct properties.
	// To check how many points you have so far you can use the different commands. The maximum is 5 points.
	// Be careful to not overspecify! 
	// If not all possible git object models are allowed by your spec you get 0 points, even if you have some correct properties.
	// To check if you are not overspecifying you can use command AllModelsArePossible. 
	// If you are overspecifying this command will return a git object model that should be possible 
	// but that you spec is not currently accepting.
  
	// 1.
	all t : Tree | all n : Name | lone t.objects.n
	
  	// 2.
  	all h : Hash | lone hash.h 
  	
  	//3.1
  	all t : Tree | t.objects.Name in (Tree+Blob).hash
  	
  	//3.2
  	all c : Commit | c.tree in Tree.hash
  
  	//3.3
  	all c : Commit | c.parent in Commit.hash
  
  	// 4.1
  	all c : Commit | c.hash not in c.parent.^(~hash.parent)
  
  	//4.2
  	all t : Tree | t.hash not in t.objects.Name.^(~hash.objects.Name)
  
  	// 5.1
  	all t, t2 : Tree | hash.(t.objects) = hash.(t2.objects) implies t = t2
  	
  	// 5.2
  	all c1, c2 : Commit | hash.(c1.tree) = hash.(c2.tree) and hash.(c1.parent) = hash.(c2.parent) implies c1 = c2 
}
