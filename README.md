# CZ2001 ALGORITHMS
## Project 2: Graph Algorithms
  
#### Problem
You are given an undirected unweighted graph G, which represents a city’s road network with n nodes being intersections/endpoints and m edges being roads. Among the n nodes, h of them are hospitals and you are interested in finding, for each node, the distance (i.e., the number of edges in the shortest path) from each node to the nearest hospital. h could take any value from 1 to n. 
- (a) Design an algorithm for computing the distance from each node in G to its nearest hospital. Output the distance and the shortest path for each node to a file. <br/>
- (b) Design an algorithm to complete the task (a) but its time complexity should not depend on the total number of hospitals h. You could skip (b) if your algorithm in (a) already satisfies this complexity constraint. 
- (c) In some circumstances (e.g., big disasters, fires, etc.), having the distance from each node to the nearest hospital is not good enough. Instead, we are interested in finding the distances to top-2 nearest hospitals from each node. Revise your algorithm in (b) to accommodate this new requirement. If revision is not possible, you are free to design a new algorithm. 
- (d) Propose an algorithm that works generally for computing the distances from each node to top-k nearest hospitals for an input value of k.

## Instruction to clone this repository
#### For the first time access to this project:
- open cmd and change directory to the folder you want to add this project to
- `git clone https://github.com/taquynhnga2001/cz2001-graphBFS.git` and then press Enter
- change directory to `cz2001-graphBFS`
- `git remote add orgin https://github.com/taquynhnga2001/cz2001-graphBFS.git`
- `git init`
#### From later access to the project:
- open cmd and change directory to the project folder `cz2001-graphBFS`
- `git pull origin main` to update the latest changes of the project. After that, you can update the project.
- `git add .` and then `git commit -m "message for what you updated"` after making any changes to the project. (use double quote " ") (the message, pls be clear for others to follow)
- `git push origin main` to push all changes to the remote repository so other people can see and update your changes.
#### To run the program:
- open the folder and double click on file `run.bat`
- OR just type `.\run.bat` on cmd

## Intruction to run the programme
The interface to run the project algorithms is in the file `UserInterface.java`. Use terminal/cmd to compile and run the code:
`javac UserInterface.java`
`java UserInterface`

-------------------------------------------------------------------------------
The interface will appear in the console. There are 2 problems in this project:
- Problem (a) : Find the shortest path from all nodes
- Problem (b) : Find the Top-k nearest hospitals from all nodes
Type in `a` or `b` to test the problem when being asked. Type in `x` to exit the code.

Note: Problem (a) refers to Part a) and b) in the lab manual.
      Problem (b) refers to Part c) and d) in the lab manual.

In each problem, we provide 3 existing data files in Tab-separated values format with different size
- data set (a) : `file1-1k.txt` (1,006 nodes, 2,461 edges) and `file2-1k.txt` (10 hospitals)
- data set (b) : `file1-10k.txt` (10,006 nodes, 25,231 edges) and `file2-10k.txt` (100 hospitals)
- data set (c) : `roadNet_CA.txt` Real road network data (1.965.206 nodes, 5.533.214 edges) and `fileHos.txt` (generate and store random hospitals based on input number)
If you want to test on your own data set:
- data set (d) : you can provide your own file in Tab-separated values format by providing the absolute file path of those files (file of graph structure and file of hospital IDs). The file must have information about the number of Nodes and Number of Edges at the beginning of the file. Refer to the .txt files mentioned above to see the format.
Type in `a`, `b`, `c` or `d` to test the data set when being asked. Type in `x` to exit the problem.

For the data set (c), you need to provide an approximate number of hospital you want to generate randomly. The hospital IDs will be written in the file `fileHos.txt`.
For the 'Problem (b) : Find the Top-k nearest hospitals from all nodes', `k` must not be bigger than 60

-------------------------------------------------------------------------------
The output files are stored in the folder `output` (in this directory). The name of the output file willbe mentioned again when running the code. Or you can read the list below to know the output file name:
- Problem (a) : 
  + data set (a) : `output-a-1k.txt`
  + data set (b) : `output-a-10k.txt`
  + data set (c) : `output-a-real-data.txt`
  + data set (d) : `output-a-own-data.txt`
- Problem (b) : 
  + data set (a) : `output-b-1k.txt`
  + data set (b) : `output-b-10k.txt`
  + data set (c) : `output-b-real-data.txt`
  + data set (d) : `output-b-own-data.txt`
